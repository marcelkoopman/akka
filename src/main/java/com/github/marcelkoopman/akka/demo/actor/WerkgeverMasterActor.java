package com.github.marcelkoopman.akka.demo.actor;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

public class WerkgeverMasterActor extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(this);

	private final Router router;
	{
		final List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < 5; i++) {
			final ActorRef r = getContext().actorOf(Props.create(WerkgeverActor.class));
			getContext().watch(r);
			routees.add(new ActorRefRoutee(r));
		}
		router = new Router(new RoundRobinRoutingLogic(), routees);
	}

	@Override
	public void onReceive(final Object msg) throws Exception {
		log.info("Received: " + msg + " going to route this");
		router.route(msg, getSender());
	}

}
