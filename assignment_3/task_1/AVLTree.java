/**
 * This generic class implements AVL Tree. It implements interface ITree.
 * The class AVLTree extends class BinarySearchTree and inherits some of its methods.
 * Anyway, class AVLTree implements its own methods insert(key), remove(key) and etc.
 * Most of the main methods (find, insert, remove) have time complexity logN (N - size of tree)
 * because of balancing the tree with trinode restructuring.
 * @param <K> this is the type of the data stored in tree.
 */
public class AVLTree<K extends Comparable> extends BinarySearchTree<K> implements ITree<K> {
    
    public AVLTree()
    {
        super();
    }

    /**
     * Method used to insert a key into the tree.
     * @param key a key to be inserted.
     */
    @Override
    public void insert(K key) {

        if(key != null)
        {
            root = insert((AVLNode) root, key);
            size++;
        }
    }

    /**
     * Private recursive method used by previous method to go through the tree and insert a key.
     * @param current start node.
     * @param key a key to be inserted.
     * @return start node (special feature of implementation, important for correct recursion).
     */
    private AVLNode insert(AVLNode current, K key)
    {
        //found a position for new node
        if(current == null)
            return new AVLNode(key);

        //if key is more than key in current node, go to right
        if(current.key.compareTo(key) < 0)
            current.right = insert(current.getRight(), key);
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
            current.left = insert(current.getLeft(), key);
        //found the same key: increase amount of it
        else
            current.amount++;

        //balance subtree if needed and update features
        current = balance(current);
        current.height = Math.max(height(current.getLeft()), height(current.getRight())) + 1;
        current.size = size(current.getLeft()) + size(current.getRight()) + current.amount;

        return current;
    }

    /**
     * Method used to remove a key from the tree.
     * In case there are more than one such key, deletes all of them.
     * @param key a key to be removed.
     */
    @Override
    public void remove(K key) {
        if(key != null)
            root = remove((AVLNode) root, key);
    }

    /**
     * Private recursive method used by previous method to go through the tree and delete a key.
     * @param current start node.
     * @param key a key to be removed.
     * @return start node (special feature of implementation, important for correct recursion).
     */
    private AVLNode remove(AVLNode current, K key)
    {
        //key not found
        if(current == null)
            return null;

        //if key is more than key in current node, go to right
        if(current.key.compareTo(key) < 0)
        {
            if(current.getRight() == null)
                return null;
            else
            {
                //go right
                current.right = remove(current.getRight(), key);

                //balance tree after deletion and update features
                current = balance(current);
                current.height = Math.max(height(current.getLeft()), height(current.getRight())) + 1;
                current.size = size(current.getLeft()) + size(current.getRight()) + current.amount;
            }
        }
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
        {
            if(current.getLeft() == null)
                return null;
            else
            {
                //go left
                current.left = remove(current.getLeft(), key);

                //balance tree after deletion and update features
                current = balance(current);
                current.height = Math.max(height(current.getLeft()), height(current.getRight())) + 1;
                current.size = size(current.getLeft()) + size(current.getRight()) + current.amount;
            }
        }
        else
        {
            size -= current.amount;

            //a node to be removed is a leaf
            if(current.getLeft() == null) return current.getRight();
            if(current.getRight() == null) return current.getLeft();

            //otherwise, replace a node with the successor
            current.key = minimum(current.getRight()).key;
            current.amount = minimum(current.getRight()).amount;
            current.right = remove(current.getRight(),(K) current.key);
            size += current.amount;
        }
        return current;
    }

    /**
     * Method used to count number of smaller elements than given key.
     * Time Compexity is logN. N - size of the tree.
     * @param key a key ot compare to.
     * @return number of smaller elements.
     */
    public int countSmallerNumbers(K key)
    {
        return key != null ? countSmallerNumbers((AVLNode) root, key) : 0;
    }

    /**
     * Private recursive method used by previous method to go through the tree.
     * and count number of smaller elements.
     * @param current start node.
     * @param key a key to compare to.
     * @return number of smaller elements.
     */
    private Integer countSmallerNumbers(AVLNode current, K key)
    {
        int counter = 0;
        //not found
        if(current == null)
            return 0;

        //if key is more than key in current node, go to right
        if(current.key.compareTo(key) < 0)
        {
            //if we go right, then all keys of nodes in left subtree are smaller than key
            //including current node
            counter += size(current.getLeft()) + 1;
            counter += countSmallerNumbers(current.getRight(),key);
        }
        //if key is less than key in current node, go to left
        else if(current.key.compareTo(key) > 0)
            counter += countSmallerNumbers(current.getLeft(),key);
        //finally, if found, all nodes in left child are smaller than key
        else
            counter += size(current.getLeft());

        return counter;
    }

    /**
     * Method used to balance the tree if needed.
     * It implements trinode restructuring.
     * @param current node, the subtree of which it should balance.
     * @return new root (current node) of the subtree.
     */
    private AVLNode balance(AVLNode current)
    {
        //subtree is unbalanced through the left side
        if(height(current.getLeft()) - height(current.getRight()) == 2)
        {
            //use single rotation
            if(height(current.getLeft().getLeft()) >= height(current.getLeft().getRight()))
                current = rightRotation(current);
            //use double rotation
            else
                current = leftRightRotation(current);
        }
        //subtree is unbalanced through the right side
        else if(height(current.getRight()) - height(current.getLeft()) == 2)
        {
            //use single rotation
            if(height(current.getRight().getRight()) >= height(current.getRight().getLeft()))
                current = leftRotation(current);
            //use double rotation
            else
                current = rightLeftRotation(current);
        }

        return current;
    }

    /**
     * Method used to balance subtree rotating it to the left side.
     * @param nodeA root-node of the subtree.
     * @return new root-node of the subtree.
     */
    private AVLNode leftRotation(AVLNode nodeA)
    {
        //new root-node
        AVLNode nodeB = nodeA.getRight();

        //exchange parent of root to the other node
        nodeB.parent = nodeA.parent;

        //give root-node's child to old root-node
        nodeA.right = nodeB.getLeft();
        if(nodeA.getRight() != null)
            nodeA.getRight().parent = nodeA;

        //link old and new root-nodes
        nodeB.left = nodeA;
        nodeA.parent = nodeB;

        //change child reference of root-node's parent
        if(nodeB.parent != null)
        {
            if(nodeB.parent.getLeft() == nodeA)
                nodeB.parent.left = nodeB;
            else
                nodeB.parent.right = nodeB;
        }

        //update features of nodes
        nodeA.size = size(nodeA.getLeft()) + size(nodeA.getRight()) + 1;
        nodeB.size = size(nodeB.getLeft()) + size(nodeB.getRight()) + 1;
        nodeA.height = Math.max(height(nodeA.getLeft()), height(nodeA.getRight())) + nodeA.amount;
        nodeB.height = Math.max(height(nodeB.getLeft()), height(nodeB.getRight())) + nodeB.amount;

        return nodeB;
    }

    /**
     * Method used to balance subtree rotating it to the right side.
     * @param nodeA root-node of the subtree.
     * @return new root-node of the subtree.
     */
    private AVLNode rightRotation(AVLNode nodeA)
    {
        //new root-node
        AVLNode nodeB = nodeA.getLeft();

        //exchange parent of root to the other node
        nodeB.parent = nodeA.parent;

        //give root-node's child to old root-node
        nodeA.left = nodeB.getRight();
        if(nodeA.getLeft() != null)
            nodeA.getLeft().parent = nodeA;

        //link old and new root-nodes
        nodeB.right = nodeA;
        nodeA.parent = nodeB;

        //change child reference of root-node's parent
        if(nodeB.parent != null)
        {
            if(nodeB.parent.getLeft() == nodeA)
                nodeB.parent.left = nodeB;
            else
                nodeB.parent.right = nodeB;
        }

        //update features of nodes
        nodeA.size = size(nodeA.getLeft()) + size(nodeA.getRight()) + 1;
        nodeB.size = size(nodeB.getLeft()) + size(nodeB.getRight()) + 1;
        nodeA.height = Math.max(height(nodeA.getLeft()), height(nodeA.getRight())) + nodeA.amount;
        nodeB.height = Math.max(height(nodeB.getLeft()), height(nodeB.getRight())) + nodeB.amount;

        return nodeB;
    }

    /**
     * Method used to balance the subtree rotating its left child to the left side,
     * then rotating the root-node itself to the right side.
     * @param nodeA root-node of the subtree.
     * @return new root-node of the subtree.
     */
    private AVLNode leftRightRotation(AVLNode nodeA)
    {
        nodeA.left = leftRotation(nodeA.getLeft());
        return rightRotation(nodeA);
    }

    /**
     * Method used to balance the subtree rotating its right child to the right side,
     * then rotating the root-node itself to the left side.
     * @param nodeA root-node of the subtree.
     * @return new root-node of the subtree.
     */
    private AVLNode rightLeftRotation(AVLNode nodeA)
    {
        nodeA.right = rightRotation(nodeA.getRight());
        return leftRotation(nodeA);
    }

    /**
     * @param current node.
     * @return max height of a node starting from leaves. -1 if null.
     */
    private int height(AVLNode current)
    {
        return current != null ? current.height : -1;
    }

    /**
     * @param current node.
     * @return size of a subtree with the root-node in current.
     */
    private int size(AVLNode current)
    {
        return current != null ? current.size : 0;
    }
}