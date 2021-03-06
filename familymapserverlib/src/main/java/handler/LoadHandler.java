package handler;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import dataAccess.Database;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

/**
 * Created by ahwang13 on 5/22/17.
 */

    /*
        The ClaimRouteHandler is the HTTP handler that processes
        incoming HTTP requests that contain the "/routes/claim" URL path.

        Notice that ClaimRouteHandler implements the HttpHandler interface,
        which is define by Java.  This interface contains only one method
        named "handle".  When the HttpServer object (declared in the Server class)
        receives a request containing the "/routes/claim" URL path, it calls
        ClaimRouteHandler.handle() which actually processes the request.
    */

public class LoadHandler extends Handler implements HttpHandler {

        // Handles HTTP requests containing the "/routes/claim" URL path.
        // The "exchange" parameter is an HttpExchange object, which is
        // defined by Java.
        // In this context, an "exchange" is an HTTP request/response pair
        // (i.e., the client and server exchange a request and response).
        // The HttpExchange object gives the handler access to all of the
        // details of the HTTP request (Request type [GET or POST],
        // request headers, request body, etc.).
        // The HttpExchange object also gives the handler the ability
        // to construct an HTTP response and send it back to the client
        // (Status code, headers, response body, etc.).
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // This handler allows a "Ticket to Ride" player to claim ability
            // route between two cities (part of the Ticket to Ride game).
            // The HTTP request body contains a JSON object indicating which
            // route the caller wants to claim (a route is defined by two cities).
            // This implementation is clearly unrealistic, because it
            // doesn't actually do anything other than print out the received JSON string.
            // It is also unrealistic in that it accepts only one specific
            // hard-coded auth token.
            // However, it does demonstrate the following:
            // 1. How to get the HTTP request type (or, "method")
            // 2. How to access HTTP request headers
            // 3. How to read JSON data from the HTTP request body
            // 4. How to return the desired status code (200, 404, etc.)
            //		in an HTTP response
            // 5. How to check an incoming HTTP request for an auth token

            boolean success = false;

            try {
                // Determine the HTTP request type (GET, POST, etc.).
                // Only allow POST requests for this operation.
                // This operation requires a POST request, because the
                // client is "posting" information to the server for processing.
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                Gson gson = new Gson();
                LoadRequest loadRequest = gson.fromJson(reqData, LoadRequest.class);
                LoadService loadService = new LoadService();
                LoadResult lr = null;

                try {
                    lr = loadService.load(loadRequest);
                } catch (SQLException e) {
                    lr.setMessage("Invalid request data");
                    e.printStackTrace();
                } catch (Database.DatabaseException e) {
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
