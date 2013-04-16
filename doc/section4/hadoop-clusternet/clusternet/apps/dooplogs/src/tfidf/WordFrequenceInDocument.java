package tfidf;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * WordFrequenceInDocument Creates the index of the words in documents, 
 * mapping each of them to their frequency.
 * 
 * Used Hadoop 0.20.2+737
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
public class WordFrequenceInDocument extends Configured implements Tool {

    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH = "1-word-freq";

    public static class WordFrequenceInDocMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        /**
         * Google's search Stopwords
         */
        private static Set<String> googleStopwords;

        static {
            googleStopwords = new HashSet<String>();
            googleStopwords.add("I"); googleStopwords.add("a"); googleStopwords.add("about");
            googleStopwords.add("an"); googleStopwords.add("are"); googleStopwords.add("as");
            googleStopwords.add("at"); googleStopwords.add("be"); googleStopwords.add("by");
            googleStopwords.add("com"); googleStopwords.add("de"); googleStopwords.add("en");
            googleStopwords.add("for"); googleStopwords.add("from"); googleStopwords.add("how");
            googleStopwords.add("in"); googleStopwords.add("is"); googleStopwords.add("it");
            googleStopwords.add("la"); googleStopwords.add("of"); googleStopwords.add("on");
            googleStopwords.add("or"); googleStopwords.add("that"); googleStopwords.add("the");
            googleStopwords.add("this"); googleStopwords.add("to"); googleStopwords.add("was");
            googleStopwords.add("what"); googleStopwords.add("when"); googleStopwords.add("where"); 
            googleStopwords.add("who"); googleStopwords.add("will"); googleStopwords.add("with");
            googleStopwords.add("and"); googleStopwords.add("the"); googleStopwords.add("www");
        }

        /**
         * Pattern used to select the words
         */
        private static final Pattern PATTERN = Pattern.compile("\\w+");
        /**
         * Default word 
         */
        private Text word = new Text();
        /**
         * Default single counter
         */
        private IntWritable singleCount = new IntWritable(1);

        public WordFrequenceInDocMapper() {
        }

        /**
         * @param key is the byte offset of the current line in the file;
         * @param value is the line from the file
         * @param output has the method "collect()" to output the key,value pair
         * @param reporter allows us to retrieve some information about the job (like the current filename) 
         * 
         *     POST-CONDITION: Output <"word", "filename@offset"> pairs
         */
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // Compile all the words using regex

            Matcher m = PATTERN.matcher(value.toString());

            // Get the name of the file from the input-split in the context
            String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();

            // build the values and write <k,v> pairs through the context
            StringBuilder valueBuilder = new StringBuilder();
            while (m.find()) {
                String matchedKey = m.group().toLowerCase();
                // remove names starting with non letters, digits, considered stopwords or containing other chars
                if (!Character.isLetter(matchedKey.charAt(0)) || Character.isDigit(matchedKey.charAt(0))
                        || googleStopwords.contains(matchedKey) || matchedKey.contains("_") || 
                            matchedKey.length() < 3) {
                    continue;
                }
                valueBuilder.append(matchedKey);
                valueBuilder.append("@");
                valueBuilder.append(fileName);
                // emit the partial <k,v>
                this.word.set(valueBuilder.toString());
                context.write(this.word, this.singleCount);
                valueBuilder.setLength(0);
            }
        }
    }

    public static class WordFrequenceInDocReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable wordSum = new IntWritable();
        
        public WordFrequenceInDocReducer() {
        }

        /**
         * @param key is the key of the mapper
         * @param values are all the values aggregated during the mapping phase
         * @param context contains the context of the job run
         * 
         *      PRE-CONDITION: receive a list of <"word@filename",[1, 1, 1, ...]> pairs 
         *        <"marcello@a.txt", [1, 1]> 
         *        
         *      POST-CONDITION: emit the output a single key-value where the sum of the occurrences. 
         *        <"marcello@a.txt", 2>
         */
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, 
                 InterruptedException {

            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            //write the key and the adjusted value (removing the last comma)
            this.wordSum.set(sum);
            context.write(key, this.wordSum);
        }
    }

    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        Job job = new Job(conf, "Word Frequence In Document");

        job.setJarByClass(WordFrequenceInDocument.class);
        job.setMapperClass(WordFrequenceInDocMapper.class);
        job.setReducerClass(WordFrequenceInDocReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new WordFrequenceInDocument(), args);
        System.exit(res);
    }
}
