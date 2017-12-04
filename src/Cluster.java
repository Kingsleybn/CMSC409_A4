import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private List<Sentence> sentences = new ArrayList<>();
    private Sentence centroid = null;

    Cluster() {
    }

    Sentence getCentroid() {
        return centroid;
    }

    void setCentroid(Sentence centroid) {
        this.centroid = centroid;
    }

    void addSentenceToCluster(Sentence sentence) {
        sentences.add(sentence);
    }

    Sentence calculateNewMedoid() {
        Sentence temp;

        // Stores the sum of distance for each sentence to all other sentences
        double[] distance = new double[sentences.size()];

        for (int i = 0; i < sentences.size(); i++) {
            temp = sentences.get(i);
            for (Sentence sentence : sentences) {
                distance[i] = distance[i] + temp.calculateEuclideanDistanceTo(sentence);
            }
        }

        double minimum = Double.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < distance.length; i++) {
            if (minimum > distance[i]) {
                index = i;
                minimum = distance[i];
            }
        }

        return sentences.get(index);
    }

    void clearCluster() {
        sentences.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence.getOriginalSentence()).append("\n");
        }
        return sb.toString();
    }
}