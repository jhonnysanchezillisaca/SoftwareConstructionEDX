package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

	/*
	 * TODO: your testing strategies for these methods should go here. Make sure
	 * you have partitions.
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk  @user about rivest so much?",
			d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk @user in 30 minutes #hype", d2);
	private static final Tweet tweet4 = new Tweet(4, "user", "life @alyssa is what it is", d3);
	private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "life @alyssa @tony @user is what it is", d3);
	private static final Tweet tweet6 = new Tweet(6, "alyssa", "life asdalyssar is what it is", d3);
	private static final Tweet tweet7 = new Tweet(7, "bbitdiddle", "@bbitdiddle life lyssar is what it is", d3);
	private static final Tweet tweet8 = new Tweet(8, "bbitdiddle", "ife lyssar is what it is", d3);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	/*
	 * Test for guessFollowsGraph
	 * 
	 * 	no tweets
	 * 	tweets no mentions
	 * 	tweets mention himself
	 * 	tweets one user mention other
	 * 	tweets one user multiple mentions
	 * 	tweets various users mention one user
	 * 	tweets various users multiple mentions
	 * 	
	 */
	
	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}
	
	@Test
	public void testGuessFollowsGraphNoMentions() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet6, tweet8));

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}
	
	@Test
	public void testGuessFollowsGraphMentionHimself() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet7));

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}
	
	@Test
	public void testGuessFollowsGraphOneUserMentionsOtherUser() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));

		assertTrue(followsGraph.get("@alyssa").contains("@user"));
		assertEquals("expected one entry",1, followsGraph.size());
		assertEquals("expected one entry in set",1, followsGraph.get("@alyssa").size());

	}
	
	@Test
	public void testGuessFollowsGraphOneUserMentionsOthersUsers() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5));

		assertTrue(followsGraph.get("@bbitdiddle").contains("@user"));
		assertTrue(followsGraph.get("@bbitdiddle").contains("@tony"));
		assertTrue(followsGraph.get("@bbitdiddle").contains("@alyssa"));

		assertEquals("expected one entry", 1, followsGraph.size());
		assertEquals("expected three entries in set", 3, followsGraph.get("@bbitdiddle").size());

	}
	
	@Test
	public void testGuessFollowsGraphVariousUsersMentionsOneUser() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));

		assertTrue(followsGraph.get("@alyssa").contains("@user"));
		assertEquals("expected only one entry", 1, followsGraph.size());

	}
	
	@Test
	public void testGuessFollowsGraphVariousUsersMentionsVariousUsers() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet4));

		assertTrue(followsGraph.get("@alyssa").contains("@user"));
		assertTrue(followsGraph.get("@bbitdiddle").contains("@user"));
		assertTrue(followsGraph.get("@user").contains("@alyssa"));


		assertEquals("expected three entries", 3, followsGraph.size());
		assertEquals("expected one entry in set", 1, followsGraph.get("@alyssa").size());
		assertEquals("expected one entry in set", 1, followsGraph.get("@bbitdiddle").size());
		assertEquals("expected one entry in set", 1, followsGraph.get("@user").size());

	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue("expected empty list", influencers.isEmpty());
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO
	 * NOT strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own
	 * that you have put in SocialNetwork, because that means you're testing a
	 * stronger spec than SocialNetwork says. If you need such helper methods,
	 * define them in a different class. If you only need them in this test
	 * class, then keep them in this test class.
	 */

	/*
	 * Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
	 * Redistribution of original or derived work requires explicit permission.
	 * Don't post any of this code on the web or to a public Github repository.
	 */
}
