package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.dto.InfraDTO;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseCheckList;
import kr.ac.kopo.jeonse.domain.jeonse.service.JeonseService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jeonse")
public class JeonseController {

    private final JeonseService jeonseService;

    @PostMapping("/register-doc")
    public ResponseEntity<ByteArrayResource> getRegisterDoc(@RequestParam String address) throws IOException, InterruptedException {
        ByteArrayResource registerDoc = jeonseService.getRegisterDoc(address);
        if (registerDoc == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(registerDoc);
    }

    @PostMapping("/remain")
    public ApiResponse<?> getRemainJeonse(@QueryParam("address") String address,
            @QueryParam("aptName") String aptName) {
        return ApiResponse.onSuccess(jeonseService.getRemainJeonse(address, aptName));
    }

    @GetMapping("/check-list")
    public ApiResponse<JeonseCheckList> jeonseCheckList(@QueryParam("actlNo") String actlNo) {
        return ApiResponse.onSuccess(jeonseService.checkJeonse(actlNo));
    }

    @GetMapping("/infra-list")
    public ApiResponse<List<InfraDTO>> infraList() {
        return ApiResponse.onSuccess(jeonseService.getInfraList());
    }


}
