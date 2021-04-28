package com.github.vizaizai.scholar.web.facade;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.infrastructure.external.DemoRpc;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import com.github.vizaizai.scholar.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/7/28 17:57
 */
@RestController
@RequestMapping("/book")
public class Demo1Controller {


    @Autowired
    private DemoRpc demoRpc;


    @PostMapping
    public Result<Void> addBook(@RequestBody BookDo bookDo) {
        return demoRpc.addBook(bookDo);
    }

    @GetMapping
    public Result<List<BookDo>> listAll() {
        return demoRpc.listAllBooks();
    }

    @PutMapping
    public Result<Void> editBook(@RequestBody BookDo bookDo) {
      return demoRpc.editBook(bookDo);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteBook(@PathVariable String id) {
        return demoRpc.deleteBook(id);
    }

    @GetMapping("/search")
    public Result<List<BookDo>> search(@RequestParam String keyword) {
       return demoRpc.searchBooks(keyword);
    }

    @GetMapping("/bar")
    public List<String> bar(String[] ids, @RequestHeader HttpHeaders headers) {
//        if (Utils.getRandom(5,1) > 1) {
//            throw new RuntimeException();
//        }
        System.out.println(JSON.toJSONString(headers));
        System.out.println(JSON.toJSONString(ids));
        return Arrays.asList("hello", "world");
    }

    @PostMapping("foo")
    public List<String> foo(@RequestBody String foo) {
        System.out.println(foo);
        return Arrays.asList("hello", "world");
    }

    @PostMapping("foo1")
    public List<String> foo1(HttpServletRequest request,  @RequestParam("formData") String body) {
        System.out.println(JSON.toJSONString(request.getParameterMap()));
        System.out.println(body);
        return Arrays.asList("hello", "world");
    }

}
