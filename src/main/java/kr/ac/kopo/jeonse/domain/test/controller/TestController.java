package kr.ac.kopo.jeonse.domain.test.controller;

import kr.ac.kopo.jeonse.domain.test.service.TestService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import kr.ac.kopo.jeonse.global.payload.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/test")
    public ApiResponse<?> test(@RequestParam String flag) {
        if (flag.equals("test")){
            return ApiResponse.onSuccess(testService.test());
        }
        else {
            return ApiResponse.onFailure(String.valueOf(404), ErrorStatus._BAD_REQUEST.getMessage(), null);
        }
    }
}
