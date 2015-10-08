package lab1.measurement;

public class PrintFormat extends AbstractFormatter
{
    @Override
    public void onClass(Class<?> c)
    {
        add("\n\n%s\n-----------------------------\n", c.toString());
    }

    @Override
    public void onInvocation(String id)
    {
        add("Invocation %s\n", id);
    }

    @Override
    public void onMethod(String name, Long value)
    {
        add("\t%s : %s\n", name, value.toString());
    }
}
