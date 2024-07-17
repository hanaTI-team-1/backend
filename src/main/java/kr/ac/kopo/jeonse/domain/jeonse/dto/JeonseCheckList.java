package kr.ac.kopo.jeonse.domain.jeonse.dto;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class JeonseCheckList {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JeonseRate {
        private boolean success;
        private int salePrice;
        private float jeonseSaleRate;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BuilderLedger {
        private boolean success;
        private String information;
        private String isViolation;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CertifiedRealEstateAgent {
        private boolean success;
        private String licensedRealEstateAgent;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppropriateJeonsePrice{
        private boolean success;
        private int jeonsePrice;
        private InfrastructureNum infrastructureNum;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InfrastructureNum{
            private int school;
            private int publicSecurity;
            private int busStop;
            private int subway;
            private int mart;
        }
    }

    Jeonse jeonse;
    JeonseRate jeonseRate;
    BuilderLedger builderLedger;
    CertifiedRealEstateAgent certifiedRealEstateAgent;
    AppropriateJeonsePrice appropriateJeonsePrice;

}
