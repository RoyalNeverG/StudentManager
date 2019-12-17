package com.lc.studentmanager.dao.mapper;

import com.lc.studentmanager.entity.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ScoreMapper extends Mapper<Score> {

    @Select("SELECT * FROM `score` where stuid=#{stuid} and cid=#{cid}")
    public List<Score> findScoreByStuidAndCid(@Param("stuid")String stuid,@Param("cid")String cid);

}
