// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)
package index;

import static org.apache.hadoop.mrunit.testutil.ExtendedAssert.assertListEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mock.MockInputSplit;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the word frequency mapper.
 */
public class WordFreqMapperTest extends TestCase {

    private Mapper<LongWritable, Text, Text, IntWritable> mapper;
    private MapDriver<LongWritable, Text, Text, IntWritable> driver;

    /** We expect pathname@offset for the key from each of these */
    private final Text KEY_SUFIX = new Text("@" + MockInputSplit.getMockPath().toString());

    @Before
    public void setUp() {
        mapper = new WordFrequenceInDocMapper();
        driver = new MapDriver<LongWritable, Text, Text, IntWritable>(mapper);
    }

    @Test
    public void testEmpty() {
        List<Pair<Text, IntWritable>> out = null;
        
        try {
            out = driver.withInput(new LongWritable(0), new Text("")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, Text>> expected = new ArrayList<Pair<Text, Text>>();
        
        assertListEquals(expected, out);
    }

    @Test
    public void testOneWord() {
        List<Pair<Text, IntWritable>> out = null;

        try {
            out = driver.withInput(new LongWritable(0), new Text("foo")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, IntWritable>> expected = new ArrayList<Pair<Text, IntWritable>>();
        expected.add(new Pair<Text, IntWritable>(new Text("foo" + KEY_SUFIX), new IntWritable(1)));

        assertListEquals(expected, out);
    }

    @Test
    public void testMultiWords() {
        List<Pair<Text, IntWritable>> out = null;

        try {
            out = driver.withInput(new LongWritable(0), new Text("foo bar baz!!!! ????")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, IntWritable>> expected = new ArrayList<Pair<Text, IntWritable>>();
        expected.add(new Pair<Text, IntWritable>(new Text("foo" + KEY_SUFIX), new IntWritable(1)));
        expected.add(new Pair<Text, IntWritable>(new Text("bar" + KEY_SUFIX), new IntWritable(1)));
        expected.add(new Pair<Text, IntWritable>(new Text("baz" + KEY_SUFIX), new IntWritable(1)));

        assertListEquals(expected, out);
    }
}
