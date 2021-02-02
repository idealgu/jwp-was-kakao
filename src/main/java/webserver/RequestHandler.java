package webserver;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Map<String, Controller> controllers = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initalizeController();
    }

    private void initalizeController() {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list.html", new ListUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("default", new DefaultController());
    }

    private Optional<Controller> getController(String uri) {
        return Optional.ofNullable(controllers.get(uri));
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = Request.of(in);
            Response response = Response.of(out);

            Controller controller = getController(request.getUri()).orElse(controllers.get("default"));
            controller.service(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
