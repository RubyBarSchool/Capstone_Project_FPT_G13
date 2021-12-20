package com.university.fpt.acf.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver) {
        //************************************
        // cấu hình swagger
        //************************************
        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        newRule(
                                typeResolver.resolve(List.class, LocalDateTime.class),
                                typeResolver.resolve(List.class, String.class)
                        ),
                        newRule(
                                typeResolver.resolve(List.class, LocalDate.class),
                                typeResolver.resolve(List.class, String.class)
                        )
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.university"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        //************************************
        // trả về thông tin swagger
        //************************************
        return new ApiInfoBuilder().title("Capstone Project of FPT University")
                .description("Project Spring Boot Anh Chung Furniture")
                .contact(new Contact("truongtv", "https://www.facebook.com/i.am.money.addict/", "truongtv1399it@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}
