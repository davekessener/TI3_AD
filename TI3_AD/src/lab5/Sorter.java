package lab5;

import java.util.Comparator;

public abstract class Sorter
{
    public <T extends Comparable<T>> void sort(T[] a) { sort(a, (o1, o2) -> o1.compareTo(o2)); }
    public abstract <T> void sort(T[] a, Comparator<? super T> c);
}
