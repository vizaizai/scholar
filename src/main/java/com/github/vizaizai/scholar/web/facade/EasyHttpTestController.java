package com.github.vizaizai.scholar.web.facade;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.web.dto.Book;
import com.github.vizaizai.scholar.web.dto.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/7/28 17:57
 */
@RestController
public class EasyHttpTestController {

    @GetMapping("/testInt")
    public int testInt(){
        return 2313;
    }

    @GetMapping("/testDouble")
    public double testDouble() {
        return 3.22;
    }
    @GetMapping("/listAllBooks")
    public Result<List<Book>> test1() {
        return Result.ok(Arrays.asList(Book.create(),Book.create()));
    }

    @GetMapping("/getBookById/{id}")
    public String test2(@PathVariable String id) {
        System.out.println("id" + id);
        return "ok";
    }

    @GetMapping("/listBooks")
    public String test3(HttpServletRequest request) {
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        return "ok";
    }

    @GetMapping("/listBooks/categories/{cid}/books")
    public String test4(@PathVariable String cid,  HttpServletRequest request) {
        System.out.println("cid" + cid);
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        return "ok";
    }

    @PostMapping("/addBookUseForm")
    public String test5(Book book) {
        System.out.println(JSON.toJSONString(book));
        return "ok";
    }

    @PatchMapping("/addBookUseJSON")
    public String test6(@RequestBody Book book) {
        System.out.println(JSON.toJSONString(book));
        return "ok";
    }

    @GetMapping("/listBookByIds")
    public String test7(String[] ids) {
        System.out.println(JSON.toJSONString(ids));
        return "ok";
    }


    @PostMapping("/addBookUseFormData")
    public String test8(Book book, MultipartFile[] files) throws IOException {
        System.out.println(JSON.toJSONString(book));
        this.saveFile(files);
        return "ok";
    }

    @PostMapping("/upload/e-book/{id}")
    public String test9(@PathVariable String id, HttpServletRequest request) throws IOException {
        System.out.println("id: " + id);
        FileOutputStream fileOutputStream = new FileOutputStream("/file");
        StreamUtils.copy(request.getInputStream(), fileOutputStream);
        fileOutputStream.close();
        return "ok";
    }


    @PostMapping("/open/api/external/createorder")
    public String test9(@RequestBody String body) throws IOException {
        System.out.println(body);
        return "{}";
    }

    private void saveFile(MultipartFile... files) throws IOException {
        if (files == null || files.length == 0) {
            System.out.println("files is Empty");
            return;
        }
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }
            String filename = StringUtils.isBlank(file.getOriginalFilename()) ? "file" : file.getOriginalFilename();
            file.transferTo(new File("D:"+File.separator + filename));
        }
    }

    public static void main(String[] args) {

        String s = "🍉";

        System.out.println(new String(s.getBytes(StandardCharsets.UTF_8)));
    }
}
