package kr.ac.kopo.jeonse.global.geo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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

    public String getLoadNameAddress(double lat, double lon) {
        final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x={0}&y={1}";
        String url = MessageFormat.format(KAKAO_API_URL, String.valueOf(lon), String.valueOf(lat));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 05605d63db818c85ac75264827261f46");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        log.info("url : {}", url);
//        log.info("response : {}", response.getBody());
        // JSON 응답 파싱
        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray documents = jsonObject.getJSONArray("documents");

        if (!documents.isEmpty()) {
            JSONObject addressObject = documents.getJSONObject(0).getJSONObject("address");
            return addressObject.getString("address_name");
        } else {
            return "Address not found";
        }
    }
}
