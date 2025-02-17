package org.trandy.trandy_server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "AI API 명세서", description = "AI API 명세서", version = "v1")
)
public class SwaggerConfig {
    // http://localhost:8080/swagger-ui/index.html#/

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("Default Template Swagger")
                .description("Template Swagger")
                .contact(new Contact());

        // 스웨거에서 인증(Authorize) 버튼 세팅
        String jwtSchemeName = "Authorization";
        String refreshName = "Refresh";

        // API 요청 헤더에 인증 정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName); // 헤더에 토큰 포함
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName).addList(refreshName); // 헤더에 토큰 포함

        // SecurityScheme 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(refreshName, new SecurityScheme()
                        .name(refreshName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("REFRESH"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}