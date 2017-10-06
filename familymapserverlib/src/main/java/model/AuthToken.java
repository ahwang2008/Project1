package model;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Used to store the authentication tokens in memory.
 */
public class AuthToken {

    private String userName; //Username
    private String authID;
    private String exp_time;
    private String errorMessage;

    public AuthToken(String userName, String authID, String exp_time, String errorMessage) {
        this.userName = userName;
        this.authID = authID;
        this.exp_time = exp_time;
        this.errorMessage = errorMessage;
    }

    public AuthToken() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
