package kr.ac.kopo.jeonse.domain.batch.service;

import kr.ac.kopo.jeonse.domain.batch.vo.CrawlingVO;
import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.global.geo.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {
    private final GeoLocationService geoLocationService;

    public String updateJeonse(List<CrawlingVO> crawlingVO) {
        ArrayList<Jeonse> jeonseList = new ArrayList<>();
        for(CrawlingVO crawling : crawlingVO){
            jeonseList.add(crawling.convertToJeonse(crawling));
//            geoLocationService.
        }
        log.info("jeonseList : {}", jeonseList);
        return null;
    }




}
