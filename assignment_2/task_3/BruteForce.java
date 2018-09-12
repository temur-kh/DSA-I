/**
 * Class for solving White Rabbit problem using Brute Force strategy
 * Contains one method solve()
 */
public class BruteForce {
    public BruteForce(){}

    /**
     * Solve problem (Brute-Force strategy): find maximum number of second White Rabbit can buy with his budget
     * Algorithm is based on using bitmasks
     * @param clocks clocks (time, cost) that White Rabbit can buy
     * @param budget number of cents that White Rabbit has
     * @return maximum number of seconds (time) White Rabbit can buy
     */
    static int solve(Clock[] clocks, int budget)
    {
        int answer = 0;
        int combinations = (int) Math.pow(2, clocks.length);

        //iterate all possible combinations
        for(int i = 0; i != combinations; i++)
        {
            //create local variables
            int currentComb = i;
            int index = 0;
            int currentTime = 0;
            int currentCost = 0;
            //bitMask is used to identify clocks to buy in this combination
            boolean[] bitMask = new boolean[clocks.length];

            //initialize values of bitMask
            while(currentComb > 0)
            {
                bitMask[index] = currentComb % 2 == 1;
                index++;
                currentComb /= 2;
            }
            while(index < clocks.length)
            {
                bitMask[index] = false;
                index++;
            }

            //count Time and Cost of clocks to buy in this combination
            for(int j = 0; j != bitMask.length; j++)
            {
                if(bitMask[j])
                {
                    currentTime += clocks[j].getTime();
                    currentCost += clocks[j].getCost();
                }
            }

            //check budget and compare result with answer
            if(currentCost <= budget)
            {
                answer = Math.max(answer, currentTime);
            }
        }
        return answer;
    }
}