package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.Data;

@Data
public class RecommendRequest {
    private String dongNm;
    private int school;
    private int publicSecurity;
    private int busStop;
    private int subway;
    private int mart;
}
