package handler;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.nio.file.*;

import com.sun.net.httpserver.*;
/**
 * Created by ahwang13 on 5/22/17.
 */

public class DefaultHandler extends Handler implements HttpHandler {
    public void handle(HttpExchange exchange) throws FileNotFoundException, IOException {
        String command = exchange.getRequestURI().toString();
        System.out.println("Command received: " + command);
        InputStream in = exchange.getRequestBody();
        //OutputStream os = exchange.getResponseBody();
        OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody());
        FileInputStream fis = new FileInputStream(new File("web/index.html"));
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


        //String filePathStr = "the/path/of/a/file";
        //Path filePath = FileSystems.getDefault().getPath(filePathStr);
        //Files.copy(filePath, httpExch.getResponseBody());

        String file = "";
        if (command.equals("/"))
        {
            file = "web/index.html"; //DO I NEED TO INCLUDE THE FAVICON FILES AS WELL??
        }
        else if (command.equals("/css/main.css"))
        {
            file = "web/css/main.css";
        }
        else if (command.equals("/favicon.ico"))
        {
            file = "web/favicon.ico";
        }
        Scanner S = null;
        try
        {
            S = new Scanner(new FileReader(file));
        }
        catch(IOException e)
        {
            S = new Scanner(new FileReader("HTML/404.html"));
        }
        StringBuilder str = new StringBuilder();
        while (S.hasNextLine())
        {
            //str.append(fis);
            str.append(S.nextLine() + "\n");
        }
        S.close();
        os.write(str.toString()); //Maybe include a while loop to use a buffer so that we do it piece by piece instead of all at once since the file is super large
        os.close();
    }
}
