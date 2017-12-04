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
        centroids.add(clusterSentences.get(0));
        centroids.add(clusterSentences.get(1));
        centroids.add(clusterSentences.get(3));
        centroids.add(clusterSentences.get(13));
        centroids.add(clusterSentences.get(15));
        centroids.add(clusterSentences.get(21));

        for (int i = 0; i < centroids.size(); i++) {
            clusters.add(new Cluster());
            clusters.get(i).setCentroid(centroids.get(i));
        }

        // Main clustering algorithm
        int count = 0;
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
                        index = j;
                        minimum = temp.getDistance()[j];
                    }
                }

                temp.setCentroid(centroids.get(index));
                clusterSentences.set(i, temp);
            }

            // Add one centroid to one cluster
            for (int i = 0; i < clusters.size(); i++) {
                clusters.get(i).setCentroid(centroids.get(i));
            }

            // Add sentences to their clusters
            for (Sentence sentence : clusterSentences) {
                for (Cluster cluster : clusters) {
                    if (sentence.getCentroid().equals(cluster.getCentroid())) {
                        cluster.addSentenceToCluster(sentence);
                    }
                }
            }

            // Checks to see if clusters have changed
            if (count > 1) {
                boolean compareCluster = true;
                for (Cluster cluster : clusters) {
                    if (!cluster.compareClusters()) compareCluster = false;
                }
                if (compareCluster) break;
            }

            // Calculate new medoids
            centroids.clear();
            for (Cluster cluster : clusters) {
                cluster.setCentroid(cluster.calculateNewMedoid());
                centroids.add(cluster.getCentroid());

                // Get ready for next iteration
                cluster.saveOldCluster();
                cluster.clearCluster();
            }
            count++;
        }

        System.out.println(count - 1 + " iterations ran");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + i + ": ");
            System.out.println(clusters.get(i).toString() + "\n");
        }
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