public interface ConfigurableConstants {
	public static final int THRESHOLD_AS_RUNNING_STATE = 90; // probability * 100
	public static final int TIME_TO_REBUILD_FRIEND_LIST = 10; // i.e., Rebuild when time = (STATE_REFRESH_RATE * TIME_TO_REBUILD_FRIEND_LIST)
	public static final int STATE_REFRESH_RATE = 100; // milliseconds
}