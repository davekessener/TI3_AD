package lab10.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lab10.IP;
import lab10.Map;

public class MapViewer extends Application
{
    private MapUI ui_;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Map<IP, String> map = null;
        
        ui_ = new MapUI(stage, map);
        
        registerHandlers();
        
        ui_.show();
        
        stage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
    
    private void registerHandlers()
    {
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
