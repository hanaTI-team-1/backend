package kr.ac.kopo.jeonse.domain.jeonse.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.jeonse.domain.jeonse.domain.*;
import kr.ac.kopo.jeonse.domain.jeonse.dto.InfraDTO;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseCheckList;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseRateDto;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.BuildingRegisterMapper;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.JeonseMapper;
import kr.ac.kopo.jeonse.global.geo.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JeonseService {

    private final JeonseMapper jeonseMapper;
    private final BuildingRegisterMapper buildingRegisterMapper;
    private final RestTemplate restTemplate;
    private final GeoLocationService geoLocationService;

    private static final String API_KEY = "65cc914ef40df38979142db76be2b32f";

    private static final String REGISTER_API_IC = "https://apick.app/rest/iros/1";
    private static final String REGISTER_API_DOC = "https://apick.app/rest/iros_download/1";

    public JeonseRateDto calculateJeonseRate(String atclNo) {
        return jeonseMapper.findJeonseRateByAtclNo(atclNo);
    }


    public List<Jeonse> getRemainJeonse(String address, String aptName) {
        return jeonseMapper.selectRemainJeonse(address, aptName);
    }

    public ByteArrayResource getRegisterDoc(String address) throws IOException, InterruptedException {
        return getRegisterDocByIcId(getIcId(address));
    }

    private ByteArrayResource getRegisterDocByIcId(String icId) throws IOException, InterruptedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(REGISTER_API_DOC);
        uploadFile.setHeader("CL_AUTH_KEY", API_KEY);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("ic_id", icId);

        org.apache.http.HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);

        // wait 10 seconds
        TimeUnit.SECONDS.sleep(10);

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

        for (BuildingRegister tmp : buildingRegister) {
            if (tmp.getMainUseCodeName().contains("근린생활시설") || tmp.getOtherUse().equals("근린생활시설")) {
                isBuildingRegister = false;
                buildingRegisterInfo = "주요 용도: " + tmp.getMainUseCodeName() + " | 기타 용도: " + tmp.getOtherUse();
                break;
            }
            buildingRegisterInfo = "주요 용도: " + tmp.getMainUseCodeName() + " | 기타 용도: " + tmp.getOtherUse();
        }

        return JeonseCheckList.builder()
                .jeonse(jeonse)
                .jeonseRate(JeonseCheckList.JeonseRate.builder()
                        .success(Integer.parseInt(jeonseRateDto.getJeonsePrice()) < 80)
                        .salePrice(Integer.parseInt(jeonseRateDto.getJeonsePrice()))
                        .build())
                .builderLedger(JeonseCheckList.BuilderLedger.builder()
                        .success(isBuildingRegister)
                        .information(buildingRegisterInfo)
                        .build())
                .appropriateJeonsePrice(JeonseCheckList.AppropriateJeonsePrice.builder()
                        .success(true)
                        .jeonsePrice(100000000)
                        .infrastructureNum(JeonseCheckList.AppropriateJeonsePrice.InfrastructureNum.builder()
                                .school(5)
                                .publicSecurity(3)
                                .busStop(13)
                                .subway(2)
                                .mart(2)
                                .build())
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

    public List<InfraDTO> getInfraList() {
        String[] targetGu = {"강남구", "동작구", "관악구", "송파구", "강서구"};
        List<InfraDTO> infraList = new ArrayList<>();

        for (String gu : targetGu) {
            infraList.add(InfraDTO.builder()
                    .guName(gu)
                    .busStations(getBusStations(gu))
                    .groceries(getGroceries(gu))
                    .policeStations(getPoliceStations(gu))
                    .schools(getSchools(gu))
                    .crimeRate(getPrimeRate(gu))
                    .build());
        }

        return infraList;
    }

    private float getPrimeRate(String gu) {
        return jeonseMapper.getPrimeRate(gu);
    }

    private List<InfraDTO.School> getSchools(String gu) {
        List<School> schools = jeonseMapper.getSchools(gu);
        return schools.stream()
                .map(school -> InfraDTO.School.builder()
                        .schoolName(school.getSchoolName())
                        .latitude(school.getLatitude())
                        .longitude(school.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

    private List<InfraDTO.PoliceStation> getPoliceStations(String gu) {
        List<PoliceStation> policeStations = jeonseMapper.getPoliceStations(gu);
        return policeStations.stream()
                .map(policeStation -> InfraDTO.PoliceStation.builder()
                        .name(policeStation.getName() + " " + policeStation.getGubun())
                        .latitude(policeStation.getLatitude())
                        .longitude(policeStation.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

    private List<InfraDTO.Grocery> getGroceries(String gu) {
        List<Grocery> groceries = jeonseMapper.getGroceries(gu);
        return groceries.stream()
                .map(grocery -> InfraDTO.Grocery.builder()
                        .businessName(grocery.getBusinessName())
                        .latitude(grocery.getLatitude())
                        .longitude(grocery.getLongitude())
                        .build())
                .collect(Collectors.toList());

    }

    private List<InfraDTO.BusStation> getBusStations(String gu) {
        List<BusStop> busStops = jeonseMapper.getBusStops(gu);
        return busStops.stream()
                .map(busStop -> InfraDTO.BusStation.builder()
                        .title(busStop.getTitle())
                        .latitude(busStop.getLatitude())
                        .longitude(busStop.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }
}

