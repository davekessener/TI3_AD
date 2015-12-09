package lab11.test;

import static org.junit.Assert.assertEquals;
import lab11.Crypt;

import org.junit.Test;

public abstract class CryptTest
{
    protected abstract Crypt getImplementation();
    
    @Test
    public void testEncryption()
    {
        Crypt c = getImplementation();
        String s = "123Example!";
        
        assertEquals(s, c.decrypt(c.encrypt(s)));
    }
}
