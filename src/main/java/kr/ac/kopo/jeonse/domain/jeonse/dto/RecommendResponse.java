package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendResponse {
    private String clusterType;
    private List<JeonseCheckList> jeonseCheckList;
}
