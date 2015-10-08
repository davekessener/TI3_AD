package lab1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Daniel Kessener
 * 
 * implementation des listeninterface mit einem
 * linearen array.
 * keine antizipative indizierung.
 *
 * @param <T>
 */
public final class LinearArrayList<T> extends AbstractList<T>
{
    private Object[] buf_; // Lineares array das den listeninhalt haelt
    private int l_; // tatsaechliche listenlaenge

    public LinearArrayList()
    {
        clear();
    }
    
    @Override
    public Pos begin()
    {
        return LAPos.valueOf(0);
    }
    
    @Override
    public Pos end()
    {
        return LAPos.valueOf(l_);
    }

    @Override
    public void insert(Pos pos, T t)
    {
        assert pos != null : "Precondition violated: p != null";
        assert pos instanceof LAPos : "Precondition violated: p instanceof LAPos";
        
        int p = ((LAPos) pos).idx;
        
        assert p >= 0 && p <= l_ : "Precondition violated: p >= 0 && p <= i_";
        assert t != null : "Precondition violated: t != null";

        resize(); // expandiere array wenn noetig

        // verschiebe elemente [p,i_) ein element nach hinten
        // (standard memmove impl)
        for(int i = l_ ; i > p ; --i)
        {
            buf_[i] = buf_[i - 1];
            
            addCounter(1); // One assignment
        }

        // fuege element t an position p ein
        buf_[p] = t;
        addCounter(1); // assignment
        ++l_;
    }

    @Override
    public void delete(Pos pos)
    {
        assert pos != null : "Precondition violated: p != null";
        assert pos instanceof LAPos : "Precondition violated: p instanceof LAPos";
        
        int p = ((LAPos) pos).idx;
        
        assert p >= 0 && p < l_ : "Precondition violated: p >= 0 && p <= i_";

        for(int i = p ; i < l_ - 1 ; ++i)
        {
            buf_[i] = buf_[i + 1];
            addCounter(1); // Assignment
        }

        buf_[l_ - 1] = null; // um gc nicht zu behindern
        addCounter(1); // Assignment
        --l_;

        resize();
    }

    @Override
    public Pos find(T t)
    {
        assert t != null : "Precondition violated: t != null";

        // einfache lineare suche
        for(int i = 0 ; i < l_ ; ++i)
        {
            addCounter(1); // comparison
            if(buf_[i].equals(t))
                return LAPos.valueOf(i);
        }

        return null;
    }

    // SuppressWarnings ist notwendig wegen Type Erasure
    @SuppressWarnings("unchecked")
    @Override
    public T retrieve(Pos pos)
    {
        assert pos != null : "Precondition violated: p != null";
        assert pos instanceof LAPos : "Precondition violated: p instanceof LAPos";
        
        int p = ((LAPos) pos).idx;
        
        assert p >= 0 && p < l_ : "Precondition violated: p >= 0 && p <= i_";

        return (T) buf_[p];
    }

    @Override
    public void concat(List<T> list)
    {
        // nutzen des interface List zur garantierten komformitaet
        // mit anderen implementationen (performanceverlust ist in kauf zu nehmen)
        for(Pos b = list.begin(), e = list.end() ; !b.equals(e) ; b = b.next())
        {
            insert(end(), list.retrieve(b));
        }
    }

    @Override
    public int size( )
    {
        return l_;
    }

    @Override
    public void clear()
    {
        buf_ = new Object[0];
        l_ = 0;
        resetCounter();
    }

    private void resize()
    {
        // wenn der interne buffer voll ist vergroessern ...
        if(l_ == buf_.length)
        {
            buf_ = Arrays.copyOf(buf_, buf_.length * 2 + 1);
        }
        // ... und wenn er fast leer ist, verkleinern.
        else if(l_ < buf_.length / 4)
        {
            buf_ = Arrays.copyOf(buf_, buf_.length / 2);
        }
    }
    
    private static class LAPos implements Pos
    {
        private final int idx;
        
        private LAPos(int idx)
        {
            this.idx = idx;
        }

        @Override
        public Pos next()
        {
            return valueOf(idx + 1);
        }
        
        // factory function garantiert das wenn p1.idx == p2.idx,
        // dann auch p1 == p2
        // erspart das schreiben von equals(Object)
        private static Pos valueOf(int i)
        {
            if(!lookup.containsKey(i))
            {
                lookup.put(i, new LAPos(i));
            }
            
            return lookup.get(i);
        }
        
        private static final Map<Integer, Pos> lookup = new HashMap<>();
    }
}
