package handler;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import dataAccess.Database;
import result.ClearResult;
import service.ClearService;
import sun.net.www.http.HttpCaptureOutputStream;

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
public class ClearHandler extends Handler implements HttpHandler {

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

            //String command = exchange.getRequestURI().toString();
            //System.out.println("    Received URI: " + command);
            try{
                Database db = new Database();
                ClearService clearService = new ClearService();
                ClearResult cr = new ClearResult();
                try{
                    clearService.clear();
                    cr.setMessage("Clear succeeded.");
                }
                catch (SQLException e) {
                    cr.setMessage("Clear failed.");
                    e.printStackTrace();
                }
                catch (Database.DatabaseException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                String jsonStr = gson.toJson(cr);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream respBody = exchange.getResponseBody();
                writeString(jsonStr, respBody);
                respBody.close();
                success = true;

                if (!success)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
            catch (IOException e)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().close();
                e.printStackTrace();
            }

            /*
            try
            {
                db.openConnection();
                //db.resetDB(true);
                db.closeConnection(true);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                db.closeConnection(false);
            } catch (Database.DatabaseException e) {
                e.printStackTrace();
            }
            */

            //sendOutData(makeMessage("Clear succeeded."), exchange);

        }
    }
