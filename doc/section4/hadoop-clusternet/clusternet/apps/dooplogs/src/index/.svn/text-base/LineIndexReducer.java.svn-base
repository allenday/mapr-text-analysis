// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)

package index;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * LineIndexReducer Takes a list of filename@offset entries for a single word and concatenates them into a list.
 */
public class LineIndexReducer extends Reducer<Text, Text, Text, Text> {

    public LineIndexReducer() {
    }

    /**
     * @param key is the key of the mapper
     * @param values are all the values aggregated during the mapping phase
     * @param context contains the context of the job run
     * 
     *      PRE-CONDITION: receive a list of <"word", "filename@offset"> pairs 
     *        <"marcello", ["a.txt@3345", "b.txt@344", "c.txt@785"]> 
     *        
     *      POST-CONDITION: emit the output a single key-value where all the file names 
     *        are separated by a comma ",". 
     *        <"marcello", "a.txt@3345,b.txt@344,c.txt@785">
     */
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder valueBuilder = new StringBuilder();

        for (Text val : values) {
            valueBuilder.append(val);
            valueBuilder.append(",");
        }
        //write the key and the adjusted value (removing the last comma)
        context.write(key, new Text(valueBuilder.substring(0, valueBuilder.length() - 1)));
        valueBuilder.setLength(0);
    }
}
