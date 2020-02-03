package br.com.planet.config;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket planetApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
                .apis(RequestHandlerSelectors.basePackage("br.com.planet"))
                .paths(regex("/v1.*"))
                .build()
                .apiInfo(metaInfo());
    }
    
    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Planet Microservice | Backend | API REST",
                "Create by : Pedro Henrique Andalecio Gontijo",
                "1.0",
                "",
                new Contact("LinkedIn", "https://www.linkedin.com/in/pedroandalecio/", ""),
                "",
                "", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
    
}
