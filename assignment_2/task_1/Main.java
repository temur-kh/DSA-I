import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException
    {
        //Input text
        Scanner scan = new Scanner(new File("input.txt"));
        ArrayList<String> text = new ArrayList<>();
        while(scan.hasNext())
        {
            String word = scan.next();
            word = word.toLowerCase();
            word = format(word);
            text.add(word);
        }

        //Adding words into the table and counting them
        HashTable<String, Integer> hashTable = new HashTable<>();
        ArrayList<String> banList = getBanList();
        for(int i = 0; i != text.size(); i++)
        {
            String word = text.get(i);

            //Word to singular form
            word = checkPluralForm(word, text);

            //Add to the table if possible
            if(banList.contains(word) || word.equals(" ") || word.equals("")) continue;
            if(!hashTable.contains(word))
            {
                hashTable.set(word, 1);
            }
            else
            {
                int count = hashTable.get(word);
                hashTable.set(word, count + 1);
            }
        }

        //Sort output results
        HashSet<Entry<String, Integer>> entrySet = (HashSet<Entry<String,Integer>>) hashTable.entrySet();
        Entry<String, Integer>[] sortList = sortedSet(entrySet);
        ArrayList<Entry<String, Integer>> answer = new ArrayList<>();
        for(int i = 0; i != sortList.length; i++)
        {
            answer.add(new Entry<>(sortList[i].key, sortList[i].value));
        }

        //Clear hashTable
        HashSet<String> keySet = (HashSet<String>) hashTable.keySet();
        for(String key: keySet)
        {
            hashTable.remove(key);
        }

        //Output
        FileWriter file = new FileWriter(new File("output.txt"));
        for(int i = 0; i != answer.size(); i++)
        {
            if(i != answer.size() - 1) file.write("<\"" + answer.get(i).key + "\", " + answer.get(i).value + ">\n");
            else file.write("<\"" + answer.get(i).key + "\", " + answer.get(i).value + ">");
        }

        //Closing files
        file.flush();
        scan.close();
        file.close();
    }

    /**
     * @param entrySet gets a set of Entries
     * @return sorted array of Entries from set
     */
    public static Entry<String,Integer>[] sortedSet(Set<Entry<String, Integer>> entrySet)
    {
        int i = 0,count = 0;
        for(Entry<String,Integer> entry: entrySet)
        {
            if(entry.value > 1)
                count++;
        }
        Entry<String, Integer>[] sortedList = new Entry[count];
        for(Entry<String, Integer> entry: entrySet)
        {
            if(entry.value > 1) {
                sortedList[i] = new Entry<>(entry.key, entry.value);
                i++;
            }
        }
        Arrays.sort(sortedList, Comparator.comparing(p -> p.key));
        return sortedList;
    }

    /**
     * Check word for plural form
     * @param word word to be checked
     * @param text text to be used in detecting singular words
     * @return singular form of word
     */
    public static String checkPluralForm(String word, ArrayList<String> text)
    {
        if(word.charAt(word.length()-1)=='s')
        {
            String singular = word.substring(0, word.length() - 1);
            if(getBanList().contains(singular))
                return word;
            else if(text.contains(singular))
                return singular;
        }
        return word;
    }

    /**
     * Checks word for short endings and punctuation symbols
     * @param word word to be formatted
     * @return word after checking and formatting
     */
    public static String format(String word)
    {
        //check for punctuation symbols
        if(!Character.isLetter(word.charAt(word.length() - 1)))
            word = word.substring(0, word.length() - 1);
        //check for short endings
        if(word.length() > 2 && (word.substring(word.length() - 2, word.length()).equals("'s")
                || word.substring(word.length() - 2, word.length()).equals("'d")))
            word = word.substring(0, word.length() - 2);
        if(word.length() > 3 && word.substring(word.length() - 2,word.length()).equals("'re"))
            word = word.substring(0, word.length() - 3);

        return word;
    }

    /**
     * @return a list of forbidden words
     */
    public static ArrayList<String> getBanList()
    {
        String[] banArray = new String[]{
                "a", "in", "at", "to", "on", "not", "for", "s", "'s", "'d", "'re", "is", "are", "am", "has", "i", "we", "you"};

        ArrayList<String> banList = new ArrayList<>();
        for(String word: banArray)
        {
            banList.add(word);
        }

        return banList;
    }
}