package com.lc.studentmanager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.dao.mapper.ScoreVoMapper;
import com.lc.studentmanager.entity.vo.ScoreVo;
import com.lc.studentmanager.util.pageHelper.PageResult;
import com.lc.studentmanager.util.pageHelper.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.service
 * @Author: lc
 * @CreateTime: 2019-12-15 15:19
 * @Description:
 */
@Service
@Transactional
public class ScoreService {

    @Autowired
    private ScoreVoMapper scoreMapper;

    public PageResult getAllScore(String pageindex,String pagesize){
        PageHelper.startPage(Integer.valueOf(pageindex), Integer.valueOf(pagesize));
        List<ScoreVo> allScoreVo = scoreMapper.getAllScoreVo();
        PageInfo<ScoreVo> pageInfo=new PageInfo<>(allScoreVo);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);
        return pageResult;
    }

    public PageResult getAllStudentScore(String pageindex,String pagesize,String stuid){
        PageHelper.startPage(Integer.valueOf(pageindex), Integer.valueOf(pagesize));
        List<ScoreVo> allStudentScoreVo = scoreMapper.getAllStudentScoreVo(stuid);
        PageInfo<ScoreVo> pageInfo=new PageInfo<>(allStudentScoreVo);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);
        return pageResult;
    }

}
