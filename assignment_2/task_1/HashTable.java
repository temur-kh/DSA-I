import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class HashTable is Data Type for storing pairs (key,value),
 * getting access and quick searching/adding/removing.
 * @param <K> parameter key
 * @param <V> parameter value
 */
public class HashTable<K, V> {

    //Private constants
    private double MAX_LOAD_FACTOR = 0.5;
    private double MIN_LOAD_FACTOR = 0.1;
    private int DEFAULT_CAPACITY = 11;

    //Constants for detecting status of cell
    private int EMPTY = 0;
    private int ACTIVE = 1;
    private int DELETED = -1;

    //Containers for storing data
    private int[] status;
    private Entry<K, V>[] table;

    //variables representing parameters of HashTable
    private int capacity;
    private int maxProbingSequenceLength;
    private int size;

    //Constructor
    public HashTable()
    {
        table = createTable(DEFAULT_CAPACITY);
        maxProbingSequenceLength = 0;
    }

    /**
     * Creates a table, that's creates new container, updates variables and status
     * @param capacity size of new table
     * @return array of Entries (container of data)
     */
    private Entry<K, V>[] createTable(int capacity)
    {
        this.capacity = capacity;
        size = 0;

        Entry<K, V>[] entries = (Entry<K, V>[]) new Entry[capacity];
        status = new int[capacity];

        for(int i = 0; i != entries.length; i++)
        {
            entries[i] = new Entry<>();
            status[i] = EMPTY;
        }

        return entries;
    }

    /**
     * Calculates current load factor of table
     * @return real number in range [0,1]
     */
    private double currentLoadFactor()
    {
        return (double) size / capacity;
    }

    /**
     * Finds prime number next to the given number for initializing new capacity
     * @param capacity given number
     * @return integer (prime number bigger or equal to capacity)
     */
    private int nextCapacity(int capacity)
    {
        while(true)
        {
            boolean prime = true;
            for(int i = 2; i <= Math.sqrt(capacity); i++)
            {
                if(capacity % i == 0)
                {
                    prime = false;
                    break;
                }
            }
            if(prime) return capacity;
            capacity++;
        }
    }

    /**
     * Hash function based on Object.hashcode() function
     * @param key object to find the hashcode of
     * @return integer number in range [0,capacity)
     */
    private int indexOf(K key)
    {
        int hash = key.hashCode();
        return Math.abs(hash) % capacity;
    }

    /**
     * Find new cell in table for a key, or finds cell that already contains key.
     * In order to handle collisions we use quadratic probing.
     * @param key key to find cell for
     * @return position - intener number in range [0,capacity)
     */
    private int findPosition(K key)
    {
        int index = indexOf(key);

        //save the position of first cell in status DELETED
        int deletedPos = Integer.MIN_VALUE;
        boolean deletedPosChosen = false;

        //make probing until empty position or the key is found
        int probing = 0;
        while(status[index] != EMPTY && !table[index].key.equals(key))
        {
            //save first visited cell in status DELETED
            if(!deletedPosChosen && status[index] == DELETED)
            {
                deletedPos = index;
                deletedPosChosen = true;
            }
            probing++;

            //go to next position using quadratic probing
            index = (index + (int) Math.pow(probing,2)) % capacity;
        }

        //save the length of maximal probing sequence
        maxProbingSequenceLength = Math.max(maxProbingSequenceLength, probing);

        //if the key already exists return its position
        //else if there's deleted position return it
        //otherwise return last index after probing
        if(table[index].key==null || !table[index].key.equals(key))
            return deletedPos != Integer.MIN_VALUE ? deletedPos : index;
        else
            return index;
    }

    /**
     * @param key key to find value of
     * @return value of key if key is in table, otherwise returns null
     */
    public V get(K key)
    {
        int index = findPosition(key);
        return status[index] == ACTIVE ? table[index].value : null;
    }

    /**
     * @param key key of new entry to be added in table
     * @param value value of new entry to be added in table
     * @return old value of key if key was in table, otherwise returns null
     */
    public V set(K key, V value)
    {
        //resize table if it is overloaded
        if(currentLoadFactor() > MAX_LOAD_FACTOR)
            resize(nextCapacity(2 * capacity));

        //put entry
        int index = findPosition(key);
        if(status[index] == ACTIVE)
        {
            V output = table[index].value;
            table[index].value = value;
            return output;
        }
        else
        {
            table[index].key = key;
            table[index].value = value;
            status[index] = ACTIVE;
            size++;
            return null;
        }
    }

    /**
     * @param key key of entry to be removed from table
     * @return value of key and deletes entry it if key was in table, otherwise returns null
     */
    public V remove(K key)
    {
        //resize table if it it underloaded
        if(currentLoadFactor() < MIN_LOAD_FACTOR && capacity / 2 >= DEFAULT_CAPACITY)
            resize(nextCapacity(capacity / 2));

        //delete entry
        int index = findPosition(key);
        if(status[index] == ACTIVE)
        {
            status[index] = DELETED;
            size--;
            return table[index].value;
        }
        else
        {
            return null;
        }
    }

    /**
     * @param key key to be searched in table
     * @return true if table contains key, false otherwise
     */
    public boolean contains(K key)
    {
        int index = findPosition(key);
        return status[index] == ACTIVE;

    }

    /**
     * Used to resize table when it is underloaded or overloaded
     * @param newCapacity new capacity for table
     */
    public void resize(int newCapacity)
    {
        HashSet<Entry<K, V>> set = (HashSet<Entry<K, V>>) entrySet();
        table = createTable(newCapacity);

        for(Entry<K, V> item: set)
        {
            set(item.key, item.value);
        }
    }

    /**
     * @return number of entries in the table
     */
    public int size()
    {
        return size;
    }

    /**
     * @return capacity of the table
     */
    public int capacity()
    {
        return capacity;
    }

    /**
     * @return true if the table is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * @return all entries in the table as set
     */
    public Set<Entry<K, V>> entrySet()
    {
        HashSet<Entry<K, V>> set = new HashSet<>();
        for(int i = 0; i != table.length; i++)
        {
            if(status[i] == ACTIVE)
            {
                set.add(table[i]);
            }
        }
        return set;
    }

    /**
     * @return all keys in the table as set
     */
    public Set<K> keySet()
    {
        HashSet<K> set = new HashSet<>();
        for(int i = 0; i != table.length; i++)
        {
            if(status[i] == ACTIVE)
            {
                set.add(table[i].key);
            }
        }
        return set;
    }

    /**
     * @return all values in the table as list
     */
    public ArrayList<V> values()
    {
        ArrayList<V> list = new ArrayList<>();
        for(int i = 0; i != table.length; i++)
        {
            if(status[i] == ACTIVE)
            {
                list.add(table[i].value);
            }
        }
        return list;
    }

    /**
     * @return maximal probing sequence for quadratic probing
     */
    public int getMaxProbingSequenceLength() {
        return maxProbingSequenceLength;
    }
}