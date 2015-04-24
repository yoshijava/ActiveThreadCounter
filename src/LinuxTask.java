import java.io.*;

class LinuxTask {
    
    private long pid;
    private String cmdline;
    private int state = -1; // 1 stands for running. Others are assigned as 0

    // There are many possible states for a thread
    // I'd like to calculate how much time a thread spent in state "R" (running or runnable)
    //
    // R  Running
    // S  Sleeping in an interruptible wait
    // D  Waiting in uninterruptible disk sleep
    // Z  Zombie
    // T  Stopped (on a signal) or (before Linux 2.6.33) trace stopped
    // t  Tracing stop (Linux 2.6.33 onward)
    // W  Paging (only before Linux 2.6.0)
    // X  Dead (from Linux 2.6.0 onward)
    // x  Dead (Linux 2.6.33 to 3.13 only)
    // K  Wakekill (Linux 2.6.33 to 3.13 only)
    // W  Waking (Linux 2.6.33 to 3.13 only)
    // P  Parked (Linux 3.9 to 3.13 only)

    public LinuxTask(File file) {
        // prefix should be something like /proc/[pid1]/task/[pid2]/
        String prefix = file.toString();
        
        // cmdline
        cmdline = UtilityClass.justGetFirstLine( prefix + "/cmdline" );
        if (cmdline == null) {
            // File not found. Is it possible?
            // keep all things default
            UtilityClass.log(prefix + "/cmdline is not found. Weird.");
            return;
        }

        // pid
        String[] words = prefix.split("/");
        pid = Long.parseLong(words[words.length-1]);
        
        // state
        String line = UtilityClass.justGetFirstLine( prefix + "/stat" );
        words = line.split(" ");
        String strState = words[2];
        if (strState.equals("R")) {
            // yes! this thread is runnable or running
            state = 1;
        }
        else {
            // other cases. I don't care.
            state = 0;
        }
    }


}