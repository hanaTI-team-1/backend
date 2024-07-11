package kr.ac.kopo.jeonse.domain.batch.mapper;

import kr.ac.kopo.jeonse.domain.jeonse.domain.Jeonse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchMapper {
    public void deleteAllJeonse();
    public void updateJeonse(List<Jeonse> jeonseList);
}
