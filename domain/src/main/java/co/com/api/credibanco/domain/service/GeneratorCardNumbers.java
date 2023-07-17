package co.com.api.credibanco.domain.service;

import org.springframework.stereotype.Service;

@Service
public class GeneratorCardNumbers {

    public static String generate(String productId) {

        //https://stackoverflow.com/questions/28742702/generate-secure-random-number-uniformly-over-a-range-in-java FIX Inclusive
        int digits = 10;
        long lowerLimit = (long) Math.pow(10, digits - 1);
        long upperLimit = (long) Math.pow(10, digits) - 1;

        var randomNumber =lowerLimit + (int)(Math.random() * ((upperLimit - lowerLimit) + 1));

        return productId.concat(String.valueOf(randomNumber));

    }
     private GeneratorCardNumbers(){

     }

}
