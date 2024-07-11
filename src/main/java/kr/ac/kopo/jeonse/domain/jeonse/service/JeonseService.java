package kr.ac.kopo.jeonse.domain.jeonse.service;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.JeonseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JeonseService {

    @Autowired
    private JeonseMapper jeonseMapper;

    public Jeonse getJeonseByAtclNo(String atclNo) {
        return jeonseMapper.selectJeonseByAtclNo(atclNo);
    }

    public double calculateJeonseRate(String atclNo) {
        Jeonse jeonse = getJeonseByAtclNo(atclNo);
        if (jeonse != null && jeonse.getPrc() > 0) {
            return (double) jeonse.getRentPrc() / jeonse.getPrc() * 100;
        } else {
            throw new IllegalArgumentException("Invalid article number or price.");
        }
    }
}

