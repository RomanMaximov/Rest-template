package demo;

import demo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class RestTemplateApplication extends SpringBootServletInitializer {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        SpringApplication.run(RestTemplateApplication.class, args);
        String cookies = getCookie();
        System.out.println("Cookies: " + cookies);

        String code = addUser(cookies) + updateUser(cookies) + deleteUser(cookies);
        System.out.println("Code: " + code);

    }

    private static String getCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate
                .exchange("http://91.241.64.178:7081/api/users", HttpMethod.GET, new HttpEntity<>(headers), String.class)
                .getHeaders()
                .getFirst("set-cookie");
    }

    private static String addUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate
                .exchange("http://91.241.64.178:7081/api/users", HttpMethod.POST, new HttpEntity<>(new User(3L, "James", "Brown", (byte) 35), headers), String.class)
                .getBody();
    }

    private static String updateUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate
                .exchange("http://91.241.64.178:7081/api/users", HttpMethod.PUT, new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 35), headers), String.class)
                .getBody();
    }

    private static String deleteUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate
                .exchange("http://91.241.64.178:7081/api/users" + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class)
                .getBody();
    }
}
