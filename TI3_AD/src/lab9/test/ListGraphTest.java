package lab9.test;

import lab9.Graph;
import lab9.ListGraph;

public class ListGraphTest extends GraphTest
{
    @Override
    protected Graph<String> getImplementation()
    {
        return new ListGraph<String>();
    }
}
