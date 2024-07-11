package kr.ac.kopo.jeonse.domain.jeonse.mapper;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JeonseMapper {

    @Select("SELECT * FROM jeonse WHERE atclNo = #{atclNo}")
    Jeonse selectJeonseByAtclNo(String atclNo);

    List<Jeonse> selectRemainJeonse(@Param("address") String address,@Param("aptName") String aptName);
}

