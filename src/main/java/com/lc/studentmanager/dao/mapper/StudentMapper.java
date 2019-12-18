package com.lc.studentmanager.dao.mapper;

import com.lc.studentmanager.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.dao.mapper
 * @Author: lc
 * @CreateTime: 2019-12-12 22:02
 * @Description:
 */
@Repository
public interface StudentMapper extends Mapper<Student> {

    public List<Student> searchStudentByIdAndName(@Param("stuid") String stuid,@Param("stuname") String stuname);

}
