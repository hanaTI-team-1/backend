package kr.ac.kopo.jeonse.global.geo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoLocationService {
    private final RestTemplate restTemplate;

    private String getLoadNameAddress(double lat, double lon) {
        final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x={0}&y={1}";
        String url = MessageFormat.format(KAKAO_API_URL, String.valueOf(lon), String.valueOf(lat));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 06f4209ef83a0d441ebcff2a3f8bc2b8");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        log.info("url : {}", url);
        log.info("response : {}", response.getBody());
        return null;
    }
}
