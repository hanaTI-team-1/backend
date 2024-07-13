package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.domain.BuildingRegister;
import kr.ac.kopo.jeonse.domain.jeonse.service.BuildingRegisterService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/building-registers")
@RestController
public class BuildingRegisterController {
    private final BuildingRegisterService buildingRegisterService;

    @GetMapping("/test")
    public ApiResponse<String> test() {
        return ApiResponse.onSuccess("테스트");

    }

    @GetMapping
    public ApiResponse<List<BuildingRegister>> getBuildingRegister(@QueryParam("address") String address) {
        System.out.println("주소 들어옴 "  + address);
        List<BuildingRegister> buildingRegister = buildingRegisterService.getBuildingRegisterByAddress(address);
        return ApiResponse.onSuccess(buildingRegister);

    }
}
