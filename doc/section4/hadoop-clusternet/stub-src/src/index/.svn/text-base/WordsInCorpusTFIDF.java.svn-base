package index;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * WordFrequenceInDocument Creates the index of the words in documents, 
 * mapping each of them to their frequency.
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * @version "Hadoop 0.20.1"
 */
public class WordsInCorpusTFIDF extends Configured implements Tool {
    
    // where to read the data from.
    private static final String INPUT_PATH = "input";

    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH = "1-word-freq";
    
    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH_2 = "2-word-counts";

    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH_3 = "3-tf-idf";

    /* (non-Javadoc)
     * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
     */
    public int run(String[] args) throws Exception {
        
        Configuration conf = getConf();
        Job job = new Job(conf, "Word Frequence In Document");
        job.setJarByClass(WordFrequenceInDocument.class);
        job.setMapperClass(WordFrequenceInDocMapper.class);
        job.setReducerClass(WordFrequenceInDocReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        job.waitForCompletion(true);

        Configuration conf2 = getConf();
        Job job2 = new Job(conf2, "Words Counts");
        job2.setJarByClass(WordCountsInDocuments.class);
        job2.setMapperClass(WordCountsForDocsMapper.class);
        job2.setReducerClass(WordCountsForDocsReducer.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job2, new Path(OUTPUT_PATH));
        FileOutputFormat.setOutputPath(job2, new Path(OUTPUT_PATH_2));

        job2.waitForCompletion(true);

        Configuration conf3 = getConf();
        //Getting the number of documents from the original input directory.
        Path inputPath = new Path("input");
        FileSystem fs = inputPath.getFileSystem(conf3);
        FileStatus[] stat = fs.listStatus(inputPath);
        conf3.setInt("docsInCorpus", stat.length);
        Job job3 = new Job(conf3, "Word in Corpus, TF-IDF");
        job3.setJarByClass(WordsInCorpusTFIDF.class);
        job3.setMapperClass(WordsInCorpusTFIDFMapper.class);
        job3.setReducerClass(WordsInCorpusTFIDFReducer.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job3, new Path(OUTPUT_PATH_2));
        FileOutputFormat.setOutputPath(job3, new Path(OUTPUT_PATH_3));
        return job3.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new WordsInCorpusTFIDF(), args);
        System.exit(res);
    }
}
