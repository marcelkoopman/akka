package com.github.marcelkoopman.akka.demo;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.marcelkoopman.akka.demo.actor.WerkgeverActor;
import com.github.marcelkoopman.akka.demo.model.Werkgever;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.PoisonPill;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

public class TestWerkgeverActorSystem 
{
	private static WerkgeverActorSystem werkgeverActorSystem;
	
	@Test
	public void testMsg() throws TimeoutException {
		final ActorSystem actorSystem = werkgeverActorSystem.getActorSystem();
		assertNotNull(actorSystem);
		
		werkgeverActorSystem.sendMessage("Hello1");
		werkgeverActorSystem.sendMessage("Hello2");
		werkgeverActorSystem.sendMessage("Hello3");
		werkgeverActorSystem.sendMessage("Hello4");
		werkgeverActorSystem.sendMessage("Hello5");
		werkgeverActorSystem.sendMessage(PoisonPill.getInstance());
		werkgeverActorSystem.sendMessage("Hello6");
		
		final Inbox inbox = werkgeverActorSystem.getInbox();
		
		while (true) {
			final Werkgever answer = (Werkgever) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
			//assertEquals(StringUtils.reverse("Hello1"), answer.getNaam());
			System.err.println(answer);
		}
		
	}
	
	@BeforeClass
	public static void startUp() {
		werkgeverActorSystem = new WerkgeverActorSystem();
	}
	
	@AfterClass 
	public static void shutDown() throws Exception {
		werkgeverActorSystem.terminate();
	}
}
