package com.lc.studentmanager.controller;

import com.lc.studentmanager.dao.mapper.ManagerMapper;
import com.lc.studentmanager.dao.mapper.StudentMapper;
import com.lc.studentmanager.entity.Manager;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.StudentService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-14 17:14
 * @Description:
 */
@Api(tags = {"个人信息管理接口"})
@Controller
@RequestMapping("/info")
public class UserInfoController {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation("返回个人信息视图")
    @GetMapping("")
    public String getInfoPage(){
        return "userinfo";
    }

    @ApiOperation("更新个人信息")
    @PostMapping("/updateinfo")
    @ResponseBody
    public Result updateInfo(String stuphone, String stubirthday, HttpSession httpSession){
        Student student=new Student();
        student.setStuid((String) httpSession.getAttribute("userid"));
        student.setStuphone(stuphone);
        student.setStubirthday(stubirthday);
        int updateinfo = studentService.updateinfo(student);
        if(updateinfo==1){
            Student student1=new Student();
            student1.setStuid((String) httpSession.getAttribute("userid"));
            Student student2 = studentMapper.selectOne(student1);
            httpSession.setAttribute("studentinfo", student2);
            return new Result(StatusCode.OK, "修改成功", true);
        }
        return new Result(StatusCode.ERROR, "修改失败", false);
    }

    @ApiOperation("更新密码")
    @ResponseBody
    @PostMapping("/updatepassword")
    public Result updatepassword(String oldpassword,String newpassword,HttpSession httpSession){
        if(httpSession.getAttribute("role").toString().equals("student")){
            Student studentinfo = (Student) httpSession.getAttribute("studentinfo");
            if(studentinfo.getPassword().equals(oldpassword)){
                Student student=new Student();
                student.setStuid((String) httpSession.getAttribute("userid"));
                student.setPassword(newpassword);
                int i = studentMapper.updateByPrimaryKeySelective(student);
                if(i==1){
                    return new Result(StatusCode.OK, "修改成功", true);
                }
            }
        }else{
           Manager managerinfo= (Manager) httpSession.getAttribute("managerinfo");
           if(managerinfo.getPassword().equals(oldpassword)){
               Manager manager=new Manager();
               manager.setId((String) httpSession.getAttribute("userid"));
               manager.setPassword(newpassword);
               int i=managerMapper.updateByPrimaryKeySelective(manager);
               if(i==1){
                   return new Result(StatusCode.OK, "修改成功", true);
               }
           }
        }
        return new Result(StatusCode.ERROR, "修改失败", false);
    }
}
