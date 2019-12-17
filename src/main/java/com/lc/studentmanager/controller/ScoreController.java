package com.lc.studentmanager.controller;

import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.ScoreService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-15 14:45
 * @Description:
 */
@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreMapper scoreMapper;

    @AuthToken
    @GetMapping("/findallPage/{pageindex}")
    public String getScore(@PathVariable("pageindex") String pageindex, HttpSession httpSession, Model model){
        String role = (String)httpSession.getAttribute("role");
        if(role.equals("manager")){
            PageResult allScore = scoreService.getAllScore(pageindex,"3");
            model.addAttribute("scoreall", allScore);
            return "score";
        }
        if(role.equals("student")){
            PageResult allStudentScore = scoreService.getAllStudentScore(pageindex,"3",(String) httpSession.getAttribute("userid"));
            model.addAttribute("scoreall", allStudentScore);
            return "score";
        }
        PageResult allScore = scoreService.getAllScore(pageindex,"3");
        model.addAttribute("scoreall", allScore);
        return "score";
    }

    @ResponseBody
    @GetMapping("/addScore")
    public Result addScore(String cid, HttpSession httpSession){
        String userid = (String) httpSession.getAttribute("userid");
        List<Score> scoreByStuidAndCid = scoreMapper.findScoreByStuidAndCid(userid, cid);
        if(scoreByStuidAndCid.isEmpty()){
            Score score=new Score();
            score.setStuid(userid);
            score.setCid(cid);
            int i = scoreMapper.insertSelective(score);
            if(i==1){
                return new Result(StatusCode.OK, "选课成功", true);
            }
            return new Result(StatusCode.ERROR, "选课失败", false);
        }
        return new Result(StatusCode.ERROR, "该课程已经选择", false);
    }

    @ResponseBody
    @GetMapping("/deletescore")
    public Result deleteScore(String scoreid){
        Score score=new Score();
        score.setScoreid(Integer.valueOf(scoreid));
        int delete = scoreMapper.delete(score);
        if(delete==1){
            return new Result(StatusCode.OK, "删除选课成功", true);
        }
        return new Result(StatusCode.ERROR, "删除选课失败", false);
    }



}
