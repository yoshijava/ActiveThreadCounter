import java.io.*;

class ActiveThreadCounter {

	private long pid;

	public ActiveThreadCounter(long pid) {
		// If OS is not linux, just quit.
		checkOS();
		this.pid = pid;
		checkPID();
	}


	private void checkPID() {
		File file = new File("/proc/" + pid + "/");
		System.out.println("Checking whether " + pid + " exists...");
		if (file.exists() && file.isDirectory()) {
			System.out.println("Exist.");
		}

	}
	
	private void checkOS() {
		String OS = System.getProperty("os.name");
		if( OS.equalsIgnoreCase("linux") == false ) {
			System.out.println("Only linux OSs are supported");
			System.exit(1);
		}
	}

    public static void main(String[] args) {
    	long pid = Long.parseLong(args[0]);
        ActiveThreadCounter obj = new ActiveThreadCounter(pid);
        System.out.println("End.");

    }
}

