package net;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

import request.LoginRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.PersonResult;
import result.RegisterResult;

/**
 * Created by Hwang on 6/3/2017.
 */


public class ServerProxy {
    private boolean success;

    public LoginResult login(String serverHost, String serverPort, LoginRequest loginRequest) {

        // This method shows how to send a POST request to a server
        success = false;
        LoginResult loginResult = new LoginResult();
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body

            // Add an auth token to the request in the HTTP "Authorization" header
            //http.addRequestProperty("Authorization", "afj232hj2332");
            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            String jsonStr = gson.toJson(loginRequest);
            // This is the JSON string we will send in the HTTP request body
            // Get the output stream containing the HTTP request body
            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(jsonStr, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Login Succeeded.");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                loginResult = gson.fromJson(respData, LoginResult.class);
                //Reader reader = new InputStreamReader(respBody);
                //LoginResult loginResult = gson.fromJson(reader, LoginResult.class);
                if (loginResult.getErrorMessage() == null)
                {
                    success = true;
                }
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return loginResult;
    }

    public RegisterResult register(String serverHost, String serverPort, RegisterRequest registerRequest) {

        success = false;
        RegisterResult registerResult = new RegisterResult();
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");

            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            Gson gson = new Gson();
            String jsonStr = gson.toJson(registerRequest);

            OutputStream reqBody = http.getOutputStream();
            writeString(jsonStr, reqBody);
            reqBody.close();


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Register Succeeded.");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                registerResult = gson.fromJson(respData, RegisterResult.class);
                //Reader reader = new InputStreamReader(respBody);
                //LoginResult loginResult = gson.fromJson(reader, LoginResult.class);
                if (registerResult.getErrorMessage() == null)
                {
                    success = true;
                }
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return registerResult;
    }

    public PersonResult fetchPerson(String serverHost, String serverPort, String authToken) {

        // This method shows how to send a POST request to a server
        success = false;
        PersonResult personResult = new PersonResult();
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("GET");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(false);	// There is a request body

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authToken);
            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            //String jsonStr = gson.toJson(loginRequest);
            // This is the JSON string we will send in the HTTP request body
            // Get the output stream containing the HTTP request body
            //OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            //writeString(jsonStr, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            //reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Person Fetch Succeeded.");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                personResult = gson.fromJson(respData, PersonResult.class);
                //Reader reader = new InputStreamReader(respBody);
                //LoginResult loginResult = gson.fromJson(reader, LoginResult.class);
                success = true;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return personResult;
    }

    public EventResult fetchEvent(String serverHost, String serverPort, String authToken) {

        // This method shows how to send a POST request to a server
        success = false;
        EventResult eventResult = new EventResult();
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("GET");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(false);	// There is a request body

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authToken);
            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            Gson gson = new Gson();
            //String jsonStr = gson.toJson(loginRequest);
            // This is the JSON string we will send in the HTTP request body
            // Get the output stream containing the HTTP request body
            //OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            //writeString(jsonStr, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            //reqBody.close();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // The HTTP response status code indicates success,
                // so print a success message
                System.out.println("Event Fetch Succeeded.");
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                eventResult = gson.fromJson(respData, EventResult.class);
                //Reader reader = new InputStreamReader(respBody);
                //LoginResult loginResult = gson.fromJson(reader, LoginResult.class);
                success = true;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return eventResult;
    }

    public boolean isSuccess() {
        return success;
    }

    /*
            The readString method shows how to read a String from an InputStream.
        */
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
