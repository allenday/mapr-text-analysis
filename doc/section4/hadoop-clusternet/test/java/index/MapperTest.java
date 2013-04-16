// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)
package index;

import static org.apache.hadoop.mrunit.testutil.ExtendedAssert.assertListEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mock.MockInputSplit;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the inverted index mapper.
 */
public class MapperTest extends TestCase {

    private Mapper<LongWritable, Text, Text, Text> mapper;
    private MapDriver<LongWritable, Text, Text, Text> driver;

    /** We expect pathname@offset for the key from each of these */
    private final Text EXPECTED_OFFSET = new Text(MockInputSplit.getMockPath().toString() + "@0");

    @Before
    public void setUp() {
        mapper = new LineIndexMapper();
        driver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
    }

    @Test
    public void testEmpty() {
        List<Pair<Text, Text>> out = null;

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
        List<Pair<Text, Text>> out = null;

        try {
            out = driver.withInput(new LongWritable(0), new Text("foo")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, Text>> expected = new ArrayList<Pair<Text, Text>>();
        expected.add(new Pair<Text, Text>(new Text("foo"), EXPECTED_OFFSET));

        assertListEquals(expected, out);
    }

    @Test
    public void testMultiWords() {
        List<Pair<Text, Text>> out = null;

        try {
            out = driver.withInput(new LongWritable(0), new Text("foo bar baz!!!! ????")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, Text>> expected = new ArrayList<Pair<Text, Text>>();
        expected.add(new Pair<Text, Text>(new Text("foo"), EXPECTED_OFFSET));
        expected.add(new Pair<Text, Text>(new Text("bar"), EXPECTED_OFFSET));
        expected.add(new Pair<Text, Text>(new Text("baz"), EXPECTED_OFFSET));

        assertListEquals(expected, out);
    }
}
