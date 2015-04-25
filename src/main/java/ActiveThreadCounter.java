import java.io.*;
import java.util.*;


class ActiveThreadCounter {

    private long pid;
    private File[] friendsFile;
    private File myself;
    private File myTask;
    private Vector<LinuxTask> friendGroup = new Vector<LinuxTask>();

    private static void logd(String s) {
        UtilityClass.logd(s);
    }

    private static void log(String s) {
        UtilityClass.log(s);
    }

    public ActiveThreadCounter(long pid) {
        this.pid = pid;
        checkPID();
        searchFriends();
    }

    private void checkPID() {
        myself = new File("/proc/" + pid + "/");
        logd("Checking whether " + pid + " exists...");
        if (myself.exists() && myself.isDirectory()) {
            logd("The proc of pid = " + pid + " exists! Good job.");
            myTask = new File("/proc/" + pid + "/task/");
        }
        else {
            logd("Are you nuts?");
            System.exit(1);
        }

    }
    
    // return 0 for good cases. -1 for bad case
    private int searchFriends() {
        friendsFile = myTask.listFiles();
        if(friendsFile == null) {
            // this proc directory does not exist. It has been TERMINATED.
            return -1;
        }
        for(int i=0; i < friendsFile.length; i++) {
            try {
                LinuxTask task = new LinuxTask(friendsFile[i]);
                friendGroup.add(task);
            }
            catch(IOException e) {
                logd("So fast. The new born friend is gone...");
            }
        }
        return 0;
    }

    // clean and rebuild the friend list
    public int rebuildFriendsList() {
        friendGroup.clear();
        return searchFriends();
    }

    // notify friends to update their states
    public void notifyFriendsToUpdateState() {
        Iterator<LinuxTask> iter = friendGroup.iterator();
        while (iter.hasNext()) {
            LinuxTask friend = iter.next();
            try {
                friend.updateState();
            }
            catch(IOException e) {
                logd("An old friend is gone. RIP. He is no longer in my friend list anymore.");
                iter.remove();
            }
        }
    }

    // this is the so-called TLP? Fuck.
    // I just want to return the number of threads that are in running/runnable state in the previous sampling period
    public int getRunningState() {
        int runningTask = 0;

        for( LinuxTask task : friendGroup ) {
            // current state
            int state = task.getCurrentState();
            runningTask += state;  // if not running/runnable, state = 0
        }
        return runningTask;
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
        ActiveThreadCounter obj = new ActiveThreadCounter(pid);
        log("Sleep tight, man.");

    }
}

