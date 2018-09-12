/**
 * This is the interface of B-Tree structure.
 * @param <K> this is the type of the data stored in a tree.
 */
public interface IBTree<K extends Comparable> {
    K find(K key);
    void insert(K key);
    Iterable traverse();
    Iterable print() throws Exception;
    int size();
    boolean isEmpty();
}