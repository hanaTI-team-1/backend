package kr.ac.kopo.jeonse.domain.jeonse.mapper;

import kr.ac.kopo.jeonse.domain.jeonse.domain.*;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseRateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface JeonseMapper {

    Jeonse selectJeonseByAtclNo(@Param("atclNo") String atclNo);

    List<Jeonse> selectRemainJeonse(@Param("address") String address, @Param("aptName") String aptName);

    JeonseRateDto findJeonseRateByAtclNo(@Param("atclNo") String atclNo);

    List<BusStop> getBusStops(@Param("gu") String gu);

    List<Grocery> getGroceries(@Param("gu") String gu);

    List<PoliceStation> getPoliceStations(@Param("gu") String gu);

    List<School> getSchools(@Param("gu") String gu);

    float getPrimeRate(@Param("gu") String gu);
}

