package com.github.vizaizai.scholar.application.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.vizaizai.retry.core.Retry;
import com.github.vizaizai.retry.util.Utils;
import com.github.vizaizai.scholar.application.service.UserAppService;
import com.github.vizaizai.scholar.infrastructure.persistence.database.UserMapper;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:38
 */
@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void add(String name) {

        UserDo userDo = new UserDo();
        userDo.setName(name);
        Retry.inject(()-> {
            if (Utils.getRandom(10,3) > 6) {
                throw new RuntimeException("发生异常");
            }
            userMapper.insert(userDo);

        }).execute();
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
