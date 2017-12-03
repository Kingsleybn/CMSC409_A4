import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class TDMer {
    private String[] sentences;
    private ArrayList<int[]> TDM;
    private ArrayList<String> words;
    private int cols;

    TDMer(String[] input) {
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
            for (String tempWord : tempWords) {
                //Ignore spaces
                if (!tempWord.equals(" ") && !tempWord.equals("")) {
                    //See if word has been used before
                    if (this.words.contains(tempWord)) {
                        //Look up word in word list and increment TDM
                        index = this.words.indexOf(tempWord);
                        tempTDM.get(index)[i]++;

                    }
                    //Add word to TDM if needed
                    else {
                        //Add word to word list
                        this.words.add(tempWord);

                        //Add a new column to TDM and increment
                        int[] test = new int[this.cols];
                        for (int g = 0; g < this.cols; g++) {
                            test[g] = 0;
                        }
                        tempTDM.add(test);

                        index = this.words.indexOf(tempWord);
                        tempTDM.get(index)[i]++;
                    }
                }
            }
        }
        return tempTDM;
    }

    //Print word list and TDM
    String printTDM() throws IOException {
        File file = new File("TDM.csv");

        FileWriter fw = new FileWriter(file);

        StringBuilder finalWordList = new StringBuilder();

        for (int i = 0; i < this.words.size(); i++) {
            System.out.print(padRight(this.words.get(i)));
            fw.write(this.words.get(i) + ",");
            finalWordList.append(this.words.get(i)).append(" ");

            for (int j = 0; j < this.cols; j++) {
                System.out.print(this.TDM.get(i)[j] + " ");
                fw.write(this.TDM.get(i)[j] + ",");
            }
            System.out.println();
            fw.write("\n");
        }
        fw.close();
        return finalWordList.toString();
    }

    private static String padRight(String s) {
        return String.format("%1$-" + 15 + "s", s);
    }
}

