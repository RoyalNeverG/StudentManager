package com.lc.studentmanager.dao.mapper;

import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.entity.vo.ScoreVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.dao.mapper
 * @Author: lc
 * @CreateTime: 2019-12-15 14:52
 * @Description:
 */
@Repository
public interface ScoreVoMapper extends Mapper<ScoreVo> {

    @Select("SELECT\n" +
            "\tscoreid,\n" +
            "\tcid,stuid,grade,stuname,studept,cname\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\t*\n" +
            "\t\tFROM\n" +
            "\t\t\tscore\n" +
            "\t\tNATURAL JOIN student\n" +
            "\t\tWHERE\n" +
            "\t\t\tscore.stuid = student.stuid\n" +
            "\t) s\n" +
            "NATURAL JOIN course\n" +
            "WHERE\n" +
            "\ts.cid = course.cid")
    List<ScoreVo> getAllScoreVo();

    @Select("SELECT\n" +
            "\tcid,scoreid,stuid,grade,stuname,studept,cname\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\t*\n" +
            "\t\tFROM\n" +
            "\t\t\tscore\n" +
            "\t\tNATURAL JOIN student\n" +
            "\t\tWHERE\n" +
            "\t\t\tscore.stuid = student.stuid\n" +
            "\t) s\n" +
            "NATURAL JOIN course\n" +
            "WHERE\n" +
            "\ts.cid = course.cid and stuid=#{stuid}")
    List<ScoreVo> getAllStudentScoreVo(@Param("stuid") String stuid);
}
