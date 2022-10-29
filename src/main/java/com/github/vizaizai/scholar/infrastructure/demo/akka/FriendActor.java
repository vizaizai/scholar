package com.github.vizaizai.scholar.infrastructure.demo.akka;

import akka.actor.AbstractActor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaochongwei
 * @date 2021/11/24 14:13
 */
@Slf4j
public class FriendActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::onReceivePing)
                .matchAny(System.out::println)
                .build();
    }

    private void onReceivePing(String ping) {
        getSender().tell(AskResponse.succeed(null), getSelf());
    }
}
