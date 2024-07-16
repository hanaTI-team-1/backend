package kr.ac.kopo.jeonse.domain.jeonse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppropriateJeonse {
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
