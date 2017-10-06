package request;

/**
 * Created by ahwang13 on 5/19/17.
 */


/**
 * Holds the parameters for the fill request.
 */
public class FillRequest {
    private String userName;
    private String generations;

    public FillRequest(String userName, String generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGenerations() {
        return generations;
    }

    public void setGenerations(String generations) {
        this.generations = generations;
    }
}
