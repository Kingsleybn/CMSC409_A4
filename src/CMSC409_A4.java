import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CMSC409_A4 {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(new File("sentences.txt"));
        ArrayList<String> originalSentences = new ArrayList<>();

        while (scan.hasNextLine()) {
            originalSentences.add(scan.nextLine());
        }

        String[] sentences = originalSentences.toArray(new String[originalSentences.size()]);

        // Get tokens for each sentence
        Tokenizer t = new Tokenizer(sentences);
        String[] tokens = t.getTokens();

        // Stemming then combining
        Stemmer stemmer = new Stemmer();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            String[] sentenceToken = tokens[i].split("\\s+");
            for (String s : sentenceToken) {
                tokens[i] = "";
                stemmer.add(s.toCharArray(), s.length());
                stemmer.stem();
                s = stemmer.toString();
                sb.append(s).append(" ");
            }
            tokens[i] = sb.toString();
            sb.delete(0, sb.length());
        }

        // Build and print TDM, outputs to TDM.csv
        TDMer tdm = new TDMer(tokens);
        try {
            tdm.printTDM();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Transposes the TDM
        List<List<Integer>> transposedData = transpose(tdm.TDM);
        for (List<Integer> transposedDatum : transposedData) {
            System.out.println(transposedDatum);
        }

        // Makes sentences objects
        List<Sentence> clusterSentences = new ArrayList<>();
        for (int i = 0; i < originalSentences.size(); i++) {
            clusterSentences.add(new Sentence(originalSentences.get(i), transposedData.get(i), i));
        }

        // Get initial clustering with 5 clusters, sentence # 2, 5, 10, 15, 20, which are arbitrarily chosen
        List<Cluster> clusters = new ArrayList<>();
        List<Sentence> centroids = new ArrayList<>();
        centroids.add(clusterSentences.get(1));
        centroids.add(clusterSentences.get(4));
        centroids.add(clusterSentences.get(9));
        centroids.add(clusterSentences.get(14));
        centroids.add(clusterSentences.get(19));

        for (int i = 0; i < 5; i++) {
            clusters.add(new Cluster());
            clusters.get(i).addSentenceToCluster(centroids.get(i));
        }

        // Array lists to compare old and new clusters


        while (true) {

            // Calculate minimum distance for from sentences to the 5 centroids, then choosing the closest
            // cluster based on the minimum distance
            for (int i = 0; i < clusterSentences.size(); i++) {
                Sentence temp = clusterSentences.get(i);

                for (int j = 0; j < centroids.size(); j++) {
                    temp.getDistance()[j] = temp.calculateEuclideanDistanceTo(centroids.get(j));
                }

                double minimum = Double.MAX_VALUE;
                int index = 0;

                for (int j = 0; j < temp.getDistance().length; j++) {
                    if (minimum > temp.getDistance()[j]) {
                        index = i;
                        minimum = temp.getDistance()[j];
                    }
                }

            }

        }






        /*ArrayList<ArrayList<Double>> tf_idf = new ArrayList<>();
        ArrayList<Double> idf = new  ArrayList<>();

        for (ArrayList<Integer> integers : tdm.TDM) {
            int count = 0;
            for (Integer integer : integers) {
                if (integer != 0){
                    count++;
                }
            }
            idf.add(Math.log(27/count));
        }

        for (List<Integer> tranposedDatum : transposedData) {
            Double max = (double)Collections.max(tranposedDatum);
            ArrayList<Double> tfTemp = new ArrayList<>();
            for (int i = 0; i < tranposedDatum.size(); i++) {
                Double occurrence = (double) tranposedDatum.get(i);
                tfTemp.add((occurrence/max) * idf.get(i));
            }
            tf_idf.add(tfTemp);
        }

        for (ArrayList<Double> doubles : tf_idf) {
            System.out.println(doubles);
        }*/
    }

    private static <T> List<List<T>> transpose(ArrayList<ArrayList<T>> table) {
        List<List<T>> ret = new ArrayList<>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

}