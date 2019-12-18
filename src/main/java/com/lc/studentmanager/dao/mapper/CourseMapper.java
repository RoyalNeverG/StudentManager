package com.lc.studentmanager.dao.mapper;

import com.lc.studentmanager.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CourseMapper extends Mapper<Course> {

    public List<Course> searchCourseByIdAndName(@Param("cidinfo")String cidinfo,@Param("cnameinfo")String cnameinfo);
}
