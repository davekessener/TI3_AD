package lab9.test;

import lab9.Graph;
import lab9.MapGraph;

public class MapGraphTest extends GraphTest
{
    @Override
    protected Graph<String> getImplementation()
    {
        return new MapGraph<String>();
    }
}
