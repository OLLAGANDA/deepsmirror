package com.deepmirror.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 문서화 설정
 *
 * <p>SpringDoc OpenAPI 3를 사용하여 REST API 문서를 자동 생성합니다.</p>
 *
 * <h3>접속 정보:</h3>
 * <ul>
 *   <li>Swagger UI: http://localhost:8080/swagger-ui.html</li>
 *   <li>OpenAPI JSON: http://localhost:8080/api-docs</li>
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 문서 설정
     *
     * @return OpenAPI 객체 (제목, 버전, 설명 포함)
     */
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("DeepMirror API")
                .version("v1.0")
                .description("Big Five 성격 분석 서비스 API 문서");

        return new OpenAPI()
                .info(info);
    }
}

