import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AVL
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        //file reader and writer
        Scanner scan = new Scanner(new File("input.txt"));
        FileWriter file = new FileWriter(new File("output.txt"));

        //input
        String line = scan.nextLine();
        Integer[] number = parseLine(line);

        //create a tree and insert keys
        AVLTree<Integer> tree = new AVLTree<>();
        for(int i = 0; i != number.length; i++)
        {
            tree.insert(number[i]);
        }

        //count number of smaller elements
        int counter = 0;
        for(int i = 0; i != number.length; i++)
        {
            int n = tree.countSmallerNumbers(number[i]);
            counter += n;
        }
        file.write(counter + "");

        //closing
        scan.close();
        file.close();
    }

    /**
     * Method used to parse a line into an array of integers.
     * @param line line to be parsed.
     * @return an array of integers.
     */
    public static Integer[] parseLine(String line)
    {
        String[] numbers = line.split(" ");
        Integer[] integers = new Integer[numbers.length];
        for(int i = 0; i != numbers.length; i++)
        {
            integers[i] = Integer.parseInt(numbers[i]);
        }
        return integers;
    }
}