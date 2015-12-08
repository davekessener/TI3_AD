package lab10;

import java.util.HashMap;

public class IP
{
    private int ip_;
    
    private IP(int ip)
    {
        ip_ = ip;
    }
    
    @Override
    public int hashCode()
    {
        return ip_;
    }
    
    @Override
    public String toString()
    {
        return String.format("%d.%d.%d.%d", (ip_ >> 24) & 0xff, (ip_ >> 16) & 0xff, (ip_ >> 8) & 0xff, ip_ & 0xff);
    }
    
    public static IP valueOf(int ip)
    {
        if(!lookup.containsKey(ip))
        {
            lookup.put(ip, new IP(ip));
        }
        
        return lookup.get(ip);
    }
    
    private static final java.util.Map<Integer, IP> lookup = new HashMap<>();
}
