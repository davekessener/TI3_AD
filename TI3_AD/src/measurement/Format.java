package measurement;

public interface Format
{
    public abstract void onClass(Class<?> c);
    public abstract void onInvocation(String id);
    public abstract void onMethod(String name, Long value);
}
