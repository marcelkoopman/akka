package com.github.marcelkoopman.akka.demo.actor;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.github.marcelkoopman.akka.demo.model.Werkgever;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WerkgeverActor extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(this);

	@Override
	public void onReceive(final Object msg) throws Exception {
		try {
			onReceiveInternal(msg);
		} catch (final Exception e) {
			log.error("Exception: " + e);
			throw e;
		}
	}

	private void onReceiveInternal(final Object msg)
			throws UnknownHostException, ExecutionException, InterruptedException {
		log.info("Received: " + msg);
		getSender().tell(new Werkgever(StringUtils.reverse("" + msg)), getSelf());
		
		final RiakClient client = RiakClient.newClient("127.0.0.1");

		final Location location = new Location(new Namespace("WerkgeverBucket"), "TestKey");
		final StoreValue sv = new StoreValue.Builder(msg).withLocation(location).build();
		final StoreValue.Response svResponse = client.execute(sv);
		log.info("Stored: " + svResponse);
		
		client.shutdown();
	}

}
