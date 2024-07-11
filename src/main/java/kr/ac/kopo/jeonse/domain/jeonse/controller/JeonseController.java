package kr.ac.kopo.jeonse.domain.jeonse.controller;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.service.JeonseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jeonse")
public class JeonseController {

    @Autowired
    private JeonseService jeonseService;

    @GetMapping("/{atclNo}")
    public Jeonse getJeonse(@PathVariable String atclNo) {
        return jeonseService.getJeonseByAtclNo(atclNo);
    }

    @GetMapping("/rate/{atclNo}")
    public double getJeonseRate(@PathVariable String atclNo) {
        return jeonseService.calculateJeonseRate(atclNo);
    }
}

