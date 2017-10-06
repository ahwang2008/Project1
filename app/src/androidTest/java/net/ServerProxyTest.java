package net;

import org.junit.Test;

import request.LoginRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.PersonResult;
import result.RegisterResult;
import server.Server;

import static org.junit.Assert.*;

/**
 * Created by Hwang on 6/19/2017.
 */
public class ServerProxyTest {
    //INVALID REGISTER AND LOGIN
    @Test
    public void login() throws Exception {
        //VALID TESTCASE
        String serverHost = "10.24.64.76"; //REPLACE SERVER HOST HERE FOR TESTING
        String serverPort = "8080";
        String username = "sheila";
        String password = "parker";
        LoginRequest loginRequest = new LoginRequest(username, password);
        ServerProxy serverProxy = new ServerProxy();
        LoginResult loginResult = serverProxy.login(serverHost, serverPort, loginRequest);
        assertTrue(loginResult.getErrorMessage() == null);

        //INVALID TESTCASE
        serverHost = "10.24.64.76";
        username = "invalid";
        password = "login";
        LoginRequest loginRequest1 = new LoginRequest(username, password);
        LoginResult loginResult1 = serverProxy.login(serverHost, serverPort, loginRequest1);
        assertTrue(loginResult1.getErrorMessage() != null);
    }

    @Test
    public void register() throws Exception {
        //VALID TESTCASE
        String serverHost = "10.24.64.76"; //REPLACE SERVER HOST HERE FOR TESTING
        String serverPort = "8080";
        String username = "Camila";
        String password = "parker";
        String email = "sheila@parker.com";
        String firstName = "Sheila";
        String lastName = "Parker";
        String gender = "f";

        RegisterRequest registerRequest = new RegisterRequest(username, password, email, firstName, lastName, gender);
        ServerProxy serverProxy = new ServerProxy();
        RegisterResult registerResult = serverProxy.register(serverHost, serverPort, registerRequest);
        assertTrue(registerResult.getErrorMessage() == null);

        //INVALID TESTCASE
        gender = null;
        RegisterRequest registerRequest1 = new RegisterRequest(username, password, email, firstName, lastName, gender);
        RegisterResult registerResult1 = serverProxy.register(serverHost, serverPort, registerRequest1);
        assertTrue(registerResult1.getErrorMessage() != null);
    }

    @Test
    public void fetchPerson() throws Exception {
        String serverHost = "10.24.64.76"; //REPLACE SERVER HOST HERE FOR TESTING
        String serverPort = "8080";
        String authToken = "SgOEjDwV"; //REPLACE WITH ANY VALID, ALREADY CREATED AUTHTOKEN HERE

        ServerProxy serverProxy = new ServerProxy();
        PersonResult personResult = serverProxy.fetchPerson(serverHost, serverPort, authToken);
        assertTrue(personResult != null);
    }

    @Test
    public void fetchEvent() throws Exception {
        String serverHost = "10.24.64.76"; //REPLACE SERVER HOST HERE FOR TESTING
        String serverPort = "8080";
        String authToken = "SgOEjDwV"; //REPLACE WITH ANY VALID, ALREADY CREATED AUTHTOKEN HERE

        ServerProxy serverProxy = new ServerProxy();
        EventResult eventResult = serverProxy.fetchEvent(serverHost, serverPort, authToken);
        assertTrue(eventResult != null);
    }
}