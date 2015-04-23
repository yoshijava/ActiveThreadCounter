class UtilityClass {

    public static String tag = "[ATC] ";

    public static void log(String s) {
       System.out.println(tag + s);
    }
    
    public static void checkOS() {
        String OS = System.getProperty("os.name");
        if( OS.equalsIgnoreCase("linux") == false ) {
            log("Only linux OSs are supported");
            System.exit(1);
        }
    }
}