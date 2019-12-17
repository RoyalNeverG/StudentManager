package com.lc.studentmanager.service;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.studentmanager.dao.mapper.StudentMapper;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.lc.studentmanager.util.pageHelper.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.service
 * @Author: lc
 * @CreateTime: 2019-12-12 22:15
 * @Description:
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findAllStudent(){
        return studentMapper.selectAll();
    }

    public PageResult findAllStudentPage(String pageindex,String pagesize){
        PageHelper.startPage(Integer.valueOf(pageindex), Integer.valueOf(pagesize));
        PageInfo<Student> pageInfe= new PageInfo<>(studentMapper.selectAll());
        PageResult pageResult = PageUtils.getPageResult(pageInfe);
        return pageResult;
    }

    public Student findStudentOne(String username,String password){
        Student student=new Student();
        student.setStuid(username);
        student.setPassword(password);
        Student student1 = studentMapper.selectOne(student);
        return student1;
    }

    public int updateinfo(Student student){
        int i = studentMapper.updateByPrimaryKeySelective(student);
        return i;
    }

}
