import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
        Stemmer stemmer = new Stemmer();

        StringBuilder sb = new StringBuilder();

        // Stemming then combining
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


        //Build and print TDM, outputs to TDM.csv
        TDMer tdm = new TDMer(tokens);
        try {
            tdm.printTDM();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ArrayList<Double>> tf = new ArrayList<>();
        ArrayList<Double> idf = new  ArrayList<>();
        List<List<Integer>> tranposedData = transpose(tdm.TDM);

        for (ArrayList<Integer> integers : tdm.TDM) {
            int count = 0;
            for (Integer integer : integers) {
                if (integer != 0){
                    count++;
                }
            }
            idf.add(Math.log(27/count)/Math.log(2));
        }

        System.out.println(tranposedData.toString());

        for (List<Integer> tranposedDatum : tranposedData) {
            Double max = (double)Collections.max(tranposedDatum);
            ArrayList<Double> tfTemp = new ArrayList<>();
            for (int i = 0; i < tranposedDatum.size(); i++) {
                Double occurrence = (double) tranposedDatum.get(i);
                tfTemp.add(occurrence/max);
            }
        }
    }

    public static <T> List<List<T>> transpose(ArrayList<ArrayList<T>> table) {
        List<List<T>> ret = new ArrayList<List<T>>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<T> col = new ArrayList<T>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

}