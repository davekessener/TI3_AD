package lab1;

import java.util.Arrays;

import measurement.AbstractMeasurable;

/**
 * 
 * @author Daniel Kessener
 * 
 * implementierung des list interfaces mit
 * einer doubly linked list in einem array.
 * nutzt antizipative indizierung.
 *
 * @param <T>
 */
public final class LinkedArrayList<T> extends AbstractMeasurable implements List<T>
{
    private static final int DUMMY = 0;
    
    private Node[] buf_;
    private int l_; // tatsaechliche listenlaenge
    
    public LinkedArrayList()
    {
        clear();
    }

    @Override
    public Pos begin()
    {
        return buf_[DUMMY];
    }

    @Override
    public Pos end()
    {
        return buf_[buf_[DUMMY].prev];
    }

    @Override
    public void insert(Pos pos, T t)
    {
        assert pos != null : "Precondition violated: pos != null";
        assert t != null : "Precondition violated: t != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        
        int i = findEmpty();
        Node p = (Node) pos;
        
        buf_[i] = new Node(this, t, buf_[p.next].prev, p.next);
        addCounter(1); //assignment
        buf_[p.next].prev = i;
        p.next = i;
        
        ++l_;
    }

    @Override
    public void delete(Pos pos)
    {
        assert pos != null : "Precondition violated: pos != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        assert l_ > 0 : "Precondition violated: l_ > 0 [List not empty]";
        
        int i = ((Node) pos).next;
        
        assert buf_[i] != null : "Precondition violated: buf_[i] != null [Targets valid element]";
        assert i > 0 : "Precondition violated: i > 0 [Not dummy]";
        
        Node n = buf_[i];
        
        buf_[n.next].prev = n.prev;
        buf_[n.prev].next = n.next;
        buf_[i] = null;
        addCounter(1); //indirekte zerstoerung von buf_[i].elem
        
        --l_;
    }

    @Override
    public Pos find(T t)
    {
        assert t != null : "Precondition violated: t != null";
        
        int i = DUMMY;
        
        buf_[DUMMY].elem = t; // initialisiere stopper
        addCounter(1);
        
        addCounter(1); //erster vergleich
        while(!buf_[buf_[i].next].elem.equals(t))
        {
            addCounter(1); //weitere vergleiche
            i = buf_[i].next;
        }

        buf_[DUMMY].elem = null; // cleanup
        addCounter(1);
        
        return buf_[i].next == DUMMY ? null : buf_[i];
    }

    // SuppressWarnings ist notwendig wegen Type Erasure
    @SuppressWarnings("unchecked")
    @Override
    public T retrieve(Pos pos)
    {
        assert pos != null : "Precondition violated: pos != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        assert l_ > 0 : "Precondition violated: l_ > 0 [List not empty]";
        
        int i = ((Node) pos).next;
        
        assert buf_[i] != null : "Precondition violated: buf_[i] != null [Targets valid element]";
        assert i > 0 : "Precondition violated: i > 0 [Not dummy]";
        
        return (T) buf_[i].elem;
    }

    @Override
    public void concat(List<T> l)
    {
        // nutzen des interface List zur garantierten komformitaet
        // mit anderen implementationen (performanceverlust ist in kauf zu nehmen)
        for(Pos b = l.begin(), e = l.end() ; !b.equals(e) ; b = b.next())
        {
            insert(end(), l.retrieve(b));
        }
    }

    @Override
    public int size()
    {
        return l_;
    }
    
    @Override
    public void clear()
    {
        buf_ = new Node[1];
        buf_[DUMMY] = new Node(this, null, DUMMY, DUMMY);
        l_ = 0;
        resetCounter();
    }
    
    private int findEmpty()
    {
        // wenn der buffer voll ist: capacitaet verdoppeln
        if(l_ == buf_.length - 1)
        {
            buf_ = Arrays.copyOf(buf_, buf_.length * 2);
            
//            Node[] t = new Node[buf_.length * 2];
//            int l = buf_.length;
//
//            for(int i = 0 ; i < l ; ++i)
//            {
//                t[i] = buf_[i];
//            }
//
//            buf_ = t;

            return l_ + 1; // dummy element nicht vergessen
        }
        // ansonsten muss noch ein platz frei sein:
        // linear suchen
        else for(int i = 0 ; i < buf_.length ; ++i)
        {
            if(buf_[i] == null)
            {
                return i;
            }
        }
        
        // sollte niemals hier ankommen, wenn doch gibt es einen
        // fehler beim ueberwachen der tatsaechlichen listenlaenge l_
        throw new IllegalStateException("Couldn't find an empty spot despite being not full!");
    }

    private static class Node implements Pos
    {
        // braucht reference zu LinkedArrayList fuer next().
        // klasse muss aber static sein wegen parameterisierung von LAL.
        private LinkedArrayList<?> list;
        private Object elem;
        private int next, prev;
        
        private Node(LinkedArrayList<?> l)
        {
            list = l;
            elem = null;
            next = prev = 0;
        }
        
        private Node(LinkedArrayList<?> list, Object elem, int prev, int next)
        {
            this.list = list;
            this.elem = elem;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public Pos next()
        {
            return list.buf_[next];
        }
    }
}
