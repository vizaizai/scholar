package com.github.vizaizai.scholar.web.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.vizaizai.scholar.web.dto.DD;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;

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


    @PostMapping("/test5")
    public String test5(DD dd) throws IOException {
        System.out.println(JSON.toJSONString(dd.getList()));
        return "ok";
    }


    @PostMapping("/testCallback")
    public String fefef(@RequestBody String params) throws IOException {
        System.out.println(params);
        return "SUCCESS";
    }

    public static void main(String[] args) {
        String s = "[[\"520000\",\"520100\",\"520123\"],[\"520000\",\"520100\",\"520113\"],[\"520000\",\"520100\",\"520115\"],[\"520000\",\"520100\",\"520102\"],[\"520000\",\"520100\",\"520111\"],[\"520000\",\"520100\",\"520121\"],[\"520000\",\"520100\",\"520122\"],[\"520000\",\"520100\",\"520112\"],[\"520000\",\"520100\",\"520103\"]]";
        JSONArray array = JSON.parseArray(s);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            List<String> list = array.getJSONArray(i).toJavaList(String.class);
            strings.add(StringUtils.join(list,','));
        }
        System.out.println(JSON.toJSONString(strings));
    }
}
