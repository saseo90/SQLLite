package app.sqllite.app.Workflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    private MainController buildController(Stage stage) {
        return new MainController(stage);
    }
    @Override
    public void start(Stage stage) throws Exception {
//    	Scene scene = new Scene(Parent root);
//       FxUtil.loadFxml(,primaryStage ); 
//       Parent root = FXMLLoader.load(getClass().getResource("/lite/sql/frontdoor/main/Main.fxml"));
//       Scene scene = new Scene(root);
        
//       FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/sqllite/app/Workflow/Main.fxml"));
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/sqllite/app/Workflow/Main.fxml"));
       loader.setControllerFactory(t -> buildController(stage));
       stage.setTitle("작업영역");//윈도우창의 제목설정\
       stage.setScene(new Scene((Parent) loader.load()));
       stage.show();//윈도우 창 보여주기
    }
    
}
