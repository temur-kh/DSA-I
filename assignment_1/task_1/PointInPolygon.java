import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
public class PointInPolygon {
    public static void main(String[] args) throws IOException
    {
        //Setting DecimalFormat for floating numbers in output
        DecimalFormat df = new DecimalFormat("#.#");
        df.setMinimumFractionDigits(0);
        df.setRoundingMode(RoundingMode.HALF_UP);

        //Input
        Scanner scan = new Scanner(new File("input.txt"));
        String firstLine = scan.nextLine();
        String secondLine = scan.nextLine();
        Polygon polygon = new Polygon(getCoordinates(firstLine));
        Point point = getCoordinates(secondLine)[0];

        //Getting results and output
        FileWriter file = new FileWriter(new File("output.txt"));
        if(polygon.inside(point))
            file.write("Yes\n");
        else
            file.write("No\n");
        double area = polygon.getArea();
        file.write(df.format(area));
        file.flush();
        file.close();
    }

    /**
     *
     * @param line line is the input string of coordinates in unreadable format
     * @return array of Points (coordinates)
     */
    public static Point[] getCoordinates(String line)
    {
        //replace unnecessary symbols and split the line
        line = line.replace("{", "");
        line = line.replace("}", "");
        line = line.replace("(", "");
        line = line.replace(")", "");
        line = line.replace(",", " ");
        String[] numbers = line.split(" ");

        //write coordinates into an array
        Point[] points = new Point[numbers.length / 2];
        for(int i = 0; i < numbers.length; i += 2)
        {
            double X = Double.parseDouble(numbers[i]);
            double Y = Double.parseDouble(numbers[i + 1]);
            points[i / 2]=new Point(X, Y);
        }
        return points;
    }
}

/**
 * Class Polygon is used as a container for points creating shape.
 * It includes array of edges formed by adjacent points.
 * Additionally, it has methods operating upon area of polygon
 * and method deciding whether some point is inside of polygon or not.
 */
class Polygon {

    private Point[] points;                             //coordinates of vertices
    private Edge[] edges;                               //edges (segments of polygon
    double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE; //min and max values of points in X and Y axis
    double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE; //to be recomputed after creation of object
    private double area;                                //area of polygon

    public Polygon(Point[] points)
    {
        this.points = points;

        //recompute min and max values
        for(int i = 0; i != points.length; i++)
        {
            minX = Math.min(minX, points[i].getX());
            maxX = Math.max(maxX, points[i].getX());
            minY = Math.min(minY, points[i].getY());
            maxY = Math.max(maxY, points[i].getY());
        }

        //create edges of polygon
        int size = points.length <= 2 ? points.length - 1 : points.length;
        edges = new Edge[size];
        for(int i = 0; i < size; i++)
            edges[i] = new Edge(points[i], points[(i + 1)%points.length]);

        //calculate the area of polygon
        calculateArea();
    }

    /**
     * We use Crossing Number Algorithm in order to consider if given Point is inside of polygon or not.
     * For this, we create a ray starting in given Point position and going horizontally right way.
     * We compute the number of intersections of this ray with edges of polygon.
     * If the number is odd then the Point is inside of polygon, else outside.
     * (some cases when we do NOT count intersections are explained in Edge.intersectWithRay(Point point) method)
     * Additioinally, we consider several special cases when Point lies on some edge or is vertex of polygon.
     *
     * @param horizontalRayStart horizontalRayStart is the Point calculated to be inside/outside
     *                           also, it is the start point of the horizontal right-going ray
     * @return true if the Point is inside of polygon, false otherwise
     */
    public boolean inside(Point horizontalRayStart)
    {
        //Point lies on some edge or is vertex of polygon
        for(Edge edge: edges)
        {
            if(edge.isOnEdge(horizontalRayStart) || edge.isVertex(horizontalRayStart))
                return true;
        }

        //compute the number of intersections
        int counter = 0;
        for(Edge edge: edges)
        {
            if(edge.intersectWithRay(horizontalRayStart))
                counter++;
        }
        return counter % 2 == 1;
    }

    /**
     * This method makes calculation of polygon's area using Monte Carlo method.
     * Accuracy is 1E-4: If next iteration of Monte-Carlo simulation change area
     * by less than 1e-4, the computation ends.
     */
    private void calculateArea()
    {
        double boundArea = (maxX - minX) * (maxY - minY); //area of bounding rectangle
        area = boundArea;
        long allPoints = 0, pointsInside = 0;          //counters

        //declaration of used variables
        double delta = 1;             //change between old area and newly computed area
        Random random = new Random();
        double E = 1e-4;              //accuracy

        //simulation of loop
        while(Math.abs(delta) >= E)
        {
            //put 10 random points at once to avoid mistaken breaks of loop
            for(int i = 0; i != 10; i++)
            {
                double X = minX + random.nextDouble() * (maxX - minX);
                double Y = minY + random.nextDouble() * (maxY - minY);
                if(inside(new Point(X, Y)))
                    pointsInside++;
                allPoints++;
                delta = area - boundArea * (pointsInside * 1.0 / allPoints * 1.0);
                area = boundArea * (pointsInside * 1.0 / allPoints * 1.0);
            }
        }
    }

    /**
     *
     * @return value of polygon's area.
     */
    public double getArea()
    {

        return area;
    }
}

/**
 * Class Edge is used to store together two points that form an edge of polygon.
 * Class has method operating with intersection of some ray with edge,
 * method deciding whether some point is vertex of edge or not, and
 * method deciding whether some point is on edge or not.
 */
class Edge{
    //vertices of edge
    Point start;
    Point end;

    Edge(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    /**
     * Decide whether some point is vertex of edge or not
     * @param point point to be considered
     * @return true if point is vertex of edge, false otherwise.
     */
    public boolean isVertex(Point point)
    {
        return (start.getX() == point.getX() && start.getY() == point.getY()) ||
                (end.getX() == point.getX() && end.getY() == point.getY());
    }

    /**
     * Decide whether some point is on edge or not
     * @param point point to be considered
     * @return true if point is on edge, false otherwise.
     */
    //(5 + 3) * 3 = 5 3 + 3 *
    public boolean isOnEdge(Point point)
    {
        if(start.getX() - end.getX() == 0)  //edge is vertical
        {
            return point.getX() == start.getX() &&
                    point.getY() <= Math.max(start.getY(), end.getY()) &&
                    point.getY() >= Math.min(start.getY(), end.getY());
        }
        else if(start.getY() - end.getY() == 0) //edge is horizontal
        {
            return point.getY() == start.getY() &&
                    point.getX() <= Math.max(start.getX(), end.getX()) &&
                    point.getX() >= Math.min(start.getX(), end.getX());
        }
        else                                //consider other case using function of line Y=K*X+B
        {
            double K = (start.getY() - end.getY()) / (start.getX() - end.getX());
            double B = start.getY() - start.getX() * K;

            //coordinates of point that belongs to edge-line and intersects ray
            double Y = point.getY();
            double X = (Y - B) / K;

            return point.getX() == X && Y <= Math.max(start.getY(), end.getY()) && Y >= Math.min(start.getY(), end.getY());
        }
    }

    /**
     * Operate with intersection of some ray with edge to check whether a Point is inside of polygon
     * or not using Crossing Number Algorithm.
     * The method decides whether a horizontal right-way ray from point intersects the edge.
     * There are two cases that we do NOT consider as intersection:
     * 1) when edge is horizontal
     * 2) when the ray intersects the edge in the upper vertex and the rest of edge is under the ray.
     * @param point point is a start point of horizontal right-way ray.
     * @return true if the ray intersects the edge, false otherwise.
     */
    public boolean intersectWithRay(Point point)
    {
        if(start.getX() - end.getX() == 0)      //edge is vertical
        {
            Point upperPoint = start.getY() >= end.getY() ? start : end;
            return point.getX() <= start.getX() &&
                    point.getY() <= Math.max(start.getY(), end.getY()) &&
                    point.getY() >= Math.min(start.getY(), end.getY()) &&
                    point.getY() != upperPoint.getY(); //point is not upper vertex of edge
        }
        else if(start.getY() - end.getY() == 0) //edge is horizontal
        {
            //we do NOT consider horizontal edges
            return false;
        }
        else                                //consider other case using function of line Y=K*X+B
        {
            double K = (start.getY() - end.getY()) / (start.getX() - end.getX());
            double B = start.getY() - start.getX() * K;

            //coordinates of point that belongs to edge-line and intersects ray
            double Y = point.getY();
            double X = (Y - B) / K;

            Point upperPoint = start.getY() >= end.getY() ? start : end;
            if(X == upperPoint.getX() && Y == upperPoint.getY())    //point is not upper vertex of edge
                return false;
            else
                return point.getX() <= X && X <= Math.max(start.getX(), end.getX()) &&
                        X >= Math.min(start.getX(), end.getX()) && Y <= Math.max(start.getY(), end.getY()) &&
                        Y >= Math.min(start.getY(), end.getY());
        }
    }
}

/**
 * Class Point is used to store (x,y) coordinates together
 * and get access to them.
 */
class Point
{
    private double x, y;
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
}