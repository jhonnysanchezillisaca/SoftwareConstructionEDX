package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk  @user about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk @user in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "life @tony is what it is", d3);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "life @alyssa is what it is", d3);
    private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "life @alyssa @tony @user is what it is", d3);
    private static final Tweet tweet6 = new Tweet(6, "bbitdiddle", "life asd@alyssar is what it is", d3);
    private static final Tweet tweet7 = new Tweet(7, "bbitdiddle", "@user life lyssar is what it is", d3);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Tests of getTimespan
     * 
     *  when there's only one tweet
     *  when there are an even number of tweets
     *  when there are an uneven number of tweets
     *  when d1 == d2
     *  when d1 > d2
     *  when d1 < d2 
     */

    
    @Test
    public void testGetTimespanOneTweetIsZero(){
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3));
        
        assertEquals(d3, timespan.getStart());
        assertEquals(d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanEvenNumberTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanUnevenNumberTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanSecondTweetBeforeFirst() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanSameTimeTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    /*
     * Tests of getMentionedUsers
     * 
     * 	no mention of users
     * 	multiple mentions of same user
     * 	different mentions in different tweets
     * 	different mentions in same tweet
     * 	invalid user mention (like an email address)
     * 	mention at beginning of tweet
     */
    
    @Test
    public void testGetMentionedUsersMultipleMentionsSameUser(){
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        
        assertTrue(mentionedUsers.contains("@user"));
        assertEquals(1, mentionedUsers.size());
    }
    
    @Test
    public void testGetMentionedUsersDifferentUsersVariousTweets(){
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        
        assertTrue(mentionedUsers.contains("@user"));
        assertTrue(mentionedUsers.contains("@alyssa"));
        assertTrue(mentionedUsers.contains("@tony"));
        assertEquals(3, mentionedUsers.size());
    }
    
    @Test
    public void testGetMentionedUsersDifferentUsersSameTweets(){
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertTrue(mentionedUsers.contains("@user"));
        assertTrue(mentionedUsers.contains("@alyssa"));
        assertTrue(mentionedUsers.contains("@tony"));
        assertEquals(3, mentionedUsers.size());
    }
    
    @Test
    public void testGetMentionedUsersInvalidMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersBeginningOfTweet() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        
        assertTrue(mentionedUsers.contains("@user"));
        assertEquals(1, mentionedUsers.size());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
