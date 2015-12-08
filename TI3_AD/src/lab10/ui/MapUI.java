package lab10.ui;

import java.util.Iterator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lab10.IP;
import lab10.Map;

public class MapUI
{
    private Stage stage_;
    private Map<IP, String> map_;
    private TextArea text_;
    
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
    
    @SuppressWarnings("unchecked")
    private void initStage()
    {
        VBox pane = new VBox();
        ComboBox<IP> ips = new ComboBox<>();
        text_ = new TextArea();
        
        ips.setPromptText("IP Adresses");
        ips.setVisibleRowCount(10);
        ips.getItems().addAll(map_.keySet());
        ips.setOnAction(e -> selectIP(((ComboBox<IP>) e.getSource()).getSelectionModel().getSelectedItem()));
        
        text_.setEditable(false);

        StackPane.setAlignment(ips, Pos.TOP_CENTER);
        StackPane.setAlignment(text_, Pos.CENTER);
        
        pane.getChildren().addAll(ips, text_);
        
        stage_.setTitle("Log Viewer");
        stage_.setScene(new Scene(pane));
        stage_.centerOnScreen();
    }
    
    private void selectIP(IP ip)
    {
        text_.setText(join(map_.get(ip), "\n"));
    }
    
    private String join(Iterable<String> a, String j)
    {
        StringBuilder sb = new StringBuilder();
        
        for(Iterator<String> i = a.iterator() ; i.hasNext() ;)
        {
            String s = i.next();
            sb.append(s);
            if(i.hasNext()) sb.append(j);
        }
        
        return sb.toString();
    }
}
