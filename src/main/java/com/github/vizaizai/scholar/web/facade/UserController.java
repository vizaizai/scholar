package com.github.vizaizai.scholar.web.facade;

import com.github.vizaizai.scholar.application.service.UserAppService;
import com.github.vizaizai.scholar.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaochongwei
 * @date 2020/7/28 17:57
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserAppService userAppService;


    @PostMapping
    public Result<Void> addBook(String name) {
        userAppService.add(name);
        return Result.ok();
    }
}
