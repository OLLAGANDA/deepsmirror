package com.deepmirror.server.controller;

import com.deepmirror.server.dto.ResultCreateRequest;
import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 성격 분석 결과 관련 REST API Controller
 *
 * <p>클라이언트로부터 Big Five 성격 검사 점수를 받아
 * AI 분석을 수행하고 결과를 저장하는 엔드포인트를 제공합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    private final ResultService resultService;

    /**
     * 생성자 주입
     *
     * @param resultService 성격 분석 결과 서비스
     */
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * 성격 분석 결과 저장
     *
     * <p>클라이언트가 전송한 Big Five 점수를 받아 AI 분석을 수행하고
     * 데이터베이스에 저장한 후 결과를 반환합니다.</p>
     *
     * @param request 성격 검사 점수 및 닉네임을 담은 요청 객체
     * @return 저장된 결과 (ID, AI 분석 포함)
     */
    @PostMapping
    public ResponseEntity<ResultResponse> createResult(@RequestBody ResultCreateRequest request) {
        ResultResponse response = resultService.createResult(request);
        return ResponseEntity.ok(response);
    }
}

