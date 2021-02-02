package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Response {
    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> contentType = new HashMap<>();
    private Map<String, String> contentLocation = new HashMap<>();

    private Response(OutputStream out) {
        dos = new DataOutputStream(out);
        initializeExtension();
    }

    public static Response of(OutputStream dos) {
        return new Response(dos);
    }

    private void initializeExtension() {
        contentType.put("js", "text/javascript");
        contentType.put("css", "text/css");

        contentLocation.put("ico", "./templates/");
        contentLocation.put("html", "./templates/");
    }

    private Optional<String> getContentType(String extension) {
        return Optional.ofNullable(contentType.get(extension));
    }

    private Optional<String> getContentLocation(String extension) {
        return Optional.ofNullable(contentLocation.get(extension));
    }

    public void forward(String location) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getContentLocation(getExtension(location))
                .orElse("./static/") + location);
        addHeader("Content-Length", Integer.toString(body.length));

        writeResponse200(body);
    }

    private String getExtension(String location) {
        String[] arr = location.split("\\.");
        String extension = arr[arr.length - 1];
        String contentType = getContentType(extension).orElse("text/html");
        addHeader("Content-Type", contentType + ";charset=utf-8");
        return extension;
    }

    private void writeResponse200(byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void sendRedirect(String location) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found\r\n");
        addHeader("Location", location);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void userListForward(String location) throws IOException {
        getExtension(location);

        String users = getUsers();
        byte[] body = users.getBytes();
        addHeader("Content-Length", Integer.toString(body.length));
        writeResponse200(body);
    }

    private String getUsers() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        Collection<User> users = DataBase.findAll();

        return template.apply(users);
    }
}
