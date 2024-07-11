package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.service.JeonseService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jeonse")
public class JeonseController {

    private final JeonseService jeonseService;

    @GetMapping("/{atclNo}")
    public Jeonse getJeonse(@PathVariable String atclNo) {
        return jeonseService.getJeonseByAtclNo(atclNo);
    }

    @GetMapping("/rate/{atclNo}")
    public double getJeonseRate(@PathVariable String atclNo) {
        return jeonseService.calculateJeonseRate(atclNo);
    }


    @GetMapping("/remain")
    public ApiResponse<?> getRemainJeonse(@QueryParam("address") String address, @QueryParam("aptName") String aptName) {
        return ApiResponse.onSuccess(jeonseService.getRemainJeonse(address, aptName));
    }


}

