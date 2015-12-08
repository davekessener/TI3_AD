package lab10;

import java.util.Date;
import java.util.Random;

public class LogGenerator
{
    private Random rand_;
    private Date date_;
    private IP oldIP_;
    
    public LogGenerator(long seed)
    {
        rand_ = new Random(seed);
        date_ = new Date(EPOCH);
    }
    
    public void fillMap(Map<IP, String> map, int c)
    {
        while(c-- > 0)
        {
            IP ip = getNextIP();
            
            map.put(ip, generate(ip));
        }
    }
    
    private IP getNextIP()
    {
        if(oldIP_ == null || rand_.nextBoolean())
        {
            oldIP_ = IP.valueOf(rand_.nextInt());
        }
        
        return oldIP_;
    }
    
    private String generate(IP addr)
    {
        String ip = addr.toString();
        String date = getNextDate();
        String request = getNextRequest();
        String answer = getNextAnswer();
        
        return String.format("%s  - -  [%s]  \"%s\"  %s", ip, date, request, answer);
    }
    
    private String getNextDate()
    {
        date_ = new Date(date_.getTime() + ONE_HOUR + (rand_.nextLong() % ONE_HOUR));
        
        return date_.toString();
    }
    
    private String getNextRequest()
    {
        return "GET / HTTP/1.0";
    }
    
    private String getNextAnswer()
    {
        return "200";
    }
    
    private static final long EPOCH = 1420100000000L;
    private static final long ONE_HOUR = 60L * 60L * 1000L;
}
