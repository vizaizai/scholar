package com.github.vizaizai.scholar.web.facade;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liaochongwei
 * @date 2020/7/28 17:57
 */
@RestController
public class TestController {

    @PostMapping("/test3")
    public String foo1(HttpServletRequest request) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("/file");
        System.out.println(request.getInputStream().available());
        StreamUtils.copy(request.getInputStream(), fileOutputStream);
        fileOutputStream.close();
        return "ok";
    }
    @PostMapping("/test4")
    public String test4(HttpServletRequest request, MultipartFile[] file1) throws IOException {
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        return "ok";
    }
}
