/**
 * Class for solving White Rabbit problem using Dynamic Programming strategy
 * Contains one method solve()
 */
public class DynamicProgramming {
    public DynamicProgramming(){}

    /**
     * Solve problem (Dynamic Programming strategy): find maximum number of second White Rabbit can buy with his budget
     * @param clocks clocks (time, cost) that White Rabbit can buy
     * @param budget number of cents that White Rabbit has
     * @return maximum number of seconds (time) White Rabbit can buy
     */
    static int solve(Clock[] clocks, int budget)
    {
        //create a matrix for computations
        int[][] dp = new int[clocks.length + 1][budget + 1];

        //make a base of dynamics
        for(int i = 0; i <= budget; i++)
        {
            dp[0][i] = 0;
        }

        //implementation of dynamic programming
        for(int i = 1; i <= clocks.length; i++)
        {
            for(int j=0; j <= budget; j++)
            {
                if(clocks[i - 1].getCost() > j)
                {
                    dp[i][j] = dp[i - 1][j];
                }
                else
                {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - clocks[i - 1].getCost()] + clocks[i - 1].getTime());
                }
            }
        }

        //dp[clock.length][budget] contains the answer
        return dp[clocks.length][budget];
    }
}