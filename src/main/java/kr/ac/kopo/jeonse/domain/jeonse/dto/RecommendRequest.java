package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.Data;

@Data
public class RecommendRequest {
    private String dongName;
    private String policeOffice;
    private String subway;
    private String school;
    private String mart;
    private String bus;
    private String price;
}
