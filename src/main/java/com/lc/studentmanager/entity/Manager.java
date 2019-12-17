package com.lc.studentmanager.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.entity
 * @Author: lc
 * @CreateTime: 2019-12-14 13:00
 * @Description:
 */
@Table(name = "manager")
@Data
public class Manager {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public String id;

    public String username;

    public String password;
}
