package kr.ac.kopo.jeonse.domain.jeonse.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.jeonse.domain.jeonse.domain.BuildingRegister;
import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseCheckList;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseRateDto;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.BuildingRegisterMapper;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.JeonseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JeonseService {

    private final JeonseMapper jeonseMapper;
    private final BuildingRegisterMapper buildingRegisterMapper;
    private final RestTemplate restTemplate;

    private static final String API_KEY = "65cc914ef40df38979142db76be2b32f";

    private static final String REGISTER_API_IC = "https://apick.app/rest/iros/1";
    private static final String REGISTER_API_DOC = "https://apick.app/rest/iros_download/1";

    public JeonseRateDto calculateJeonseRate(String atclNo) {
        return jeonseMapper.findJeonseRateByAtclNo(atclNo);
    }


    public List<Jeonse> getRemainJeonse(String address, String aptName) {
        return jeonseMapper.selectRemainJeonse(address, aptName);
    }

    public ByteArrayResource getRegisterDoc(String address) throws IOException {
//        원본
//        return getRegisterDocByIcId(getIcId(address));

//        되는 거
//        return getRegisterDocByIcId("3058724");

//        안되는 거 (민재집)
        return getRegisterDocByIcId("3059680");
    }

    private ByteArrayResource getRegisterDocByIcId(String icId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(REGISTER_API_DOC);
        uploadFile.setHeader("CL_AUTH_KEY", API_KEY);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("ic_id", icId);

        org.apache.http.HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);

        CloseableHttpResponse response = httpClient.execute(uploadFile);
        org.apache.http.HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            try (InputStream inputStream = responseEntity.getContent();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                return new ByteArrayResource(byteArrayOutputStream.toByteArray());
            }
        }
        httpClient.close();
        return null;

    }

    private String getIcId(String address) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();


        HttpHeaders headers = new HttpHeaders();
        headers.add("CL_AUTH_KEY", API_KEY);

        Map<String, String> body = new HashMap<>();
        body.put("address", address);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(REGISTER_API_IC, requestEntity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            return String.valueOf(root.path("data").path("ic_id").asInt());
        } catch (Exception e) {
            log.error("Error occurred while making API request", e);
            throw e;
        }
    }

    public JeonseCheckList checkJeonse(String actlNo) {
        Jeonse jeonse = jeonseMapper.selectJeonseByAtclNo(actlNo);
        JeonseRateDto jeonseRateDto = calculateJeonseRate(actlNo);

        List<BuildingRegister> buildingRegister = buildingRegisterMapper.findBuildingRegisterByAddress(parseAddress(jeonse.getAddress()));

        boolean isBuildingRegister = true;
        String buildingRegisterInfo = "";

        for(BuildingRegister tmp : buildingRegister){
            if (tmp.getMainUseCodeName().contains("근린생활시설") || tmp.getOtherUse().equals("근린생활시설")){
                isBuildingRegister = false;
                buildingRegisterInfo = "주요 용도: " + tmp.getMainUseCodeName() + " | 기타 용도: " + tmp.getOtherUse();
                break;
            }
            buildingRegisterInfo = "주요 용도: " + tmp.getMainUseCodeName() + " | 기타 용도: " + tmp.getOtherUse();
        }

        return JeonseCheckList.builder()
                .jeonse(jeonse)
                .jeonseRate(JeonseCheckList.JeonseRate.builder()
                        .success(Integer.parseInt(jeonseRateDto.getJeonsePrice()) < 80 )
                        .salePrice(Integer.parseInt(jeonseRateDto.getJeonsePrice()))
                        .build())

                .builderLedger(JeonseCheckList.BuilderLedger.builder()
                        .success(isBuildingRegister)
                        .information(buildingRegisterInfo)
                        .build())
                .certifiedRealEstateAgent(JeonseCheckList.CertifiedRealEstateAgent.builder()
                        .success(true)
                        .licensedRealEstateAgent(jeonse.getRltrNm())
                        .build())
                .build();
    }

    private String parseAddress(String address) {
        String[] addressParts = address.split(" ");
        List<String> filteredAddressParts = Arrays.stream(addressParts)
                .filter(part -> !part.equals("서울"))
                .collect(Collectors.toList());
        String tmp = String.join(" ", filteredAddressParts);
        return tmp;
    }
}

