package by.vk.ase.pay;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.info.BuildProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition(info = @Info(title = "ASEPAY Service", version = "1.0.0-RC1", description = "Documentation APIs v1.0"))
@EnableJpaAuditing
@Slf4j
public class Application {

  public static void main(String[] args) {
    final var context = SpringApplication.run(Application.class, args);
    final var properties = context.getBean(BuildProperties.class);
    log.info("[SERVICE] Application version {}", properties.getVersion());
  }

}
