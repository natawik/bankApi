package bootcamp.bankApi.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

abstract class AbstractHandler implements HttpHandler {

    public String getRequest(HttpExchange httpExchange) {
        String request = "";
        try (InputStream in = httpExchange.getRequestBody()) {
            byte[] buffer = new byte[32 * 1024];
            int readBytes = in.read(buffer);
            request = new String(buffer, 0, readBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void sendResponse(String response, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        try (OutputStream out = httpExchange.getResponseBody()) {
            out.write(response.getBytes());
        }
    }
}
