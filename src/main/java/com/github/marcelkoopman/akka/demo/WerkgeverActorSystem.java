package com.github.marcelkoopman.akka.demo;

import com.github.marcelkoopman.akka.demo.actor.WerkgeverMasterActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class WerkgeverActorSystem {

	private final ActorSystem actorSystem = ActorSystem.create("AkkaDemo");
	private final Inbox inbox = Inbox.create(actorSystem);
	private final ActorRef werkgeverMasterActor;
	
	public WerkgeverActorSystem () {
		actorSystem.log().info("ActorSystem is up.");
		werkgeverMasterActor = actorSystem.actorOf(Props.create(WerkgeverMasterActor.class), "werkgeverMasterActor");
	}
	
	public ActorSystem getActorSystem() {
		return this.actorSystem;
	}

	public Inbox getInbox() {
		return this.inbox;
	}
	
	public void sendMessage(final Object msg) {
		inbox.send(werkgeverMasterActor, msg);
	}

	public void terminate() {
		actorSystem.log().info("ActorSystem termination requested...");
		final Future<Terminated> future = actorSystem.terminate();
		try {
			Await.result(future, new Timeout(Duration.create(5, "seconds")).duration());
			System.err.println("ActorSystem terminated!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
