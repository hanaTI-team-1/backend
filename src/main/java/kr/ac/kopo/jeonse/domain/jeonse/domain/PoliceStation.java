package kr.ac.kopo.jeonse.domain.jeonse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoliceStation {
    private String name;
    private String gubun;
    private String latitude;
    private String longitude;
}
