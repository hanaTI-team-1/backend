package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.domain.BuildingRegister;
import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseRateDto;
import kr.ac.kopo.jeonse.domain.jeonse.service.BuildingRegisterService;
import kr.ac.kopo.jeonse.domain.jeonse.service.JeonseService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jeonse")
public class JeonseController {

    private final JeonseService jeonseService;
    private final BuildingRegisterService buildingRegisterService;

    @GetMapping("/{atclNo}")
    public Jeonse getJeonse(@PathVariable String atclNo) {
        return jeonseService.getJeonseByAtclNo(atclNo);
    }


    @GetMapping("/remain")
    public ApiResponse<?> getRemainJeonse(@QueryParam("address") String address, @QueryParam("aptName") String aptName) {
        return ApiResponse.onSuccess(jeonseService.getRemainJeonse(address, aptName));
    }

    @GetMapping("/building-register")
    public ApiResponse<List<BuildingRegister>> getBuildingRegister(@QueryParam("road_address")String road_address) {
        return ApiResponse.onSuccess(buildingRegisterService.getBuildingRegisterByAddress(road_address));
    }

    @GetMapping("/rate/{atclNo}")
    public ApiResponse<JeonseRateDto> getJeonseRate(@PathVariable String atclNo) {
        System.out.println(atclNo);
        return ApiResponse.onSuccess(jeonseService.calculateJeonseRate(atclNo));
    }


}

