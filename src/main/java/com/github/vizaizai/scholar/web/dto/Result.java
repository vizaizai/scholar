package com.github.vizaizai.scholar.web.dto;

/**
 * @author liaochongwei
 * @date 2020/7/16 17:15
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private String description;
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Result<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> ok() {
        return build(200, "ok");
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = ok();
        result.data = data;
        return result;
    }

    private static <T> Result<T> build(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.code  = code;
        result.msg = msg;
        return result;
    }
}