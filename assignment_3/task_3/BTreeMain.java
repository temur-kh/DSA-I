import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BTreeMain {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        //file reader and writer
        Scanner scan = new Scanner(new File("input.txt"));
        FileWriter file = new FileWriter(new File("output.txt"));

        //input
        String line = scan.nextLine();
        Comparable[] number = parseLine(line);

        //create a tree and insert keys
        BTree<Comparable> tree = new BTree<>();
        for(int i = 0; i != number.length; i++)
        {
            tree.insert(number[i]);
        }

        //traverse and output result
        ArrayList<String> traverse = tree.traverse();
        for(int i = 0; i != traverse.size(); i++)
        {
            file.write(traverse.get(i) + " ");
        }

        //closing
        scan.close();
        file.close();
    }

    /**
     * Method used to parse a line into an array either of integers or of characters.
     * @param line line to be parsed.
     * @return an array of comparable keys: integers or characters.
     */
    public static Comparable[] parseLine(String line)
    {
        String[] sequence = line.split(" ");

        //if a letter is found
        if(sequence.length>0 && Character.isLetter(sequence[0].charAt(0)))
        {
            Character[] letters = new Character[sequence.length];
            for(int i = 0; i != sequence.length; i++)
            {
                letters[i] = sequence[i].charAt(0);
            }
            return letters;
        }
        //if a number is found
        else
        {
            Integer[] integers = new Integer[sequence.length];
            for(int i = 0; i != sequence.length; i++)
            {
                integers[i] = Integer.parseInt(sequence[i]);
            }
            return integers;
        }
    }
}