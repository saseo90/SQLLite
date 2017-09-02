package lite.sql.frontdoor.main;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class MainController implements Initializable{

    @FXML
    private VBox pan;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lite/sql/frontdoor/main/MainPane.fxml"));
        Parent parent = null;        
        try {
            parent = loader.load();
        } catch (Exception e) {
            System.out.println("로그");
        	System.out.println(e.getMessage());
        }
//         load 화면정보를 workTop에 append 한다.
        if(pan.getChildren() != null){
//         이전에 자식을 가지고 있는경우 제거한다.
            pan.getChildren().clear();
        }
//        workTop 에 자식을 추가한다.
        pan.getChildren().addAll(parent);
        
    }

}
