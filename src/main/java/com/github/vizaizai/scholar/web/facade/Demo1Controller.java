package com.github.vizaizai.scholar.web.facade;

import com.github.vizaizai.scholar.infrastructure.external.DemoRpc;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import com.github.vizaizai.scholar.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<String> bar() {
        return Arrays.asList("hello", "world");
    }
}
