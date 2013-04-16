package tfidf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * WordCountsInDocuments counts the total number of words in each document and 
 * produces data with the relative and total number of words for each document.
 * 
 * @author Marcello de Sales (mdesales)
 */
public class WordCountsInDocuments extends Configured implements Tool {

    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH = "2-word-counts";

    // where to read the data from.
    private static final String INPUT_PATH = "1-word-freq";

    public static class WordCountsForDocsMapper extends Mapper<LongWritable, Text, Text, Text> {

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

    public static class WordCountsForDocsReducer extends Reducer<Text, Text, Text, Text> {

        private Text wordAtDoc = new Text();
        private Text wordAvar = new Text();

        public WordCountsForDocsReducer() {
        }

        /**
         * @param key is the key of the mapper
         * @param values are all the values aggregated during the mapping phase
         * @param context contains the context of the job run 
         * 
         *        PRE-CONDITION: receive a list of <document, ["word=n", "word-b=x"]>
         *            pairs <"a.txt", ["word1=3", "word2=5", "word3=5"]> 
         *            
         *       POST-CONDITION: <"word1@a.txt, 3/13">,
         *            <"word2@a.txt, 5/13">
         */
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int sumOfWordsInDocument = 0;
            Map<String, Integer> tempCounter = new HashMap<String, Integer>();
            for (Text val : values) {
                String[] wordCounter = val.toString().split("=");
                tempCounter.put(wordCounter[0], Integer.valueOf(wordCounter[1]));
                sumOfWordsInDocument += Integer.parseInt(val.toString().split("=")[1]);
            }
            for (String wordKey : tempCounter.keySet()) {
                this.wordAtDoc.set(wordKey + "@" + key.toString());
                this.wordAvar.set(tempCounter.get(wordKey) + "/" + sumOfWordsInDocument);
                context.write(this.wordAtDoc, this.wordAvar);
            }
        }
    }

    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        Job job = new Job(conf, "Words Counts");

        job.setJarByClass(WordCountsInDocuments.class);
        job.setMapperClass(WordCountsForDocsMapper.class);
        job.setReducerClass(WordCountsForDocsReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new WordCountsInDocuments(), args);
        System.exit(res);
    }
}
