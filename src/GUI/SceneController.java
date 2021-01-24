package GUI;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneController extends Application{
    
    @Override
    public void start(Stage window) throws Exception {
        MainMenu mainMenuScene = new MainMenu();
        window.getIcons().add(new Image(SceneController.class.getResourceAsStream("./Media/arnocropped.png")));
        
        window.setScene(mainMenuScene.getScene(window));
        window.show();        
    }
        
    public static void main(String[] args) {
        launch(SceneController.class);
    }    
}
