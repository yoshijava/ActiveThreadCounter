class ActiveThreadCounterThread extends Thread {
    
    private long pid;
    private ActiveThreadCounter monitoredThread;
    private boolean done = false;

    // we should call it "threads that are in runnable/running state", but whatever...
    private int TLP = 0;

    public ActiveThreadCounterThread(long pid) {
        this.pid = pid;
        monitoredThread = new ActiveThreadCounter(pid);
    }   

    public void run() {
        log("I'm going to fucking monitoring the threads' states...");
        int counter = ConfigurableConstants.TIME_TO_REBUILD_FRIEND_LIST;

        while(!done) {    
            // existing friends, please update your states
            monitoredThread.notifyFriendsToUpdateState();
            TLP = monitoredThread.getAvgRunningState();
            log("The fucking TLP = " + TLP);

            counter--;
            if (counter == 0 ) {
                log("time to remake friends!");
                monitoredThread.rebuildFriendsList();
                counter = ConfigurableConstants.TIME_TO_REBUILD_FRIEND_LIST;
            }            
            
            nap();
        }

        log("You make me feel embarrassing...");
    }


    private void nap() {
        try {
            Thread.sleep(ConfigurableConstants.STATE_REFRESH_RATE);
        }
        catch(InterruptedException e) {
            log("Fucking sleeping fails. How come?");
            e.printStackTrace();
        }        
    }

    public void stopMonitoring() {
        done = true;
    }

    private static void log(String s) {
        UtilityClass.log(s);
    }

    public static void main(String[] args) {
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
        ActiveThreadCounterThread obj = new ActiveThreadCounterThread(pid);
        obj.start();
        log("Buckle up!");
    }

}