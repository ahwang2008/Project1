package request;

/**
 * Created by ahwang13 on 5/19/17.
 */

/**
 * Holds the parameters for the Login request.
 */
public class LoginRequest {

    private String userName;
    private String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //Reader reader = new InputStreamReader(httypExch.getRequestBody());
    //LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
