package kr.ac.kopo.jeonse.domain.jeonse.mapper;

import kr.ac.kopo.jeonse.domain.jeonse.domain.BuildingRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuildingRegisterMapper {
    List<BuildingRegister> findBuildingRegisterByAddress(@Param("road_address") String roadAddress);

    String findBuildingViolationByAddress(@Param("gu") String gu, @Param("dong") String dong, @Param("bun") String bun, @Param("bu") String bu);
}
