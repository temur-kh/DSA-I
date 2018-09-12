import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JosephusProblem {
    public static void main(String[] args) throws IOException
    {
        //Input of data
        Scanner scan = new Scanner(new File("input.txt"));
        String[] scannedArray = scan.nextLine().split(" ");
        String[] names = new String[scannedArray.length - 1];
        for(int i = 0; i != scannedArray.length - 1; i++)
            names[i] = scannedArray[i];
        int k = Integer.parseInt(scannedArray[scannedArray.length - 1]);

        //Implementation of algorithm using LinkedQueue
        LinkedQueue queue = new LinkedQueue(names);
        int counter = 0;
        while(queue.size() > 1)
        {
            counter++;
            if(counter == k)
            {
                queue.pop();
                counter = 0;
            }
            else queue.next();
        }

        //Output
        FileWriter file = new FileWriter(new File("output.txt"));
        file.write(queue.front());
        file.flush();
        file.close();
    }
}

/**
 * LinkedQueue is a queue with principle FIFO.
 * Its construction uses links:
 * Nodes that contain value and link to the next element of the queue.
 */
class LinkedQueue {

    private class Node //Class-helper
    {
        String value;
        Node next;
    }

    private Node first; //first Node of the queue
    private Node last;  //last Node of the queue
    private int size;      //the number of Nodes in the queue

    /**
     * Initializes an empty queue
     */
    public LinkedQueue()
    {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Initializes a queue with initial Nodes in it
     * @param values Nodes to be included in a queue initially
     */
    public LinkedQueue(String[] values)
    {
        first = null;
        last = null;
        size = 0;
        for(int i = 0; i != values.length; i++)
            push(values[i]);
    }
    /**
     * @param value the Node to add to the end of the queue
     */
    public void push(String value)
    {
        Node oldLast = last;
        last = new Node();
        last.value = value;
        last.next = null;
        if(isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    /**
     * @return the value of the Node from the front of the queue.
     * If the queue is empty it throws Exception.
     */
    public String front()
    {
        if(!isEmpty()) return first.value;
        else throw new NoSuchElementException("Queue is empty!");
    }

    /**
     * @return the value of the Node from the front of the queue
     * and deletes this Node
     * If the queue is empty it throws Exception.
     */
    public String pop()
    {
        if(!isEmpty())
        {
            Node oldFirst = first;
            first = oldFirst.next;
            size--;
            if(isEmpty()) last = null;
            return oldFirst.value;
        }
        else throw new NoSuchElementException("Queue is empty!");
    }

    /**
     * @return the current front Node and
     * throws it to the end of the queue.
     * If the queue is empty it throws Exception.
     */
    public String next()
    {
        if(!isEmpty())
        {
            String Node = pop();
            push(Node);
            return Node;
        }
        else throw new NoSuchElementException("Queue is empty!");
    }

    /**
     * @return True if the queue is empty;
     * False otherwise
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * @return the number of Nodes in the queue
     */
    public int size()
    {
        return size;
    }
}