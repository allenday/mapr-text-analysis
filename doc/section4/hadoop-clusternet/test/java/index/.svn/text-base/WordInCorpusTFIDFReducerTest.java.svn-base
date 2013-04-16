// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)

package index;

import static org.apache.hadoop.mrunit.testutil.ExtendedAssert.assertListEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the reducer of the word counts.
 */
public class WordInCorpusTFIDFReducerTest extends TestCase {

    private Reducer<Text, Text, Text, Text> reducer;
    private ReduceDriver<Text, Text, Text, Text> driver;

    @Before
    public void setUp() {
        reducer = new WordsInCorpusTFIDFReducer();
        driver = new ReduceDriver<Text, Text, Text, Text>(reducer);
    }

    @Test
    public void testMultiWords() {
        List<Pair<Text, Text>> out = null;
        try {
            List<Text> values = new ArrayList<Text>();
            values.add(new Text("shakespeare=10/738781"));
            values.add(new Text("davinci=10/149612"));
            out = driver.withInput(new Text("therefore"), values).run();
            
        } catch (IOException ioe) {
            fail();
        }

        List<Pair<Text, Text>> expected = new ArrayList<Pair<Text, Text>>();
        expected.add(new Pair<Text, Text>(new Text("therefore@shakespeare"), new Text("[2/3 , 10/738781 , 0.000003260596841]")));
        expected.add(new Pair<Text, Text>(new Text("therefore@davinci"), new Text("[20 , 10/149612 , 0.000003260596841]")));
        assertListEquals(expected, out);
    }

}
