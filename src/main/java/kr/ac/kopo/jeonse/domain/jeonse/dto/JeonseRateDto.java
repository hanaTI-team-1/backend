package kr.ac.kopo.jeonse.domain.jeonse.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class JeonseRateDto {
    private final String jeonsePrice;
    private final String maemaePrice;
    private final String rate;
}
