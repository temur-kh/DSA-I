import sun.misc.Queue;
import java.util.ArrayList;

/**
 * This generic class implements B-Tree. It implements interface IBTree.
 * Class B-Tree implements its own methods insert(key), find(key), traverse() and etc.
 * Tree has its own order and explains the amount of children of node and number of keys it contains.
 * Tree uses some special algorithms of splitting and adding for making time complexity of insertion be logarithmic.
 * @param <K> this is the type of the data stored in tree.
 */
public class BTree<K extends Comparable> implements IBTree<K> {
    //order of tree.
    //constant may be changed and it will affect on max number of keys in each node.
    private static final int T = 3;

    //root node and size of tree
    private BNode root;
    private int size;

    public BTree() {}

    /**
     * Method use to find a key in tree.
     * @param key a key to be found.
     * @return key if found, otherwise return null.
     */
    @Override
    public K find(K key)
    {
        return key != null ? find(root, key) : null;
    }

    /**
     * Private recursive method used by previous method to go through the tree and find a key.
     * @param current start node.
     * @param key a key to be found.
     * @return key if found, otherwise return null.
     */
    private K find(BNode current, K key)
    {
        boolean pathFound = false;

        //not found
        if(current == null)
            return null;

        //find a path to the next child or a key itself
        for(int i = 0; i != current.keys.size(); i++)
        {
            //go to a child
            if(current.getKey(i).key.compareTo(key) > 0)
            {
                pathFound = true;
                key = find(current.getKey(i).left, key);
                break;
            }
            //found a key
            else if(current.getKey(i).key.compareTo(key) == 0)
                return key;
        }

        //if still did not find a path, go to the last child
        if(!pathFound)
            key = find(current.getLastKey().right, key);
        return key;
    }

    /**
     * Method used to insert a key into the tree.
     * @param key a key to be inserted.
     */
    @Override
    public void insert(K key) {
        if(key != null)
        {
            //initial case
            if(root == null)
                root = new BNode(new Key(key));
            //otherwise insert
            else
            {
                Key keyNode = insert(root, new Key(key));
                if(keyNode != null)
                    root = new BNode(keyNode);
            }
            size++;
        }
    }

    /**
     * Private recursive method used by previous method to go through the tree and insert a key.
     * @param current start node.
     * @param key a key to be inserted.
     * @return start node (special feature of implementation, important for correct recursion).
     */
    private Key insert(BNode current, Key key)
    {
        //if not a leaf
        if(current.getFirstKey().left != null)
        {
            //find a path to the next child or a key itself
            for(int i = 0; i != current.keys.size(); i++)
            {
                //go to a child
                if (current.getKey(i).key.compareTo(key.key) > 0)
                {
                    Key keyNode = insert(current.getKey(i).left, key);

                    //no extra key to add
                    if (keyNode == null)
                    {
                        updateFeatures(current);
                        return null;
                    }
                    //otherwise, try to add
                    else
                        return addKey(current, keyNode);
                }
                //found a key, just increase its amount
                else if (current.getKey(i).key.compareTo(key.key) == 0)
                {
                    current.getKey(i).amount++;
                    updateFeatures(current);
                    return null;
                }
            }

            //if did not find a path, go to last child
            Key keyNode = insert(current.getLastKey().right, key);
            if (keyNode == null)
            {
                updateFeatures(current);
                return null;
            }
            else
                return addKey(current, keyNode);
        }
        //if a leaf
        else
            return addKey(current, key);
    }

    /**
     * Method used to add a key into a node in a proper position.
     * @param current a node for adding.
     * @param key a key to be added.
     * @return if there is extra key to be inserted in ancestor, return this key, otherwise return null.
     */
    private Key addKey(BNode current, Key key)
    {
        int index = 0;

        //find a position to add the key to and add it
        for(int i = 0; i != current.keys.size(); i++)
        {
            if(current.getKey(i).key.compareTo(key.key) > 0)
                break;
            else if(current.getKey(i).key.compareTo(key.key) == 0)
            {
                current.getKey(i).amount++;
                return null;
            }
            index++;
        }
        current.keys.add(index, key);

        //update links to children for neighbors of an added key
        if(index > 0)
            current.getKey(index - 1).right = current.getKey(index).left;
        if(index < current.keys.size() - 1)
            current.getKey(index + 1).left = current.getKey(index).right;

        //if node is overloaded, split it and return an extra key
        if(current.keys.size() == T)
            return split(current);
        else
        {
            updateFeatures(current);
            return null;
        }
    }

    /**
     * Method used to split a node into two nodes and return middle key to upper level for adding.
     * @param current a node to be split.
     * @return if there is extra key to be inserted in ancestor, return this key, otherwise return null.
     */
    private Key split(BNode current)
    {
        //extra key for returning
        Key newKey = current.getKey(current.keys.size() / 2);

        //new left and right nodes
        BNode newLeft = new BNode();
        BNode newRight = new BNode();

        //insert keys of node to new nodes and update features
        for(int i = 0; i != current.keys.size() / 2; i++)
        {
            newLeft.keys.add(current.keys.get(i));
            newRight.keys.add(current.keys.get(i + current.keys.size() / 2 + 1));
        }
        updateFeatures(newLeft);
        updateFeatures(newRight);
        newKey.left = newLeft;
        newKey.right = newRight;

        return newKey;
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
    private void traverse(BNode current)
    {
        if(current == null)
            return;

        //go to a child and then add a key and repeat it
        for(int i = 0; i != current.keys.size(); i++)
        {
            traverse(current.getKey(i).left);
            for(int j = 0; j != current.getKey(i).amount; j++)
                traverseResult.add(current.getKey(i).key.toString());
        }

        //go to the last child
        traverse(current.getLastKey().right);
    }

    /**
     * Method used to print the tree, that is to print all parent-child relations.
     * @return list of strings - relations.
     * @throws InterruptedException
     */
    public ArrayList<String> print() throws InterruptedException {
        //queue used for breadth-find search
        Queue<BNode> queue = new Queue<>();
        ArrayList<String> lines = new ArrayList<>();

        //BFS
        if(root != null)
            queue.enqueue(root);
        while(!queue.isEmpty())
        {
            BNode current = queue.dequeue();

            //check - is not a leaf
            if(current.getFirstKey().left == null)
                continue;

            //add values of parent to a line
            String line = "";
            line += current.toString() + ": ";

            //add values of all children to a line
            for(int i = 0; i != current.keys.size(); i++)
            {
                line += current.getKey(i).left.toString() + "; ";
                queue.enqueue(current.getKey(i).left);
                if(i == current.keys.size() - 1)
                {
                    line += current.getKey(i).right.toString() + ";";
                    queue.enqueue(current.getKey(i).right);
                }
            }
            lines.add(line);
        }
        return lines;
    }

    /**
     * Method used to update such variables of node like height and size.
     * @param current a node for updating its features.
     */
    private void updateFeatures(BNode current)
    {
        if(current == null) return;
        int height = -1,size = 0;

        //calculate new height and size of a node
        for(int i = 0; i != current.keys.size(); i++)
        {
            height = Math.max(height, height(current.getKey(i).left));
            size += size(current.getKey(i).left);
            if(i == current.keys.size() - 1)
            {
                height = Math.max(height, height(current.getKey(i).right));
                size += size(current.getKey(i).right);
            }
        }
        current.height = height + 1;
        for(int i = 0; i != current.keys.size(); i++)
            size += current.getKey(i).amount;
        current.size = size;
        return;
    }

    /**
     * @param current a node.
     * @return maximal height of the node from leaves. return -1 if node is null
     */
    private int height(BNode current)
    {
        return current != null ? current.height : -1;
    }

    /**
     * @param current a node.
     * @return number of keys in a subtree with root-node as current. return 0 if node is null.
     */
    private int size(BNode current)
    {
        return current != null ? current.size : 0;
    }

    /**
     * @return size of the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true if the tree is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

}