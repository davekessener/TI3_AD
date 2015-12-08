package lab10.test;

import lab10.HashMap;
import lab10.IP;
import lab10.Map;

import org.junit.Test;

public class TestHashMap
{
    @Test
    public void doTest()
    {
        Map<IP, String> m = new HashMap<>();
        
        m.put(IP.valueOf(0), "address 0.0.0.0");
        m.put(IP.valueOf(0x7F000001), "address 127.0.0.1");
        m.put(IP.valueOf(0), "another null");
    }
}
