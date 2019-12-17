package com.lc.studentmanager.controller;

import com.lc.studentmanager.dao.mapper.CourseMapper;
import com.lc.studentmanager.dao.mapper.DepartmentMapper;
import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.entity.Course;
import com.lc.studentmanager.entity.Department;
import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.CourseService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.lc.studentmanager.util.pageHelper.PageResult;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-14 17:36
 * @Description:
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @RequestMapping("/findall/{pageindex}")
    public String findAllPage(@PathVariable("pageindex")String pageindex,  Model model, HttpServletRequest httpServletRequest){
        PageResult pageResult = courseService.getfindallCoursePage(pageindex, "3");
        model.addAttribute("allcourse", pageResult);
        return "course";
    }

    @GetMapping("/findall")
    @ResponseBody
    public Result findAll(){
        List<Department> courses = departmentMapper.selectAll();
        return new Result(StatusCode.OK, "查询成功", true,courses);
    }

    @GetMapping("/add")
    public String add(){
        return "addCourse";
    }

    @PostMapping("/addcourse")
    @ResponseBody
    public Result addCourse(Course course){
        Course course1=new Course();
        course1.setCid(course.getCid());
        Course course2 = courseMapper.selectOne(course1);
        if(course2!=null){
            return new Result(StatusCode.ERROR, "课程已存在", false);
        }
        int insert = courseMapper.insert(course);
        if(insert==1){
            return new Result(StatusCode.OK, "新增成功", true);
        }
        return new Result(StatusCode.ERROR, "新增失败", false);
    }

    @ResponseBody
    @PostMapping("/checkcoursecid")
    public Map<String, Object> checkcoursecid(String cid){
        Map<String,Object> map=new HashMap<>();
        Course course=new Course();
        course.setCid(cid);
        Course course1 = courseMapper.selectOne(course);
        if(course1==null){
            map.put("valid", true);
            return map;
        }
        map.put("valid", false);
        return map;
    }

    @ResponseBody
    @PostMapping("/checkcoursename")
    public Map<String, Object> checkcoursename(String cname){
        Map<String,Object> map=new HashMap<>();
        Course course=new Course();
        course.setCname(cname);
        Course course1 = courseMapper.selectOne(course);
        if(course1==null){
            map.put("valid", true);
            return map;
        }
        map.put("valid", false);
        return map;
    }

    @RequestMapping("/deletecourse")
    @ResponseBody
    public Result deleteCourse(String cid){
        Course course=new Course();
        course.setCid(cid);
        int delete = courseMapper.delete(course);
        if(delete==1){
            Score score=new Score();
            score.setCid(cid);
            int delete1 = scoreMapper.delete(score);
            return new Result(StatusCode.OK, "删除成功", true);
        }
        return new Result(StatusCode.ERROR, "删除失败", false);

    }

}
