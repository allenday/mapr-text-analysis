// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)

package index;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * LineIndexMapper Maps each observed word in a line to a (filename@offset) string.
 */
public class WordCountsForDocsMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    private Text docName = new Text();
    private Text wordAndCount = new Text();
    
    public WordCountsForDocsMapper() {
    }
    
    /**
     * @param key is the byte offset of the current line in the file;
     * @param value is the line from the file
     * @param context
     * 
     *     PRE-CONDITION: aa@leornardo-davinci-all.txt    1
     *                    aaron@all-shakespeare   98
     *                    ab@leornardo-davinci-all.txt    3
     * 
     *     POST-CONDITION: Output <"all-shakespeare", "aaron=98"> pairs
     */
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] wordAndDocCounter = value.toString().split("\t");
        String[] wordAndDoc = wordAndDocCounter[0].split("@");
        this.docName.set(wordAndDoc[1]);
        this.wordAndCount.set(wordAndDoc[0] + "=" + wordAndDocCounter[1]);
        context.write(this.docName, this.wordAndCount);
    }
}