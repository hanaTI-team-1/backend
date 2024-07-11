package kr.ac.kopo.jeonse.domain.batch.service;

import kr.ac.kopo.jeonse.domain.batch.mapper.BatchMapper;
import kr.ac.kopo.jeonse.domain.batch.vo.CrawlingVO;
import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.global.geo.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {
    private final GeoLocationService geoLocationService;
    private final BatchMapper batchMapper;

    @Transactional(rollbackFor = Exception.class)
    public String updateJeonse(List<CrawlingVO> crawlingVO) {
        batchMapper.deleteAllJeonse();
        ArrayList<Jeonse> jeonseList = new ArrayList<>();
        for(CrawlingVO crawling : crawlingVO){
            Jeonse jeonse = crawling.convertToJeonse(crawling);
            jeonse.setAddress(geoLocationService.getLoadNameAddress(crawling.getLat(), crawling.getLng()));
            jeonseList.add(jeonse);
        }
        batchMapper.updateJeonse(jeonseList);
//        log.info("jeonseList : {}", jeonseList);
        return null;
    }




}
