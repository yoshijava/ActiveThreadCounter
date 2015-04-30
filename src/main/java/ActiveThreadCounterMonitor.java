import java.io.IOException;

class ActiveThreadCounterMonitor extends Thread {
    
    private long pid;
    private ActiveThreadCounter monitoredThread;
    private boolean done = false;

    // we should call it "threads that are in runnable/running state", but whatever...just don't call it "TLP"
    private int R_state = 0;

    public ActiveThreadCounterMonitor(long pid) {
        this.pid = pid;
        monitoredThread = new ActiveThreadCounter(pid);
    }   

    public void run() {
        logd("I'm going to fucking monitoring the threads' states...");
        int counter = ConfigurableParameters.TIME_TO_REBUILD_FRIEND_LIST;
        int accumulatedR_state = 0;
        int round = 0;

        while(!done) {    
            // existing friends, please update your states
            monitoredThread.notifyFriendsToUpdateState();
            R_state = monitoredThread.getRunningState();
            log("The 'R' state threads = " + R_state);

            counter--;
            if (counter == 0 ) {
                logd("time to remake friends!");
                int ret = monitoredThread.rebuildFriendsList();
                if (ret == -1) {
                    // this thread is TERMINATED.
                    done = true;
                    continue;
                }
                counter = ConfigurableParameters.TIME_TO_REBUILD_FRIEND_LIST;
            }            
            round++;
            nap();
        }

        log("The monitored thread is terminated. So long!");
        log("The final avg. R_state = " + ((double) accumulatedR_state)/round);
    }


    private void nap() {
        try {
            Thread.sleep(ConfigurableParameters.STATE_REFRESH_RATE);
        }
        catch(InterruptedException e) {
            logd("Sleeping fails. How come?");
            e.printStackTrace();
        }        
    }

    // not used currently, but keep it for the future, maybe someone wants to stop the monitor in the future?
    public void stopMonitoring() {
        done = true;
    }

    private static void log(String s) {
        UtilityClass.log(s);
    }

    private static void logd(String s) {
        UtilityClass.logd(s);
    }

    public static void main(String[] args) throws IOException {
        long pid = -1;
        // If OS is not linux, just quit.
        UtilityClass.checkOS();
        if(args.length != 1) {
            log("No PID is given. Use /proc/myself/ instead.");
            String line = UtilityClass.justGetFirstLine("/proc/self/stat");
            String[] words = line.split(" ");
            pid = Long.parseLong(words[0].trim());
            log("pid of /proc/myself = " + pid);
        }
        else {
            pid = Long.parseLong(args[0]);
        }
        ActiveThreadCounterMonitor obj = new ActiveThreadCounterMonitor(pid);
        obj.start();
        logd("Buckle up!");
    }

}