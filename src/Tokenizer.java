class Tokenizer
{
    private String[] tokens;
    private String[] stopWords;

    Tokenizer(String[] input)
    {
        this.stopWords=("a able about across after all almost also am among an and any are as at be because " +
                "been but by can cannot could dear did do does either else ever every for from get got had " +
                "has have he her hers him his how however i if in into is it its just least let like likely " +
                "may me might most must my neither no nor not of off often on only or other our own rather " +
                "said say says she should since so some than that the their them then there these they this " +
                "tis to too twas us wants was we were what when where which while who whom why will with would " +
                "yet you your").split(" ");
        this.tokens=this.tokenize(input);
    }

    private String[] tokenize(String[] input)
    {
        String temp;

        for(int i=0; i<input.length; i++)
        {
            //Remove punctuation and numbers
            temp = input[i].replaceAll("[^a-zA-Z ]", "");

            //Remove stop words
            temp=removeStopWords(temp.toLowerCase());

            input[i]=temp;
        }

        return input;
    }

    //Remove stop words from each sentence
    private String removeStopWords(String sentence)
    {

        String[] temp = sentence.split(" ");
        StringBuilder newSentence= new StringBuilder();

        for(int i=0; i<temp.length;i++)
        {
            for(String stop : stopWords)
            {
                if(temp[i].equals(stop))
                {
                    temp[i]="";
                }
            }

            newSentence.append(" ").append(temp[i]);
        }

        return newSentence.toString();
    }

    String[] getTokens()
    {
        return this.tokens;
    }
}