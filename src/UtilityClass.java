class UtilityClass {

    public static final String TAG = "[ATC] ";

    public static void log(String s) {
       System.out.println(TAG + s);
    }
    
    public static void checkOS() {
        String OS = System.getProperty("os.name");
        if( OS.equalsIgnoreCase("linux") == false ) {
            log("Only linux OSs are supported");
            System.exit(1);
        }
    }
}