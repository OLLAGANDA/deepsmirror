package com.deepmirror.server.controller;

import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.service.ResultService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Thymeleaf 뷰를 렌더링하는 Controller
 *
 * <p>사용자에게 보여지는 HTML 페이지를 렌더링합니다.
 * REST API가 아닌 서버 사이드 렌더링을 담당합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Controller
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    private final ResultService resultService;
    private final ObjectMapper objectMapper;

    /**
     * 생성자 주입
     *
     * @param resultService 성격 분석 결과 서비스
     * @param objectMapper JSON 직렬화/역직렬화 도구
     */
    public ViewController(ResultService resultService, ObjectMapper objectMapper) {
        this.resultService = resultService;
        this.objectMapper = objectMapper;
    }

    /**
     * 메인 설문 화면 렌더링
     *
     * @return index.html 템플릿
     */
    @GetMapping("/")
    public String index() {
        log.debug("메인 페이지 요청");
        return "index";
    }

    /**
     * 성격 분석 결과 조회 화면 렌더링
     *
     * <p>UUID로 결과를 조회하고, detailScores JSON을 파싱하여
     * Thymeleaf 모델에 추가합니다. 결과가 없으면 메인 페이지로 리다이렉트합니다.</p>
     *
     * @param id 결과 고유 식별자 (UUID)
     * @param model Thymeleaf 모델 객체
     * @return result.html 템플릿 또는 리다이렉트
     */
    @GetMapping("/results/{id}")
    public String getResultsView(@PathVariable UUID id, Model model) {
        log.debug("결과 페이지 요청 - ID: {}", id);

        Optional<ResultResponse> result = resultService.getResult(id);

        if (result.isPresent()) {
            ResultResponse resultData = result.get();
            model.addAttribute("result", resultData);

            Map<String, Integer> detailMap = parseDetailScores(resultData.getDetailScores());
            model.addAttribute("detailMap", detailMap);

            log.info("결과 페이지 렌더링 완료 - ID: {}, 닉네임: {}", id, resultData.getNickname());
            return "result";
        } else {
            log.warn("결과를 찾을 수 없음 - ID: {}", id);
            return "redirect:/";
        }
    }

    /**
     * detailScores JSON 문자열을 Map으로 파싱
     *
     * <p>파싱 실패 시 빈 Map을 반환하여 화면 렌더링에 영향을 주지 않습니다.</p>
     *
     * @param detailScoresJson JSON 형식의 세부 점수 문자열
     * @return 파싱된 Map 또는 빈 Map
     */
    private Map<String, Integer> parseDetailScores(String detailScoresJson) {
        if (detailScoresJson == null || detailScoresJson.isEmpty()) {
            return new HashMap<>();
        }

        try {
            return objectMapper.readValue(
                    detailScoresJson,
                    new TypeReference<Map<String, Integer>>() {}
            );
        } catch (Exception e) {
            log.error("DetailScores JSON 파싱 실패: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }
}

