package com.github.vizaizai.scholar.infrastructure.demo.akka;

/**
 * @author liaochongwei
 * @date 2021/11/24 14:49
 */
public class AskResponse {

    private boolean s;

    private String msg;

    public AskResponse(boolean s, String msg) {
        this.s = s;
        this.msg = msg;
    }

    public static AskResponse succeed(String msg) {
        return new AskResponse(true,msg);
    }
}
