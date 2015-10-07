package lab1;

/**
 * 
 * @author Daniel Kessener
 * 
 * implementation des list interfaces mit einer
 * singly linked list auf dem heap.
 * nutzt antizipative indizierung.
 *
 * @param <T>
 */
public final class LinkedList<T> extends AbstractList<T>
{
    private Node head_, tail_;
    private int l_; // listenlaenge

    public LinkedList()
    {
        clear();
    }
    
    @Override
    public Pos begin()
    {
        return head_;
    }
    
    @Override
    public Pos end()
    {
        // durch antizipative idizierung muss der end()-
        // iterator auf das letzte tatsaechliche element
        // zeigen, anstatt direkt dahinter (tail_)
        // ohne prev_ pointer kann nicht von tail_ darauf
        // zugegriffen werden, eine direkte referenz
        // waere aber auch unschoen.
        // um eine lineare suche bei jeden end() aufruf
        // zu vermeiden wird der next pointer von tail_
        // genutzt um zu den element direkt vor tail zu
        // verweisen, anstatt auf null.
        // da suchoperationen mithilfe eines stopper-
        // elementes abgebrochen werden, ist dies kein risiko.
        return tail_.next;
    }

   @Override
    public void insert(Pos pos, T t)
    {
        assert t != null : "Precondition violated: t != null";
        assert pos != null : "Precondition violated: pos != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        
        Node p = (Node) pos;
        Node n = new Node(t, p.next);
        addCounter(1); // assignment: n.elem = t
        
        // stelle sicher, das tail_.next auf das letzte element
        // verweist. siehe end() fuer details
        if(p.next == tail_) tail_.next = n;
        
        p.next = n;

        ++l_;
    }

    @Override
    public void delete(Pos pos)
    {
        assert pos != null : "Precondition violated: pos != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        
        Node p = (Node) pos;
        
        assert p.next != tail_ : "Precondition violated: p.next != tail_";

        // stelle sicher, das tail_.next auf das letzte element
        // verweist. siehe end() fuer details
        if(p.next.next == tail_) tail_.next = p;

        p.next = p.next.next;
        
        addCounter(1); // fuer indirekte zerstoerung von p.next.elem

        --l_;
    }

    @Override
    public Pos find(T t)
    {
        assert t != null : "Precondition violated: t != null";

        Node n = head_;

        tail_.elem = t; // initialisiere stopper
        addCounter(1);
        
        addCounter(1); // fuer ersten vergleich
        while(!n.next.elem.equals(t))
        {
            addCounter(1); // fuer weitere vergleiche
            n = n.next;
        }

        tail_.elem = null; // cleanup
        addCounter(1);

        return n.next == tail_ ? null : n;
    }

    // SuppressWarnings ist notwendig wegen Type Erasure
    @SuppressWarnings("unchecked")
    @Override
    public T retrieve(Pos pos)
    {
        assert pos != null : "Precondition violated: pos != null";
        assert pos instanceof Node : "Precondition violated: pos instanceof Node";
        
        Node p = (Node) pos;
        
        assert p != tail_ : "Precondition violated: p != tail_";
        assert p.next != tail_ : "Precondition violated: p.next != tail_";

        return (T) p.next.elem;
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
        // cyclische dependency aufbrechen
        if(tail_ != null)
            tail_.next = null;
        
        tail_ = new Node();
        head_ = new Node(null, tail_);
        // tail_.next verweist auf das letzte element
        // siehe end() fuer details
        tail_.next = head_;
        l_ = 0;
        resetCounter();
    }
    
    private static class Node implements List.Pos
    {
        public Object elem;
        public Node next;

        private Node()
        {
            this.elem = null;
            this.next = null;
        }

        private Node(Object elem, Node next)
        {
            this.elem = elem;
            this.next = next;
        }

        @Override
        public Pos next()
        {
            return next;
        }
    }
}
