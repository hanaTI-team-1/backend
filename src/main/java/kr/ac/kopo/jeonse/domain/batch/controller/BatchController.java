package kr.ac.kopo.jeonse.domain.batch.controller;

import kr.ac.kopo.jeonse.domain.batch.service.BatchService;
import kr.ac.kopo.jeonse.domain.batch.vo.CrawlingVO;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BatchController {
    private final BatchService batchService;

    @PostMapping("/batch")
    public ApiResponse<?> updateJeonse(@RequestBody List<CrawlingVO> crawlingVO) {
        return ApiResponse.onSuccess(batchService.updateJeonse(crawlingVO));
    }
}
