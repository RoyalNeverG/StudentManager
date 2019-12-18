package com.lc.studentmanager.controller;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlShowColumnOutpuVisitor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.dao.mapper.StudentMapper;
import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.StudentService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.lc.studentmanager.util.pageHelper.PageUtils;
import com.sun.corba.se.spi.ior.ObjectKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
 * @CreateTime: 2019-12-12 19:16
 * @Description:
 */
@Api(tags = {"学生管理接口"})
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @ApiOperation("查询所有学生信息")
    @RequestMapping(value = "/findall",method = {RequestMethod.POST,RequestMethod.GET})
    public String findAll(Model model){
        List<Student> allStudent = studentService.findAllStudent();
        model.addAttribute("studentlist", allStudent);
        return "index";
    }

    @ApiOperation("分页查询所有学生信息")
    @AuthToken(role_name = { "manager"})
    @GetMapping("/findall/{pageindex}")
    public String findAllPage(@PathVariable("pageindex")String pageindex, @RequestParam(value = "pagename",required = false,defaultValue = "userinfo")String pagename, HttpServletRequest httpServletRequest, Model model){
        PageResult allStudent = studentService.findAllStudentPage(pageindex, "9");
        System.out.println(allStudent.toString());
        System.out.println("--"+pagename);
        httpServletRequest.setAttribute("pagename", pagename);
        model.addAttribute("studentlist", allStudent);
        return "index";
    }

    @ApiOperation("返回增加学生信息视图")
    @GetMapping("/add")
    public String addStudent(){
        return "addStudent";
    }

    @ApiOperation("增加学生信息")
    @ResponseBody
    @PostMapping("/addStudent")
    public Result addStudent(Student student){
        if(student.getStudept()==null||student.getStudept().equals("")){
            return new Result(StatusCode.ERROR,"新增失败", false);
        }
        int insert = studentMapper.insertSelective(student);
        if(insert==1){
            return new Result(StatusCode.OK,"新增成功", true);
        }
        return new Result(StatusCode.ERROR,"新增失败", false);
    }

    @ApiOperation("检查学生学号是否存在")
    @ResponseBody
    @PostMapping("/checkstu")
    public Map<String, Object> checkStudent(String stuid){
        Map<String,Object> map=new HashMap<>();
        Student student1=new Student();
        student1.setStuid(stuid);
        Student student2 = studentMapper.selectOne(student1);
        if(student2==null){
            map.put("valid", true);
            return map;
        }
        map.put("valid", false);
        return map;
    }

    @ApiOperation("删除学生信息")
    @GetMapping("/deleteStuInfo")
    @ResponseBody
    public Result deleteStuInfo(String stuid){
        Student student=new Student();
        student.setStuid(stuid);
        int delete = studentMapper.delete(student);
        if(delete==1){
            Score score=new Score();
            score.setStuid(stuid);
            int delete1 = scoreMapper.delete(score);
            return new Result(StatusCode.OK, "删除成功", true);
        }
        return new Result(StatusCode.ERROR, "删除失败", false);
    }

    @ApiOperation("查找学生信息")
    @RequestMapping(value = "/studentinfoserrch",method = {RequestMethod.POST,RequestMethod.GET})
    public String  studentinfoserrch(String stuidinfo,String stunameinfo,Model model){
        if((stuidinfo==null||stuidinfo.equals(""))&&(stunameinfo==null||stunameinfo.equals(""))){
            return "redirect:/student/findall/1";
        }
        PageHelper.startPage(1, 100);
        PageInfo<Student> pageInfo=new PageInfo<>(studentMapper.searchStudentByIdAndName(stuidinfo, stunameinfo));
        PageResult pageResult = PageUtils.getPageResult(pageInfo);
        model.addAttribute("studentlist", pageResult);
        return "index";
    }


}
