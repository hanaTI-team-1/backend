package kr.ac.kopo.jeonse.domain.jeonse.service;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.JeonseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JeonseService {

    private final JeonseMapper jeonseMapper;

    private final RestTemplate restTemplate;

    private static final String API_KEY = "65cc914ef40df38979142db76be2b32f";

    private static final String EXTERNAL_API_URL = "https://apick.app/rest/iros/1";

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
    public List<Jeonse> getRemainJeonse(String address, String aptName) {
        return jeonseMapper.selectRemainJeonse(address, aptName);
    }
}

