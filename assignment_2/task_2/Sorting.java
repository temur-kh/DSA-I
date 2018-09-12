import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sorting {
    public static void main(String[] args) throws IOException
    {
        //Input array of integers and chars
        Scanner scan = new Scanner(new File("input.txt"));
        String line = scan.nextLine();

        //Call 5 sorting algorithms with argument of input data and get answers
        SelectionSort selectionSort = new SelectionSort();
        String answer1 = selectionSort.sort(line);

        BubbleSort bubbleSort = new BubbleSort();
        String answer2 = bubbleSort.sort(line);

        InsertionSort insertionSort = new InsertionSort();
        String answer3 = insertionSort.sort(line);

        MergeSort mergeSort = new MergeSort();
        String answer4 = mergeSort.sort(line);

        QuickSort quickSort = new QuickSort();
        String answer5 = quickSort.sort(line);

        //If all answers are equal output answer, otherwise report ERROR
        FileWriter file = new FileWriter(new File("output.txt"));
        if(answer1.equals(answer2) && answer2.equals(answer3) && answer3.equals(answer4) && answer4.equals(answer5))
            file.write(answer1);
        else
            file.write("ERROR");

        //Closing
        file.flush();
        file.close();
        scan.close();
    }

}