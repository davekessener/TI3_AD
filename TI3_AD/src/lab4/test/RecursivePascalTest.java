package lab4.test;

import lab4.Pascal;
import lab4.RecursivePascal;

public class RecursivePascalTest extends PascalTest
{
    @Override
    protected Pascal getImplementation()
    {
        return new RecursivePascal();
    }
}
