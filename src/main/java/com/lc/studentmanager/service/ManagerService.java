package com.lc.studentmanager.service;

import com.lc.studentmanager.dao.mapper.ManagerMapper;
import com.lc.studentmanager.entity.Manager;
import com.sun.org.apache.regexp.internal.RE;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.service
 * @Author: lc
 * @CreateTime: 2019-12-14 13:07
 * @Description:
 */
@Service
@Transactional
public class ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    public Manager getMangerOne(String username,String password){
        Manager manager=new Manager();
        manager.setUsername(username);
        manager.setPassword(password);
        Manager manager1 = managerMapper.selectOne(manager);
        return manager1;
    }

}
