import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CMSC409_A4
{
    public static void main(String[] args) throws FileNotFoundException {
        //Get from file instead
        //String sentences = "The autonomous sedan will be able to travel sedan on any type of road at speeds of up to 60 miles per hour. \b On future of machine learning, sedan Ray Kurzweil has predicted that we are only 28 years away from the Singularity or when self-improving artificial super-intelligence will far exceed human intelligence.\b This gets the car from 0 to 60 miles per hour (that is, to 97 kilometers per hour) in 3.2 seconds.";

        //Get tokens for each sentence
        Tokenizer t = new Tokenizer("sentences.txt");
        String[] tokens = t.getTokens();

        //for(int i=0; i<tokens.length; i++){System.out.println(tokens[i]);}

        //Build and print TDM
        TDMer tdm = new TDMer(tokens);
        //tdm.printTDM();
    }
}
