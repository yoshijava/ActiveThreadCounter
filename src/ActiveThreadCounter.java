import java.io.*;
import java.util.Vector;

class ActiveThreadCounter {

    private long pid;
    private File[] friends;
    private File myself;
    private File myTask;
    private Vector<LinuxTask> subtask = new Vector<LinuxTask>();

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
        log("Checking whether " + pid + " exists...");
        if (myself.exists() && myself.isDirectory()) {
            log("The proc of pid=" + pid + " exists! Good job.");
            myTask = new File("/proc/" + pid + "/task/");
        }
        else {
            log("Are you nuts?");
            System.exit(1);
        }

    }
    
    private void searchFriends() {
        friends = myTask.listFiles();
        for(int i=0; i < friends.length; i++) {
            subtask.add(new LinuxTask(friends[i]));
        }
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

