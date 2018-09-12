import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WhiteRabbit {
    public static void main(String[] args) throws IOException
    {
        //Input data and parse it
        Scanner scan = new Scanner(new File("input.txt"));
        String line = scan.nextLine();
        int budget = scan.nextInt();
        Clock[] clocks = parseData(line);

        //Find maximum number of time for given budget
        DynamicProgramming solver = new DynamicProgramming();
        int answer = solver.solve(clocks, budget);

        //Output answer
        FileWriter file = new FileWriter(new File("output.txt"));
        file.write(Integer.toString(answer));

        //Closing
        file.flush();
        file.close();
        scan.close();
    }

    /**
     * Parse given line into pairs of time and cost
     * @param line line contains numbers to be restored
     * @return array of elements of class Clock
     */
    static public Clock[] parseData(String line)
    {
        String[] numbers = line.split(" ");
        Clock[] clocks = new Clock[numbers.length / 2];
        for(int i = 0, j = 0; i != numbers.length; i += 2, j++)
        {
            int time = Integer.parseInt(numbers[i]);
            int cost = Integer.parseInt(numbers[i + 1]);
            clocks[j] = new Clock(time, cost);
        }
        return clocks;
    }
}