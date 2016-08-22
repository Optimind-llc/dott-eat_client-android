package servercalls;

/**
 * Created by hugh on 2016-08-22.
 */
public class SCClientFacade implements ServerCallAPI {
    private static SCClientFacade ourInstance = new SCClientFacade();

    public static SCClientFacade getInstance() {
        return ourInstance;
    }

    private SCClientFacade() {
    }
}
