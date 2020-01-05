package com.lc.studentmanager.controller;

import com.lc.studentmanager.entity.Manager;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.service.ManagerService;
import com.lc.studentmanager.service.StudentService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-13 16:59
 * @Description:
 */
@Api(tags = {"登录接口"})
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ManagerService managerService;

    @ApiOperation("返回登录视图")
    @RequestMapping(value = "",method = {RequestMethod.POST,RequestMethod.GET})
    public String getLogin(){
        return "login";
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        return "redirect:";
    }

    @ApiOperation("登录验证")
    @ResponseBody
    @PostMapping("/")
    public Result loginCheck(String username, String password,String permissionUser,HttpServletRequest httpServletRequest){
        HttpSession httpSession=httpServletRequest.getSession();
        int result=0;
        if(permissionUser.equals("manager")){
            Manager mangerOne = managerService.getMangerOne(username, password);
            if(mangerOne!=null){
                result =1;
                httpSession.setAttribute("userid", mangerOne.getId());
                httpSession.setAttribute("username", mangerOne.getUsername());
                httpSession.setAttribute("managerinfo", mangerOne);
                httpSession.setAttribute("role","manager");
            }
        }else{
            Student studentOne = studentService.findStudentOne(username, password);
            if(studentOne!=null){
                result =1;
                httpSession.setAttribute("userid", studentOne.getStuid());
                httpSession.setAttribute("username", studentOne.getStuname());
                httpSession.setAttribute("studentinfo", studentOne);
                httpSession.setAttribute("role", "student");
            }
        }
        if(result==1){
            return new Result(StatusCode.OK, "验证成功", true);
        }
        return new Result(StatusCode.ERROR, "验证失败", false);
    }

    @ApiOperation("返回个人信息视图")
    @GetMapping("/userinfo")
    public String showUserinfo(){
        return "userinfo";
    }
}
