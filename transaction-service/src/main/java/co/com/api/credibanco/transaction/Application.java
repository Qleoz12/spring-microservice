package co.com.api.credibanco.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories({"co.com.api.credibanco.domain.repository"})
@EntityScan({"co.com.api.credibanco.domain.entity"})
@ComponentScan("co.com.api.credibanco")
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
