package com.github.vizaizai.scholar.infrastructure.external;

import com.github.vizaizai.annotation.*;
import com.github.vizaizai.boot.annotation.EasyHttpClient;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import com.github.vizaizai.scholar.web.dto.Result;

import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/7/28 15:11
 */
@EasyHttpClient("app1")
public interface DemoRpc {

    @Post("/books")
    Result<Void> addBook(@Body BookDo book);

    @Delete("/books/{id}")
    Result<String> deleteBook(@Var("id") String id);

    @Put("/books")
    Result<Void> editBook(@Body BookDo book);

    @Get("/books")
    Result<List<BookDo>> listAllBooks();

    @Get("/books/search")
    Result<List<BookDo>> searchBooks(@Param("keyword") String keyword);
}
