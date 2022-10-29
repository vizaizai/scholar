package com.github.vizaizai.scholar.infrastructure.demo.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author liaochongwei
 * @date 2021/11/24 14:12
 */
public class Demo1 {
    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("akka");
        ActorRef worker = actorSystem.actorOf(Props.create(FriendActor.class), "worker");
    }
}
