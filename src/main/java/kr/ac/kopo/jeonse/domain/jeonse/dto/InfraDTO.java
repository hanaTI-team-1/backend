package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfraDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BusStation {
        private String title;
        private String latitude;
        private String longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Grocery {
        private String businessName;
        private String latitude;
        private String longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PoliceStation {
        private String name;
        private String latitude;
        private String longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class School {
        private String schoolName;
        private String latitude;
        private String longitude;
    }

    private String guName;
    private List<BusStation> busStations;
    private List<Grocery> groceries;
    private List<PoliceStation> policeStations;
    private List<School> schools;

    private float crimeRate;

}
