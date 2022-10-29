package com.github.vizaizai.scholar.web.facade;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.vizaizai.scholar.application.converter.UserConverter;
import com.github.vizaizai.scholar.application.service.UserAppService;
import com.github.vizaizai.scholar.web.dto.Result;
import com.github.vizaizai.scholar.web.dto.User;
import com.github.vizaizai.scholar.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private UserConverter userConverter;

    @PostMapping
    public Result<Void> addBook(String name) {
        userAppService.add(name);
        return Result.ok();
    }

    @GetMapping("foo")
    public Result<UserDTO> addBoo1k() {
        User user = new User();
        user.setId("13123");
        user.setName("wgwegweg");
        user.setBirthday(LocalDate.now());
        user.setBalance(new BigDecimal("0"));
        user.setCreateTime(LocalDateTime.now());
        return Result.ok(userConverter.toDto(user));
    }

}
