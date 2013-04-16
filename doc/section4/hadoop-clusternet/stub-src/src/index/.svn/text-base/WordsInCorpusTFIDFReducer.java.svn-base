// (c) Copyright 2009 Cloudera, Inc.
// Hadoop 0.20.1 API Updated by Marcello de Sales (marcello.desales@gmail.com)

package index;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * WordCountsForDocsReducer counts the number of documents in the
 */
public class WordsInCorpusTFIDFReducer extends Reducer<Text, Text, Text, Text> {

    private static final DecimalFormat DF = new DecimalFormat("###.########");

    private Text wordAtDocument = new Text();
    
    private Text tfidfCounts = new Text();
    
    public WordsInCorpusTFIDFReducer() {
    }

    /**
     * @param key is the key of the mapper
     * @param values are all the values aggregated during the mapping phase
     * @param context contains the context of the job run PRE-CONDITION: receive a list of <word, ["doc1=n1/N1",
     *            "doc2=n2/N2"]> POST-CONDITION: <"word@doc1#D, [n+n, N, TF-IDF]">, <"word2@a.txt, 5/13">
     */
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // get the number of documents indirectly from the file-system (stored in the job name on purpose)
        int numberOfDocumentsInCorpus = context.getConfiguration().getInt("docsInCorpus", 0);
        // total frequency of this word
        int numberOfDocumentsInCorpusWhereKeyAppears = 0;
        Map<String, String> tempFrequencies = new HashMap<String, String>();
        for (Text val : values) {
            String[] documentAndFrequencies = val.toString().split("=");
            numberOfDocumentsInCorpusWhereKeyAppears++;
            tempFrequencies.put(documentAndFrequencies[0], documentAndFrequencies[1]);
        }
        for (String document : tempFrequencies.keySet()) {
            String[] wordFrequenceAndTotalWords = tempFrequencies.get(document).split("/");

            //Term frequency is the quocient of the number of terms in document and the total number of terms in doc
            double tf = Double.valueOf(Double.valueOf(wordFrequenceAndTotalWords[0])
                    / Double.valueOf(wordFrequenceAndTotalWords[1]));
            
            //interse document frequency quocient between the number of docs in corpus and number of docs the term appears
            double idf = (double) numberOfDocumentsInCorpus / (double) numberOfDocumentsInCorpusWhereKeyAppears;

            //given that log(10) = 0, just consider the term frequency in documents
            double tfIdf = numberOfDocumentsInCorpus == numberOfDocumentsInCorpusWhereKeyAppears ? 
                    tf : tf * Math.log10(idf);

            this.wordAtDocument.set(key + "@" + document);
            this.tfidfCounts.set("[" + numberOfDocumentsInCorpusWhereKeyAppears + "/"
                    + numberOfDocumentsInCorpus + " , " + wordFrequenceAndTotalWords[0] + "/"
                    + wordFrequenceAndTotalWords[1] + " , " + DF.format(tfIdf) + "]");
            
            context.write(this.wordAtDocument, this.tfidfCounts);
        }
    }
}
