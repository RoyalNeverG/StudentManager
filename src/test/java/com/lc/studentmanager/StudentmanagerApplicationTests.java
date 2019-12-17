package com.lc.studentmanager;

import com.lc.studentmanager.dao.mapper.ScoreMapper;
import com.lc.studentmanager.dao.mapper.ScoreVoMapper;
import com.lc.studentmanager.entity.vo.ScoreVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentmanagerApplicationTests {

    @Autowired
    private ScoreVoMapper scoreMapper;

    @Test
    void contextLoads() {

        List<ScoreVo> allScoreVo = scoreMapper.getAllScoreVo();
        System.out.println(allScoreVo.toString());
    }

}
