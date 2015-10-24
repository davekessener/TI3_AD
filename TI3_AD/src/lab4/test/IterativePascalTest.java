package lab4.test;

import lab4.IterativePascal;
import lab4.Pascal;

public class IterativePascalTest extends PascalTest
{
    @Override
    protected Pascal getImplementation()
    {
        return new IterativePascal();
    }
}
