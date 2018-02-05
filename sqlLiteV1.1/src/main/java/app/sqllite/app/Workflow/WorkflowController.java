package app.sqllite.app.Workflow;

import app.sqllite.app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;


/**
 * Workflow Controller 
 * 작업영역 컨트롤러 클래스
 * 작업영역을 추후 변경가능하도록 하는 기능구현을 목적으로 사용한다.
 * @author Lee SEONG-HYUN
 * @since 2018.01.21
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일                   수정자          수정내용
 *  ----------  -------  ---------------------------
 *  2018.01.21    이성현         최초작성
 *  </pre>
 *  
 */ 
public class WorkflowController {
    public WorkflowController(Stage stage) {
        
    }
    @FXML private VBox pan;
    
    private WorkflowPanController buildController() {
        return new WorkflowPanController(null);
    }
    public void initialize() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/sqllite/app/Workflow/WorkflowPane.fxml"));
        loader.setControllerFactory(t -> buildController());
        Parent parent = null;        
        try {
            parent = (Parent) loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//          load 화면정보를 workTop에 append 한다.
            if(pan.getChildren()!=null){
//             이전에 자식을 가지고 있는경우 제거한다.
                pan.getChildren().clear();
            }
//            workTop 에 자식을 추가한다.
            if(parent!=null)
            pan.getChildren().addAll(parent);
        }
    }
}
