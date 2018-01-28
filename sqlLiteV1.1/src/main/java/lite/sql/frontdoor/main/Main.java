package lite.sql.frontdoor.main;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
//    	Scene scene = new Scene(Parent root);
//       FxUtil.loadFxml(,primaryStage ); 
       Parent root = FXMLLoader.load(getClass().getResource("/lite/sql/frontdoor/main/Main.fxml"));
       Scene scene = new Scene(root);
       
       primaryStage.setTitle("작업영역");//윈도우창의 제목설정
       primaryStage.setScene(scene);//윈도우 창에 장면설정
       primaryStage.show();//윈도우 창 보여주기
    }
    
}//class
