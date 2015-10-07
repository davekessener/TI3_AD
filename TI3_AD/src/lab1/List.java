package lab1;

/**
 * 
 * @author Daniel Kessener
 * 
 * null ist kein akzeptables element.
 * 
 * doppelte elemente duerfen vorkommen,
 * alle suchoperation richten sich jedoch
 * immer nach dem ersten vorkommen des
 * gesuchten elementes.
 *
 * @param <T> Type der elemente die in der
 *              liste gespeichert werden.
 */
public interface List<T>
{
    /**
     * 
     * @return Position des ersten Elements der Liste.
     */
    public abstract Pos begin( );
    
    /**
     * 
     * @return Position direkt nach Ende der Liste.
     */
    public abstract Pos end( );

    /**
     * Leert die Liste.
     */
    public abstract void clear( );
    
    /**
     * 
     * @param p Position zum einfuegen
     * @param t Eingefuegtes element
     * 
     * @require p != null
     * @require t != null
     * @require begin() <= p <= end()
     * 
     * @ensure retrieve(p) == t
     */
    public abstract void insert(Pos p, T t);
    
    /**
     * 
     * @param p Position zum loeschen
     * 
     * @require p != null
     * @require begin() <= p < end()
     * @require size() > 0
     */
    public abstract void delete(Pos p);
    
    /**
     * 
     * @param t Element, dass gefunden werden soll
     * 
     * @require t != null
     * 
     * @ensure    retrieve(find(t)).equals(t)
     *          || find(t) == null
     * 
     * @return position des elementes <code>t</code>,
     *          falls mindestens ein wertegleiches
     *          element in der liste ist.
     * @return <code>null</code>
     *          falls kein zu <code>t</code> wertegleiches
     *          element in der liste ist.
     */
    public abstract Pos find(T t);
    
    /**
     * 
     * @param p Position des abzurufenden elementes
     * 
     * @require p != null
     * @require begin() <= p < end()
     * 
     * @ensure retrieve(p) instanceof T
     * 
     * @return element t an der position p
     *          in der liste
     */
    public abstract T retrieve(Pos p);
    
    /**
     * Haenge liste <code>l</code> an diese liste an
     * Liste <code>l</code> wird nicht modifiziert.
     * 
     * @param l Anzuhaengende Liste.
     * 
     * @require l != null
     * 
     * @ensure All elements in <code>l</code> are
     *          now also in <code>self</code>.
     */
    public abstract void concat(List<T> l);
    
    /**
     * 
     * @return Anzahl an Elementen in der Liste.
     */
    public abstract int size( );
    
    /**
     * 
     * @author Daniel Kessener
     * 
     * Interface zum Positionstypen.
     *
     */
    public static interface Pos
    {
        /**
         * Inkrement-Operation.
         * 
         * @return Naechste Position.
         */
        public abstract Pos next();
    }
}
