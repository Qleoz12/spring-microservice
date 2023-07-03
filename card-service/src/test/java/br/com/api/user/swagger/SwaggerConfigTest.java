package br.com.api.user.swagger;

import org.junit.jupiter.api.Test;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// Add necessary imports and annotations for JUnit

class SwaggerConfigTest {

    @Test
    void testApi() {
        // Create an instance of SwaggerConfig
        SwaggerConfig swaggerConfig = new SwaggerConfig();

        // Call the api() method
        Docket docket = swaggerConfig.api();

        // Verify that the returned Docket object is not null
        assertNotNull(docket);
        // Add more assertions to verify the configuration of the Docket object if needed
    }

    @Test
    void testApiInfo() {
        // Create an instance of SwaggerConfig
        SwaggerConfig swaggerConfig = new SwaggerConfig();

        // Call the apiInfo() method
        ApiInfo apiInfo = swaggerConfig.apiInfo();

        // Verify that the returned ApiInfo object is not null
        assertNotNull(apiInfo);
        // Add more assertions to verify the contents of the ApiInfo object if needed
    }

    // Write similar tests for the remaining private methods if necessary
}
