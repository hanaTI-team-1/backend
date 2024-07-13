package kr.ac.kopo.jeonse.domain.jeonse.mapper;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import kr.ac.kopo.jeonse.domain.jeonse.dto.JeonseRateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface JeonseMapper {

    Jeonse selectJeonseByAtclNo(@Param("atclNo") String atclNo);
    List<Jeonse> selectRemainJeonse(@Param("address") String address, @Param("aptName") String aptName);
    JeonseRateDto findJeonseRateByAtclNo(@Param("atclNo") String atclNo);
}

