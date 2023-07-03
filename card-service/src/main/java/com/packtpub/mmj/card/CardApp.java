package com.packtpub.mmj.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication
@Import(CardAppConfig.class)
@EnableEurekaClient
public class CardApp {

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(CardApp.class, args);
  }
}
