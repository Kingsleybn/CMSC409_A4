import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TDMer {
    private String[] sentences;
    private ArrayList<int[]> TDM;
    private ArrayList<String> words;
    private int cols;

    public TDMer(String[] input) {
        this.sentences = input;
        this.cols = input.length;
        this.words = new ArrayList<>();
        this.TDM = this.buildTDM();
    }

    private ArrayList<int[]> buildTDM() {
        String[] tempWords;
        int index;

        ArrayList<int[]> tempTDM = new ArrayList<>();

        //Repeat for each sentence
        for (int i = 0; i < this.cols; i++) {
            tempWords = this.sentences[i].split(" ");

            //Repeat for each word in sentence
            for (int j = 0; j < tempWords.length; j++) {
                //Ignore spaces
                if (!tempWords[j].equals(" ") && !tempWords[j].equals("")) {
                    //See if word has been used before
                    if (this.words.contains(tempWords[j])) {
                        //Look up word in word list and increment TDM
                        index = this.words.indexOf(tempWords[j]);
                        tempTDM.get(index)[i]++;

                    }
                    //Add word to TDM if needed
                    else {
                        //Add word to word list
                        this.words.add(tempWords[j]);

                        //Add a new column to TDM and increment
                        int[] test = new int[this.cols];
                        for (int g = 0; g < this.cols; g++) {
                            test[g] = 0;
                        }
                        tempTDM.add(test);

                        index = this.words.indexOf(tempWords[j]);
                        tempTDM.get(index)[i]++;
                    }
                }
            }
        }
        return tempTDM;
    }

    //Print word list and TDM
    void printTDM() throws IOException {
        File file = new File("TDM.csv");

        FileWriter fw = new FileWriter(file);

        for (int i = 0; i < this.words.size(); i++) {
            System.out.print(padRight(this.words.get(i), 15));
            fw.write(this.words.get(i) + ",");

            for (int j = 0; j < this.cols; j++) {
                System.out.print(this.TDM.get(i)[j] + " ");
                fw.write(this.TDM.get(i)[j] + ",");
            }
            System.out.println();
            fw.write("\n");
        }
        fw.close();
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}

