package lab4.test;

import lab4.Pascal;
import lab4.QuickPascal;

public class QuickPascalTest extends PascalTest
{
    @Override
    protected Pascal getImplementation()
    {
        return new QuickPascal();
    }
}
