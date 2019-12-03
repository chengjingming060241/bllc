package com.gizwits.boot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

/**
*@Author rongmc
*@Date 2017/5/24 19:52
*swagger2配置,能否发现接口,就是在这里通过正则表达式匹配的
*/
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(ApiInfoConfig.class)
public class SwaggerConfiguration {

    @Bean
    public Docket springfoxDocket(ApiInfoConfig apiInfo) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.ignoredParameterTypes(ApiIgnore.class);
        docket.globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                .name("Version").description("接口版本号").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build()));
        docket.apiInfo(apiInfo(apiInfo));
        docket.pathMapping("/").select()
        .apis(RequestHandlerSelectors.any())
        .paths(regex("^.*(?<!error)$")).build();
        return docket;
    }

    @Bean
    ApiInfo apiInfo(ApiInfoConfig apiInfoconfig) {
        ContactInfo contact = apiInfoconfig.getContact();
        ApiInfo apiInfo = new ApiInfo(apiInfoconfig.getTitle(),
                apiInfoconfig.getDescription(),
                apiInfoconfig.getVersion(),
                apiInfoconfig.getTermsOfServiceUrl(),
                new Contact(contact.getName(),
                        contact.getUrl(),
                        contact.getEmail()),
                apiInfoconfig.getLicense(),
                apiInfoconfig.getLicenseUrl());
        return apiInfo;
    }



}
