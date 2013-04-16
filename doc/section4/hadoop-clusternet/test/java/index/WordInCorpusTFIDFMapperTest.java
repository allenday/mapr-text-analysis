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
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the word count mapper.
 */
public class WordInCorpusTFIDFMapperTest extends TestCase {

    private Mapper<LongWritable, Text, Text, Text> mapper;
    private MapDriver<LongWritable, Text, Text, Text> driver;

    @Before
    public void setUp() {
        mapper = new WordsInCorpusTFIDFMapper();
        driver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
    }

    @Test
    public void testMultiWords() {
        List<Pair<Text, Text>> out = null;

        try {
            out = driver.withInput(new LongWritable(0), new Text("crazy@all-shakespeare\t25/1500")).run();
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, Text>> expected = new ArrayList<Pair<Text, Text>>();
        expected.add(new Pair<Text, Text>(new Text("crazy"), new Text("all-shakespeare=25/1500")));
        assertListEquals(expected, out);
    }
}
