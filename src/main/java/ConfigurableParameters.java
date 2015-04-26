import org.json.*;
import java.io.*;

public class ConfigurableParameters {
    public static int THRESHOLD_AS_RUNNING_STATE; // probability * 100
    public static int TIME_TO_REBUILD_FRIEND_LIST; // i.e., Rebuild when time = (STATE_REFRESH_RATE * TIME_TO_REBUILD_FRIEND_LIST)
    public static int STATE_REFRESH_RATE; // milliseconds
    public static int HISTORY_SIZE;

    static {
        String content = "";
        try {
            InputStream in = ConfigurableParameters.class.getResourceAsStream("Configuration.json");
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ( (line = buffReader.readLine()) != null ) {
                // i just like to add newline to make it beautiful when printing it out.
                content += line + "\n"; 
            }

            JSONObject obj = new JSONObject(content);
            THRESHOLD_AS_RUNNING_STATE = Integer.parseInt(obj.getJSONObject("configuration").getString("THRESHOLD_AS_RUNNING_STATE"));
            TIME_TO_REBUILD_FRIEND_LIST = Integer.parseInt(obj.getJSONObject("configuration").getString("TIME_TO_REBUILD_FRIEND_LIST"));
            STATE_REFRESH_RATE = Integer.parseInt(obj.getJSONObject("configuration").getString("STATE_REFRESH_RATE"));
            HISTORY_SIZE = Integer.parseInt(obj.getJSONObject("configuration").getString("HISTORY_SIZE"));
            UtilityClass.logd("Successfully read the configuration file from a json file.");
        }
        catch (IOException e) {
            UtilityClass.logd("I cannot find the fucking configuration file. I'll use default values instead.");
            THRESHOLD_AS_RUNNING_STATE = 0;
            TIME_TO_REBUILD_FRIEND_LIST = 10;
            STATE_REFRESH_RATE = 100;
            HISTORY_SIZE = 20;
        }
    }
}