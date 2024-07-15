package kr.ac.kopo.jeonse.domain.jeonse.service;

import kr.ac.kopo.jeonse.domain.jeonse.domain.BuildingRegister;
import kr.ac.kopo.jeonse.domain.jeonse.mapper.BuildingRegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingRegisterService {
    private final BuildingRegisterMapper buildingRegisterMapper;

    public List<BuildingRegister> getBuildingRegisterByAddress(String roadAddress) {
        if(roadAddress == null || roadAddress.isEmpty()) throw new RuntimeException("주소값은 필수 정보입니다.");
        return buildingRegisterMapper.findBuildingRegisterByAddress(roadAddress);
    }

}
