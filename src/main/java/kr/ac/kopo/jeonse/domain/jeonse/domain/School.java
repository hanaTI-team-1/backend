package kr.ac.kopo.jeonse.domain.jeonse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class School {
    private String schoolName;
    private String latitude;
    private String longitude;
}
