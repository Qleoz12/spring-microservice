package co.com.api.credibanco.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class GeneratorCardNumbers {

    public static String generate(String productId) {
        SecureRandom secureRandom = new SecureRandom();
        int digits = 10;
        long randomNumbers = secureRandom.nextInt((int) Math.pow(10, digits));
        return productId.concat(String.valueOf(randomNumbers));
    }
     private GeneratorCardNumbers(){

     }
}
