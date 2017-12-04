import java.util.ArrayList;
import java.util.List;

class Sentence {
    private String originalSentence;
    private List<Integer> termFrequency = new ArrayList<>();
    private int sentenceID;
    private double[] distance = new double[5];
    private

    Sentence(String originalSentence, List<Integer> termFrequency, int sentenceID) {
        this.originalSentence = originalSentence;
        this.termFrequency = termFrequency;
        this.sentenceID = sentenceID;
    }

    double calculateEuclideanDistanceTo(Sentence sentence) {
        double result = 0;
        for (int i = 0; i < termFrequency.size(); i++) {
            result = result + Math.pow(termFrequency.get(i) - sentence.termFrequency.get(i), 2);
        }
        return Math.sqrt(result);
    }

    String getOriginalSentence() {
        return originalSentence;
    }

    int getSentenceID() {
        return sentenceID;
    }

    double[] getDistance() {
        return distance;
    }
}