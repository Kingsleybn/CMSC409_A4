public class TFIDFCalc {

    private final double TOTAL_SENTENCE_COUNT = 27;
    private String finalWordList;
    private String[] tokens;

    public TFIDFCalc(String[] input, String finalWordList) {
        this.tokens = input;
        this.finalWordList = finalWordList;
    }

    public double calc() {
        /*File file = new File("TDM.csv");
        try {
            Scanner scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        return calcTF() * calcIDF();
    }

    private double calcTF() {
        double count = 0;
        for (int i = 0; i < tokens.length; i++) {
            String[] temp = tokens[i].split(" ");
        }
        return 1;
    }

    private double calcIDF() {
        return 1;
    }

}