/**
 * Class used to store pair (time, cost) of clock and have access to it
 */

public class Clock {

    private int time;
    private int cost;

    public Clock(int time,int cost)
    {
        this.time=time;
        this.cost=cost;
    }

    /**
     * @return time of clock
     */
    public int getTime()
    {
        return time;
    }

    /**
     * @return cost of clock
     */
    public int getCost()
    {
        return cost;
    }
}