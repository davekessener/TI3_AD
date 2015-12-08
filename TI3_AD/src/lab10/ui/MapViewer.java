package lab10.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import lab10.HashMap;
import lab10.IP;
import lab10.LogGenerator;
import lab10.Map;

public class MapViewer extends Application
{
    private MapUI ui_;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        ui_ = new MapUI(stage, generateMap());
        
        registerHandlers();
        
        ui_.show();
    }
    
    private void registerHandlers()
    {
    }
    
    private static Map<IP, String> generateMap()
    {
        Map<IP, String> map = new HashMap<IP, String>();
        
        (new LogGenerator(SEED)).fillMap(map, 10_000);
        
        return map;
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    private static final long SEED = 20151206L;
}
