package com.lc.studentmanager.controller;

import com.lc.studentmanager.dao.mapper.ManagerMapper;
import com.lc.studentmanager.entity.Manager;
import com.lc.studentmanager.entity.Student;
import com.lc.studentmanager.service.ManagerService;
import com.lc.studentmanager.service.StudentService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-13 16:59
 * @Description:
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ManagerService managerService;

    @RequestMapping("")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        return "redirect:";
    }

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

    @GetMapping("/userinfo")
    public String showUserinfo(){
        return "userinfo";
    }
}
