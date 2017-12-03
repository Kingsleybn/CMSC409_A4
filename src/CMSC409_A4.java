import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class CMSC409_A4 {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(new File("sentences.txt"));
        ArrayList<String> temp = new ArrayList<>();

        while (scan.hasNextLine()) {
            temp.add(scan.nextLine());
        }

        String[] sentences = temp.toArray(new String[temp.size()]);

        //Get tokens for each sentence
        Tokenizer t = new Tokenizer(sentences);
        String[] tokens = t.getTokens();
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replaceAll(" +", " ").trim();
        }

        Stemmer stemmer = new Stemmer();

        StringBuilder sb = new StringBuilder();

        // Stemming then combining
        for (int i = 0; i < tokens.length; i++) {
            String[] sentenceToken = tokens[i].split(" ");
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

        for (String token : tokens) {
            System.out.println(token);
        }

        String finalWordList = "";

        //Build and print TDM, outputs to TDM.csv
        TDMer tdm = new TDMer(tokens);
        try {
            finalWordList = tdm.printTDM().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TFIDFCalc tfidfCalc = new TFIDFCalc(tokens, finalWordList);
        tfidfCalc.calc();
    }
}