package com.deepmirror.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Discord Webhook을 통해 알림을 전송하는 서비스
 *
 * <p>피드백 제출 등 특정 이벤트 발생 시 Discord 채널로 알림을 전송합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Service
public class DiscordNotificationService {

    private static final Logger log = LoggerFactory.getLogger(DiscordNotificationService.class);

    private final String webhookUrl;
    private final RestTemplate restTemplate;

    /**
     * 생성자 주입 (Constructor Injection)
     *
     * @param webhookUrl Discord Webhook URL (application.yml에서 주입)
     * @param restTemplate HTTP 요청을 위한 RestTemplate
     */
    public DiscordNotificationService(
            @Value("${discord.webhook-url}") String webhookUrl,
            RestTemplate restTemplate) {
        this.webhookUrl = webhookUrl;
        this.restTemplate = restTemplate;
    }

    /**
     * Discord로 알림 메시지 전송
     *
     * @param message 전송할 메시지 내용
     */
    public void sendNotification(String message) {
        // 웹훅 URL이 설정되지 않았으면 알림을 보내지 않음
        if (webhookUrl == null || webhookUrl.trim().isEmpty()) {
            log.debug("Discord 웹훅 URL이 설정되지 않아 알림을 전송하지 않습니다.");
            return;
        }

        try {
            // Discord Webhook 메시지 포맷: {"content": "메시지 내용"}
            Map<String, String> payload = new HashMap<>();
            payload.put("content", message);

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HTTP 요청 엔티티 생성
            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            // Discord Webhook으로 POST 요청 전송
            restTemplate.postForEntity(webhookUrl, request, String.class);

            log.info("Discord 알림 전송 성공");
        } catch (Exception e) {
            // 전송 실패 시 에러 로그만 남기고, 메인 로직에는 영향을 주지 않음
            log.error("Discord 알림 전송 실패: {}", e.getMessage(), e);
        }
    }
}
