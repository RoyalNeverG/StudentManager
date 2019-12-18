package com.lc.studentmanager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.lc.studentmanager.util.pageHelper.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"课程管理接口"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @ApiOperation("分页查询所有课程")
    @RequestMapping(value = "/findall/{pageindex}",method = {RequestMethod.POST,RequestMethod.GET})
    public String findAllPage(@PathVariable("pageindex") String pageindex, Model model, HttpServletRequest httpServletRequest) {
        PageResult pageResult = courseService.getfindallCoursePage(pageindex, "9");
        model.addAttribute("allcourse", pageResult);
        return "course";
    }

    @ApiOperation("查询所有课程")
    @GetMapping("/findall")
    @ResponseBody
    public Result findAll() {
        List<Department> courses = departmentMapper.selectAll();
        return new Result(StatusCode.OK, "查询成功", true, courses);
    }

    @ApiOperation("显示增加课程视图")
    @GetMapping("/add")
    public String add() {
        return "addCourse";
    }

    @ApiOperation("增加课程")
    @PostMapping("/addcourse")
    @ResponseBody
    public Result addCourse(Course course) {
        Course course1 = new Course();
        course1.setCid(course.getCid());
        Course course2 = courseMapper.selectOne(course1);
        if (course2 != null) {
            return new Result(StatusCode.ERROR, "课程已存在", false);
        }
        int insert = courseMapper.insert(course);
        if (insert == 1) {
            return new Result(StatusCode.OK, "新增成功", true);
        }
        return new Result(StatusCode.ERROR, "新增失败", false);
    }

    @ApiOperation("验证课程号是否存在")
    @ResponseBody
    @PostMapping("/checkcoursecid")
    public Map<String, Object> checkcoursecid(String cid) {
        Map<String, Object> map = new HashMap<>();
        Course course = new Course();
        course.setCid(cid);
        Course course1 = courseMapper.selectOne(course);
        if (course1 == null) {
            map.put("valid", true);
            return map;
        }
        map.put("valid", false);
        return map;
    }

    @ApiOperation("验证课程名称是否存在")
    @ResponseBody
    @PostMapping("/checkcoursename")
    public Map<String, Object> checkcoursename(String cname) {
        Map<String, Object> map = new HashMap<>();
        Course course = new Course();
        course.setCname(cname);
        Course course1 = courseMapper.selectOne(course);
        if (course1 == null) {
            map.put("valid", true);
            return map;
        }
        map.put("valid", false);
        return map;
    }

    @ApiOperation("删除课程")
    @RequestMapping(value = "/deletecourse",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result deleteCourse(String cid) {
        Course course = new Course();
        course.setCid(cid);
        int delete = courseMapper.delete(course);
        if (delete == 1) {
            Score score = new Score();
            score.setCid(cid);
            int delete1 = scoreMapper.delete(score);
            return new Result(StatusCode.OK, "删除成功", true);
        }
        return new Result(StatusCode.ERROR, "删除失败", false);

    }

    @ApiOperation("条件查询课程")
    @RequestMapping(value = "/courseinfoserrch",method = {RequestMethod.POST,RequestMethod.GET})
    public String courseinfoserrch(String cidinfo, String cnameinfo, Model model) {
        if((cidinfo==null||cidinfo.equals(""))&&(cnameinfo==null||cnameinfo.equals(""))){
            return "redirect:/course/findall/1";
        }
        PageHelper.startPage(1, 100);
        PageInfo<Course> coursePageInfo = new PageInfo<>(courseMapper.searchCourseByIdAndName(cidinfo, cnameinfo));
        PageResult pageResult = PageUtils.getPageResult(coursePageInfo);
        model.addAttribute("allcourse", pageResult);
        return "course";
    }

}
