package com.lc.studentmanager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.dao.mapper.ScoreVoMapper;
import com.lc.studentmanager.entity.Score;
import com.lc.studentmanager.entity.vo.ScoreVo;
import com.lc.studentmanager.permissionManager.AuthToken;
import com.lc.studentmanager.service.ScoreService;
import com.lc.studentmanager.util.Result;
import com.lc.studentmanager.util.StatusCode;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.lc.studentmanager.util.pageHelper.PageUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.controller
 * @Author: lc
 * @CreateTime: 2019-12-15 14:45
 * @Description:
 */
@Api(tags = {"学生选课接口"})
@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreVoMapper scoreVoMapper;

    @ApiOperation("分页查询所有选课信息")
    @AuthToken
    @GetMapping("/findallPage/{pageindex}")
    public String getScore(@PathVariable("pageindex") String pageindex, HttpSession httpSession, Model model){
        String role = (String)httpSession.getAttribute("role");
        if(role.equals("manager")){
            PageResult allScore = scoreService.getAllScore(pageindex,"9");
            model.addAttribute("scoreall", allScore);
            return "score";
        }
        if(role.equals("student")){
            PageResult allStudentScore = scoreService.getAllStudentScore(pageindex,"9",(String) httpSession.getAttribute("userid"));
            model.addAttribute("scoreall", allStudentScore);
            return "score";
        }
        PageResult allScore = scoreService.getAllScore(pageindex,"9");
        model.addAttribute("scoreall", allScore);
        return "score";
    }

    @ApiOperation("增加选课信息")
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

    @ApiOperation("删除选课信息")
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

    @ApiOperation("查找选课信息")
    @PostMapping("/searchScore")
    public String searchScore(String stuidinfo,String stunameinfo,String cnameinfo, Model model,HttpSession httpSession){
        if((stuidinfo==null||stuidinfo.equals(""))&&(stunameinfo==null||stunameinfo.equals(""))&&(cnameinfo==null||cnameinfo.equals(""))){
            return "redirect:/score/findallPage/1";
        }
        List<ScoreVo> scoreVos;
        if(httpSession.getAttribute("role").equals("manager")) {
            PageHelper.startPage(1,100);
            scoreVos = scoreVoMapper.searchScoreBystuidAndstuNameAndCName(stuidinfo, stunameinfo, cnameinfo);
        }else{
            PageHelper.startPage(1,100);
            scoreVos = scoreVoMapper.searchScoreBystuidAndstuNameAndCNameIsStudent(httpSession.getAttribute("userid").toString(),cnameinfo);
        }
        PageInfo<ScoreVo> scoreVoPageInfo=new PageInfo<>(scoreVos);
        PageResult pageResult = PageUtils.getPageResult(scoreVoPageInfo);
        model.addAttribute("scoreall", pageResult);
        return "score";
    }



}
