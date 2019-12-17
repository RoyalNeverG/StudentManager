package com.lc.studentmanager.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.entity
 * @Author: lc
 * @CreateTime: 2019-12-15 14:51
 * @Description:
 */
@Table(name = "score")
@Data
public class Score {

    @Id
    public Integer scoreid;

    public String stuid;

    public String cid;

    public BigDecimal grade;
}
