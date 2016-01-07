package com.github.marcelkoopman.akka.demo.actor;

import org.apache.commons.lang3.StringUtils;

import com.github.marcelkoopman.akka.demo.model.Werkgever;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WerkgeverActor extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(this);
	
	@Override
	public void onReceive(Object msg) throws Exception {
		log.info("Received: "+msg);
		getSender().tell(new Werkgever(StringUtils.reverse(""+msg)), getSelf());
	}

	
	
}
