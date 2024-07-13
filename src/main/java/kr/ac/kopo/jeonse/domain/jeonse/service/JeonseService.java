package kr.ac.kopo.jeonse.domain.jeonse.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
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
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JeonseService {

    private final JeonseMapper jeonseMapper;

    private final RestTemplate restTemplate;

    private static final String API_KEY = "65cc914ef40df38979142db76be2b32f";

    private static final String REGISTER_API_IC = "https://apick.app/rest/iros/1";
    private static final String REGISTER_API_DOC = "https://apick.app/rest/iros_download/1";

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

    public ByteArrayResource getRegisterDoc(String address) throws IOException {
//        return getRegisterDocByIcId(getIcId(address));

//        되는 거
//        return getRegisterDocByIcId("3058724");

//        안되는 거 (민재집)
        return getRegisterDocByIcId("3059679");
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
}

