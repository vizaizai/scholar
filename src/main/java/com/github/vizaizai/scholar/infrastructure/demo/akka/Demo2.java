package com.github.vizaizai.scholar.infrastructure.demo.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author liaochongwei
 * @date 2021/11/24 14:12
 */
public class Demo2 {
    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create();
        ActorSelection actorSelection = actorSystem.actorSelection("akka://akka@127.0.0.1:64473");
        actorSelection.tell("123", actorSelection.anchor());
    }
}
