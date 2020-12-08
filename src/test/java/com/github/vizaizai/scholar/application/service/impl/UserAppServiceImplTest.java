package com.github.vizaizai.scholar.application.service.impl;

import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.UserDo;
import com.github.vizaizai.scholar.application.service.UserAppService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserAppServiceImplTest {

    @Autowired
    private UserAppService userAppService;

    @Test
    void add() {
        for (int i = 0; i < 20; i++) {
            userAppService.add("name" + i);
        }
    }
    @Test
    void getById() {
        UserDo userDo = userAppService.getById("1335758538789109762");
        Assert.assertNotNull(userDo);
    }

    @Test
    void getByName() {
        UserDo userDo = userAppService.getByName("name42");
        Assert.assertNotNull(userDo);
    }

}