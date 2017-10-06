package handler;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.sun.net.httpserver.*;
import com.google.gson.*;

import dataAccess.Database;
import model.User;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import service.LoginService;

/**
 * Created by ahwang13 on 5/22/17.
 */

    /*
        The ClaimRouteHandler is the HTTP handler that processes
        incoming HTTP requests that contain the "/routes/claim" URL path.

        Notice that ClaimRouteHandler implements the HttpHandler interface,
        which is define by Java.  This interface contains only one method
        named "handle".  When the HttpServer object (declared in the server.Server class)
        receives a request containing the "/routes/claim" URL path, it calls
        ClaimRouteHandler.handle() which actually processes the request.
    */
    //class ClaimRouteHandler implements HttpHandler {
public class LoginHandler extends Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            boolean success = false;

            try {

                if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                    // Get the HTTP request headers
                    //Headers reqHeaders = exchange.getRequestHeaders();
                    // Check to see if an "Authorization" header is present
                    //if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    //String authToken = reqHeaders.getFirst("Authorization");
                    // Verify that the auth token is the one we're looking for
                    // (this is not realistic, because clients will use different
                    // auth tokens over time, not the same one all the time).
                    //if (authToken.equals("afj232hj2332")) {

                    // Extract the JSON string from the HTTP request body

                    // Get the request body input stream
                    InputStream reqBody = exchange.getRequestBody();
                            // Read JSON string from the input stream
                    String reqData = readString(reqBody);

                            // Display/log the request JSON data
                    Gson gson = new Gson();

                    LoginRequest loginRequest = gson.fromJson(reqData, LoginRequest.class);
                    LoginService loginService = new LoginService();
                    LoginResult lr = null;

                    try
                    {
                        lr = loginService.login(loginRequest);
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    catch (Database.DatabaseException e)
                    {
                        e.printStackTrace();
                    }

                    String jsonStr = gson.toJson(lr);
                    // Start sending the HTTP response to the client, starting with
                    // the status code and any defined headers.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    // Now that the status code and headers have been sent to the client,
                    // next we send the JSON data in the HTTP response body.

                    // Get the response body output stream.
                    OutputStream respBody = exchange.getResponseBody();
                    // Write the JSON string to the output stream.
                    writeString(jsonStr, respBody);
                    // Close the output stream.  This is how Java knows we are done
                    // sending data and the response is complete/
                    respBody.close();

                    success = true;
                }

                if (!success) {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    // We are not sending a response body, so close the response body
                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();
                }
            }
            catch (IOException e) {
                // Some kind of internal error has occurred inside the server (not the
                // client's fault), so we return an "internal server error" status code
                // to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();

                // Display/log the stack trace
                e.printStackTrace();
            }
        }
}
