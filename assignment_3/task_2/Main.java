import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        //file reader and writer
        Scanner scan = new Scanner(new File("input.txt"));
        FileWriter file = new FileWriter(new File("output.txt"));

        //input
        String sequence = scan.nextLine();
        Integer[] numbers = parseLine(sequence);

        //create a tree and insert keys
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for(int i = 0; i != numbers.length; i++)
        {
            tree.insert(numbers[i]);
        }

        //FIND
        Integer number = scan.nextInt();
        number = tree.find(number);
        file.write(number + "\n");

        //REMOVE
        number = scan.nextInt();
        tree.remove(number);

        //INSERT
        number = scan.nextInt();;
        tree.insert(number);

        //TRAVERSE
        ArrayList<String> traverse = tree.traverse();
        for(int i = 0; i != traverse.size(); i++)
        {
            file.write(traverse.get(i) + " ");
        }
        file.write("\n");

        //PRINT the tree
        file.write("BST:\n");
        ArrayList<String> lines = tree.print();
        for(int i = 0; i != lines.size(); i++)
        {
            file.write(lines.get(i) + "\n");
        }

        //PRINT the mirror of tree
        file.write("BSMT:\n");
        lines = tree.mirror();
        for(int i = 0; i != lines.size(); i++)
        {
            if(i != lines.size() - 1) file.write(lines.get(i) + "\n");
            else file.write(lines.get(i));
        }

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