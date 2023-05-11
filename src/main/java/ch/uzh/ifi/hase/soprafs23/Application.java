package ch.uzh.ifi.hase.soprafs23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {

      System.setProperty("API_KEYS", "9dd751e9,7470f45a98ccc467dc3c043b1f997cf4,376a71b1,46ef2c8c088e63f038d5b2e0d43cf066");
      System.setProperty("DB_PASSWORD", "v2Rr,[3#zhsRc`F[S454'_*35'2$¨ü¨0509anxsd5%&dfg%/&34|¢§°6cxv ljr'23'1");
      SpringApplication.run(Application.class, args);
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String helloWorld() {
    return "The application is running. Yay";
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://sopra-fs23-group-34-client.oa.r.appspot.com").allowedMethods("*");
      }
    };
  }
}
