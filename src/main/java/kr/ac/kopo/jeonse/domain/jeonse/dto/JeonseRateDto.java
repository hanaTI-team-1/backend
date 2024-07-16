package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.*;


@Data
@AllArgsConstructor

public class JeonseRateDto {
    private String jeonsePrice;
    private String maemaePrice;
    private String rate;
}
