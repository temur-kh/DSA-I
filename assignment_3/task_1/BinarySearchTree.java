import sun.misc.Queue;
import java.util.ArrayList;

/**
 * This generic class implements Binary Search Tree. It implements interface ITree.
 * Some of useful methods of it are find(key), insert(key), remove(key).
 * However, BST does not guarantee good time complexity, since it does not use balancing or random-structuring.
 * @param <K> this is the type of the data stored in tree.
 */
public class BinarySearchTree<K extends Comparable> implements ITree<K>{
    //root node and size of tree
    public Node root;
    protected int size;

    public BinarySearchTree()
    {
        size = 0;
    }

    /**
     * Method use to find a key in tree.
     * @param key a key to be found.
     * @return key if found, otherwise return null.
     */
    @Override
    public K find(K key) {
        return key != null ? find(root, key) : null;
    }

    /**
     * Private recursive method used by previous method to go through the tree and find a key.
     * @param current start node.
     * @param key a key to be found.
     * @return key if found, otherwise return null.
     */
    private K find(Node current, K key)
    {
        //not found
        if(current == null)
            return null;
        //if key is more than key in current node, go to right
        else if(current.key.compareTo(key) < 0)
            key = find(current.right,key);
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
            key = find(current.left,key);
        //found
        else
            return (K) current.key;
        return key;
    }

    /**
     * Method used to insert a key into the tree.
     * @param key a key to be inserted.
     */
    @Override
    public void insert(K key) {
        if(key != null)
            root = insert(root, key);
        size++;
    }

    /**
     * Private recursive method used by previous method to go through the tree and insert a key.
     * @param current start node.
     * @param key a key to be inserted.
     * @return start node (special feature of implementation, important for correct recursion).
     */
    private Node insert(Node current, K key)
    {
        //found a position for new node
        if(current == null)
            return new Node(key);

        //if key is more than key in current node, go to right
        if(current.key.compareTo(key) < 0)
            current.right = insert(current.right, key);
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
            current.left = insert(current.left, key);
        //found the same key: increase amount of it
        else
            current.amount++;

        return current;
    }

    /**
     * Method used to remove a key from the tree.
     * In case there are more than one such key, deletes all of them.
     * @param key a key to be removed.
     */
    @Override
    public void remove(K key)
    {
        if(key != null)
            root = remove(root, key);
    }

    /**
     * Private recursive method used by previous method to go through the tree and delete a key.
     * @param current start node.
     * @param key a key to be removed.
     * @return start node (special feature of implementation, important for correct recursion).
     */
    private Node remove(Node current, K key)
    {
        //key not found
        if(current == null)
            return null;

        //if key is more than key in current node, go to right
        if(current.key.compareTo(key) < 0)
            current.right = remove(current.right, key);
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
            current.left = remove(current.left, key);
        //found
        else
        {
            size -= current.amount;

            //a node to be removed is a leaf
            if(current.left == null) return current.right;
            if(current.right == null) return current.left;

            //otherwise, replace a node with the successor
            current.key = minimum(current.right).key;
            current.amount = minimum(current.right).amount;
            current.right = remove(current.right, (K) current.key);
            size += current.amount;
        }
        return current;
    }

    /**
     * Protected method used to find minimal node in a subtree.
     * @param current start node.
     * @return minimal node of the subtree.
     */
    protected Node minimum(Node current)
    {
        return current.left == null ? current : minimum(current.left);
    }

    /**
     * Method used to make an inorder traverse through the tree and output all keys.
     * @return an array of strings - values of all keys in not descending order.
     */
    @Override
    public ArrayList<String> traverse() {
        traverseResult = new ArrayList<>();
        traverse(root);
        return traverseResult;
    }

    //extra variable for method traverse
    private ArrayList<String> traverseResult;

    /**
     * Private recursive method used by previous method to go through the tree and add key values to the tempLine.
     * @param current start node.
     */
    private void traverse(Node current)
    {
        //go to the left child if possible
        if(current.left != null)
            traverse(current.left);

        //add key value of node to the tempLine
        traverseResult.add(current.key.toString());

        //go to the right child if possible
        if(current.right != null)
            traverse(current.right);
    }

    /**
     * Method used to print the tree, that is to print all parent-child relations.
     * @return list of strings - relations.
     * @throws InterruptedException
     */
    @Override
    public ArrayList<String> print() throws InterruptedException {
        //queue used for breadth-find search
        Queue<Node> queue = new Queue<>();
        ArrayList<String> lines = new ArrayList<>();

        //BFS
        queue.enqueue(root);
        while(!queue.isEmpty())
        {
            String line = "";
            Node current = queue.dequeue();

            //add value of parent to a line
            line += current.key + " ";

            //if it has left child, add its value
            if(current.left != null)
            {
                line += current.left.key + " ";
                //if the child has at least one child, enqueue a node to queue
                if(current.left.left != null || current.left.right != null)
                    queue.enqueue(current.left);
            }

            //if it has right child, add its value
            if(current.right != null)
            {
                line += current.right.key + " ";
                //if the child has at least one child, enqueue a node to queue
                if(current.right.left != null || current.right.right != null)
                    queue.enqueue(current.right);
            }
            lines.add(line);
        }
        return lines;
    }

    /**
     * Method used to print the tree, that is to print all parent-child relations.
     * Almost the same like a method print, but it prints tree in mirror form.
     * @return list of strings - relations.
     * @throws InterruptedException
     */
    public ArrayList<String> mirror() throws InterruptedException
    {
        //queue used for breadth-find search
        Queue<Node> queue = new Queue<>();
        ArrayList<String> lines = new ArrayList<>();

        //BFS
        queue.enqueue(root);
        while(!queue.isEmpty())
        {
            String line = "";
            Node current = queue.dequeue();

            //add value of parent to a line
            line += current.key + " ";

            //if it has right child, add its value
            if(current.right != null)
            {
                line += current.right.key + " ";
                //if the child has at least one child, enqueue a node to queue
                if(current.right.left != null || current.right.right != null)
                    queue.enqueue(current.right);
            }

            //if it has left child, add its value
            if(current.left != null)
            {
                line += current.left.key + " ";
                //if the child has at least one child, enqueue a node to queue
                if(current.left.left != null || current.left.right != null)
                    queue.enqueue(current.left);
            }
            lines.add(line);
        }
        return lines;
    }

    /**
     * @return size of the tree
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * @return true if the tree is empty, false otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }
}