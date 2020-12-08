package com.github.vizaizai.scholar.application.service;

import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.UserDo;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:37
 */
public interface UserAppService {
    void add(String name);

    UserDo getById(String id);

    UserDo getByName(String name);
}
