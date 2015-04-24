class ActiveThreadCounterThread extends Thread {
    
    private int refreshRate = 100; // milliseconds
    private long pid;
    private ActiveThreadCounter monitoredThread;
    private boolean done = false;

    public ActiveThreadCounterThread(long pid) {
        this.pid = pid;
        monitoredThread = new ActiveThreadCounter(pid);
    }

    public void run() {
        while(!done) {

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