package handler;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import dataAccess.Database;
import request.FillRequest;
import result.FillResult;
import service.FillService;

/**
 * Created by ahwang13 on 5/22/17.
 */

public class FillHandler extends Handler implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            boolean success = false;

            try {
                // Determine the HTTP request type (GET, POST, etc.).
                // Only allow POST requests for this operation.
                // This operation requires a POST request, because the
                // client is "posting" information to the server for processing.
                /*
                String command = exchange.getRequestURI().toString();
                String[] params = command.split("/");
                int hello = params.length;
                if(params.length <= 2)
                {
                    //Doesn't specify a username for the fill function
                }
                //String fill = params[0];
                String username = params[0];
                int generations = 0;
                if (params[1] == null)
                {
                    generations = 4;
                }
                else
                {
                    generations = Integer.parseInt(params[1]);
                }

                if(command.endsWith(""));
                */

                String command = exchange.getRequestURI().toString();
                String[] params = command.split("/");
                String inputUsername = "";
                String inputGenerations = "";
                if (params.length == 4)
                {
                    inputUsername = params[2];
                    inputGenerations = params[3];
                }
                else if(params.length == 3)
                {
                    inputUsername = params[2];
                    inputGenerations = "4";
                }

                FillRequest fillRequest = new FillRequest(inputUsername, inputGenerations);
                FillService fillService = new FillService();
                FillResult fs = null;

                try {
                    fs = fillService.fill(fillRequest);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Database.DatabaseException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                String jsonStr = gson.toJson(fs);

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