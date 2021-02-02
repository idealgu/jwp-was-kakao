package webserver;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        Request request = Request.of(in);

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getUri());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1234", request.getParameter("userId"));
        assertEquals("1234", request.getParameter("password"));
        assertEquals("박재성", request.getParameter("name"));
        assertEquals("email@email.com", request.getParameter("email"));
    }

    @Test
    void getMethodTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = Request.of(in);
        assertEquals(request.getMethod(), "GET");
    }

    @Test
    void getUriTest() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        Request request = Request.of(in);
        assertEquals(request.getUri(), "/index.html");
    }

}
