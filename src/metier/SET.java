package metier;

/*************************************************************************
 *  Compilation:  javac SET.java
 *  Execution:    java SET
 *  
 *  Symbol table implementation using Java's HashMap library.
 *  Does not allow duplicates.
 *
 *  % java sET
 *  128.112.136.11
 *  208.216.181.15
 *  null
 *
 *************************************************************************/

import java.util.TreeSet;
import java.util.Iterator;

public class SET<Key> implements Iterable<Key>
{
	
	@SuppressWarnings("unchecked")
	public SET( TreeSet<Key> st)
	{
		super();
		Iterator<Key> it = st.iterator();
		
		while(it.hasNext())
			this.st.add((Key)it.next());
	}
	public SET()
	{
		super();
	}
	
    private TreeSet<Key> st = new TreeSet<Key>();

    public void add(Key key)          { st.add(key);               }
    
    public void addSET(SET<Key> a)
    {
        for(Key key : a)
        {
            st.add(key);
        }
        /*for(int i = 0; i < a.size(); i++)
        {
            st.addAll((Collection<? extends Key>) a);
        }*/
    }
    
    public boolean contains(Key key)  { return st.contains(key);   }
    public void remove(Key key)       { st.remove(key);            }
    public int size()                 { return st.size();          }
    public Iterator<Key> iterator()   { return st.iterator();      }


    // return a set containing the union of the elements in this SET a and b
    public SET<Key> union(SET<Key> b) {
        SET<Key> a = this;
        SET<Key> c = new SET<Key>();
        for (Key x : a) c.add(x);
        for (Key x : b) c.add(x);
        return c;
    }

    // return a set containing the intersection of the elements in this SET a and b
    public SET<Key> intersects(SET<Key> b)
    {
        SET<Key> a = this;
        SET<Key> c = new SET<Key>();
        if (a.size() < b.size()) {
            for (Key x : a) {
                if (b.contains(x)) c.add(x);
            }
        }
        else {
            for (Key x : b) {
                if (a.contains(x)) c.add(x);
            }
        }
        return c;
    }
    
    public TreeSet<Key> getSet()
    {
    	return st;
    }

    // return a string representation of this set
    public String toString() {
        String s = "";
        for (Key key : st) {
            s += key + " ";
        }
        return s;
    }

}
