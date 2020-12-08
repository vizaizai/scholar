package com.github.vizaizai.scholar.application.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.vizaizai.scholar.application.service.UserAppService;
import com.github.vizaizai.scholar.infrastructure.persistence.database.UserMapper;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:38
 */
@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(String name) {

        UserDo userDo = new UserDo();
        userDo.setName(name);
        userMapper.insert(userDo);
    }

    @Override
    public UserDo getById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserDo getByName(String name) {
        return userMapper.selectOne(Wrappers.<UserDo>lambdaQuery().eq(UserDo::getName,name));
    }
}
