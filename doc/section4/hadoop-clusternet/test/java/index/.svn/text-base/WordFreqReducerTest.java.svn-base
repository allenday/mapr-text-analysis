// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)

package index;

import static org.apache.hadoop.mrunit.testutil.ExtendedAssert.assertListEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the inverted index reducer.
 */
public class WordFreqReducerTest extends TestCase {

    private Reducer<Text, IntWritable, Text, IntWritable> reducer;
    private ReduceDriver<Text, IntWritable, Text, IntWritable> driver;

    @Before
    public void setUp() {
        reducer = new WordFrequenceInDocReducer();
        driver = new ReduceDriver<Text, IntWritable, Text, IntWritable>(reducer);
    }

    @Test
    public void testOneItem() {
        List<Pair<Text, IntWritable>> out = null;

        try {
            out = driver.withInputKey(new Text("word")).withInputValue(new IntWritable(1)).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, IntWritable>> expected = new ArrayList<Pair<Text, IntWritable>>();
        expected.add(new Pair<Text, IntWritable>(new Text("word"), new IntWritable(1)));

        assertListEquals(expected, out);
    }

    @Test
    public void testMultiWords() {
        List<Pair<Text, IntWritable>> out = null;

        try {
            List<IntWritable> values = new ArrayList<IntWritable>();
            values.add(new IntWritable(2));
            values.add(new IntWritable(5));
            values.add(new IntWritable(8));
            out = driver.withInput(new Text("word1"), values).run();
            
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, IntWritable>> expected = new ArrayList<Pair<Text, IntWritable>>();
        expected.add(new Pair<Text, IntWritable>(new Text("word1"), new IntWritable(15)));

        assertListEquals(expected, out);
    }

}
