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
        // TODO 에러 핸들링 적용하기
        if(roadAddress == null || roadAddress.isEmpty()) throw new RuntimeException("주소에 널 값이 들어옴");
        return buildingRegisterMapper.findBuildingRegisterByAddress(roadAddress);
    }

}
