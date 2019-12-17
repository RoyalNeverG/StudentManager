package com.lc.studentmanager.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.nio.channels.Pipe;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.entity
 * @Author: lc
 * @CreateTime: 2019-12-17 17:17
 * @Description:
 */
@Table(name = "department")
@Data
public class Department {

    @Id
    private Integer deptid;

    private String deptname;

}
