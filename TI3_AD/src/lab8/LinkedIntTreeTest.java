package lab8;

import java.util.Random;

import org.junit.Test;

public class LinkedIntTreeTest
{
    @Test
    public void doTest()
    {
        LinkedIntTree t = new LinkedIntTree();
        
        Random rand = new Random();
        
        for(int i = 0 ; i < 10 ; ++i)
        {
            System.out.println(rand.nextInt(100));
        }
    }
}
