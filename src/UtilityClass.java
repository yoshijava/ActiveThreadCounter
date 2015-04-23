import java.io.*;

class UtilityClass {

    public static final String TAG = "[ATC]";

    public static void log(String s) {
       System.out.println(TAG + " " + s);
    }

    // for debug message
    public static void logd(String s) {
        System.out.println(TAG + "[DEBUG] " + s);        
    }
    
    public static void checkOS() {
        String OS = System.getProperty("os.name");
        if( OS.equalsIgnoreCase("linux") == false ) {
            log("Only linux OSs are supported");
            System.exit(1);
        }
    }

    public static String justGetFirstLine(String filename) throws IOException {
        String line = null;
        FileReader fileReader = new FileReader( filename );
        BufferedReader bReader = new BufferedReader(fileReader);
        line = bReader.readLine();
        bReader.close();
        fileReader.close();
        return line;
    }
}