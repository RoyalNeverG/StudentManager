package com.lc.studentmanager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.studentmanager.dao.mapper.CourseMapper;
import com.lc.studentmanager.entity.Course;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.lc.studentmanager.util.pageHelper.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.service
 * @Author: lc
 * @CreateTime: 2019-12-15 12:49
 * @Description:
 */
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public PageResult getfindallCoursePage(String pageindex,String size){
        PageHelper.startPage(Integer.valueOf(pageindex), Integer.valueOf(size));
        List<Course> courses = courseMapper.selectAll();
        PageInfo<Course> coursePageInfo=new PageInfo<>(courses);
        PageResult pageResult = PageUtils.getPageResult(coursePageInfo);
        return pageResult;
    }

}
