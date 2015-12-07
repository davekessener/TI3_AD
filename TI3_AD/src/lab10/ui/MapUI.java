package lab10.ui;

import javafx.stage.Stage;
import lab10.IP;
import lab10.Map;

public class MapUI
{
    private Stage stage_;
    private Map<IP, String> map_;
    
    public MapUI(Stage stage, Map<IP, String> m)
    {
        stage_ = stage;
        map_ = m;
        
        initStage();
    }
    
    public void show()
    {
        stage_.show();
    }
    
    private void initStage()
    {
    }
}
