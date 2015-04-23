
class ActiveThreadCounter {

	private long pid;

	public ActiveThreadCounter() {
		// If OS is not linux, just quit.
		checkOS();



	}

	public void setMonitoredPID(long pid) {
		this.pid = pid;
	}

	private void checkOS() {
		String OS = System.getProperty("os.name");
		if( OS.equalsIgnoreCase("linux") == false ) {
			System.out.println("Only linux OSs are supported");
			System.exit(0);
		}
	}

    public static void main(String[] args) {
        ActiveThreadCounter obj = new ActiveThreadCounter();
    }
}

