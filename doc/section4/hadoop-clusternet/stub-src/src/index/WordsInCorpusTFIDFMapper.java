// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)
package index;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * WordFrequenceInDocMapper implements the Job 1 specification for the TF-IDF algorithm
 */
public class WordsInCorpusTFIDFMapper extends Mapper<LongWritable, Text, Text, Text> {

    public WordsInCorpusTFIDFMapper() {
    }

    private Text wordAndDoc = new Text();
    private Text wordAndCounters = new Text();
    
    /**
     * @param key is the byte offset of the current line in the file;
     * @param value is the line from the file
     * @param output has the method "collect()" to output the key,value pair
     * @param reporter allows us to retrieve some information about the job (like the current filename) 
     * 
     *     PRE-CONDITION: marcello@book.txt    3/1500
     *     POST-CONDITION: marcello, book.txt=3/1500,1
     */
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] wordAndCounters = value.toString().split("\t");
        String[] wordAndDoc = wordAndCounters[0].split("@");                 //3/1500
        this.wordAndDoc.set(new Text(wordAndDoc[0]));
        this.wordAndCounters .set(wordAndDoc[1] + "=" + wordAndCounters[1]);
        context.write(this.wordAndDoc, this.wordAndCounters);
    }
}