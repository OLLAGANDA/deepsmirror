package com.deepmirror.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 애플리케이션 공통 설정
 *
 * <p>Spring Bean 등록 및 공통 설정을 관리합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Configuration
public class AppConfig {

    /**
     * RestTemplate Bean 등록
     *
     * <p>HTTP 요청을 수행하기 위한 RestTemplate 인스턴스를 생성합니다.
     * Discord Webhook 알림 등 외부 API 호출에 사용됩니다.</p>
     *
     * @return RestTemplate 인스턴스
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
