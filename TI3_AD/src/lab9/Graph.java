package lab9;

public interface Graph<T> extends Iterable<T>
{
    public abstract void insert(T t);
    public abstract void remove(T t);
    public abstract boolean contains(T t);
    public abstract int size();
    public abstract Iterable<T> getNeighbors(T t);
    public abstract Iterable<T> getInNeighbors(T t);
    public abstract Iterable<T> getOutNeighbors(T t);
    public abstract void connect(T t1, T t2, double w);
    public abstract void disconnect(T t1, T t2);
    public abstract boolean areConnected(T t1, T t2);
    public abstract double getWeight(T t1, T t2);
}
