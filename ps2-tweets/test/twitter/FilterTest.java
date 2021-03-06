package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 301 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "rivest talk in 302 minutes #hype", d2);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "rivest talk in 304 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Tests of writtenBy
     * 
     * no tweets of user
     * no tweets
     * various tweets from user
     * 
     */
    
    @Test
    public void testWrittenByMultipleTweetsNoResults() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet2, tweet4), "alyssa");
        
        assertEquals(0, writtenBy.size());
        assertTrue(writtenBy.isEmpty());
    }
    
    @Test
    public void testWrittenByNoTweetsNoResults() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(), "alyssa");
        
        assertEquals(0, writtenBy.size());
        assertTrue(writtenBy.isEmpty());
    }
   
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4), "alyssa");
        
        assertEquals( 2, writtenBy.size());
        assertTrue(writtenBy.contains(tweet1));
        assertTrue(writtenBy.contains(tweet3));
    }
    
    /*
     * Test for inTimespan
     * 
     * 	multiple tweets multiple results
     * 	multiple tweets one result
     * 	multiple tweets no result
     * 	timespan zero
     * 
     */
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsOneResult() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T10:01:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsNoResult() {
        Instant testStart = Instant.parse("2016-02-17T08:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T09:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertTrue(inTimespan.isEmpty());
    }
    
    @Test
    public void testInTimespanMultipleTweetsTimespanZero() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T09:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertTrue(inTimespan.isEmpty());

    }
    
    /*
     * Tests of containing
     * 
     * 	one tweet one result
     * 	one tweet various results
     * 	one tweet one result
     * 	one tweet no results
     * 	various tweets one result
     * 	various tweets one result
     * 	various tweets no results
     * 	words not separated by spaces
     * 
     * TODO: case insensitive tests
     * 	
     */
    
    @Test
    public void testContainingSubstringsNoResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("ona"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingOneTweetNoResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("rivendel"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingOneTweetOneResult() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
    }
    
    @Test
    public void testContainingOneTweetMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1), Arrays.asList("talk", "reasonable"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
        assertEquals(1, containing.size());
    }
    
    @Test
    public void testContainingMultipleTweetsOneResult() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("reasonable"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testContainingMultipleTweetsNoResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("gondor"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingMultipleTweetsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk", "reasonable"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
