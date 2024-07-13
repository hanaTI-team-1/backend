package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.service.JeonseService;
import kr.ac.kopo.jeonse.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.QueryParam;
import java.io.IOException;

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

    @PostMapping("/register-doc")
    public ResponseEntity<ByteArrayResource> getRegisterDoc(@RequestParam String address) throws IOException {
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
    public ApiResponse<?> getRemainJeonse(@QueryParam("address") String address, @QueryParam("aptName") String aptName) {
        return ApiResponse.onSuccess(jeonseService.getRemainJeonse(address, aptName));
    }


}

