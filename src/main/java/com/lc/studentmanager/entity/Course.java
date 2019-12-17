package com.lc.studentmanager.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.entity
 * @Author: lc
 * @CreateTime: 2019-12-14 17:30
 * @Description:
 */
@Table(name = "course")
@Data
public class Course {

    @Id
    public String cid;

    public String cname;

    public Integer ccredit;

}
