package com.lc.studentmanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.dao.mapper
 * @Author: lc
 * @CreateTime: 2019-12-12 21:56
 * @Description:
 */
@Table(name = "student")
@Data
public class Student {

    @Id
    public String stuid;

    public String stuname;

    public String stusex;

    public String stuphone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public String stubirthday;

    public String studept;

    public String password;

}
