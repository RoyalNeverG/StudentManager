package com.lc.studentmanager.controller;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlShowColumnOutpuVisitor;
import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.dao.mapper.StudentMapper;
import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.StudentService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.sun.corba.se.spi.ior.ObjectKey;
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
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @RequestMapping("/findall")
    public String findAll(Model model){
        List<Student> allStudent = studentService.findAllStudent();
        model.addAttribute("studentlist", allStudent);
        return "index";
    }

    @AuthToken(role_name = { "manager"})
    @GetMapping("/findall/{pageindex}")
    public String findAllPage(@PathVariable("pageindex")String pageindex, @RequestParam(value = "pagename",required = false,defaultValue = "userinfo")String pagename, HttpServletRequest httpServletRequest, Model model){
        PageResult allStudent = studentService.findAllStudentPage(pageindex, "3");
        System.out.println(allStudent.toString());
        System.out.println("--"+pagename);
        httpServletRequest.setAttribute("pagename", pagename);
        model.addAttribute("studentlist", allStudent);
        return "index";
    }


    @GetMapping("/add")
    public String addStudent(){
        return "addStudent";
    }

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


}
