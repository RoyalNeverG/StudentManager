package com.lc.studentmanager.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.entity.vo
 * @Author: lc
 * @CreateTime: 2019-12-15 14:54
 * @Description:
 */
@Data
public class ScoreVo {

    public String scoreid;

    public String cid;

    public String stuid;

    public BigDecimal grade;

    public String stuname;

    public String studept;

    public String cname;
}
