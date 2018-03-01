package app.sqllite.app.main;
import com.cmm.EasyStageUtil;

import app.sqllite.app.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * MAIN CLASS 
 * 애플리케이션 실행하는 클래스입니다. 
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
public class SqlLiteApp extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EasyStageUtil est = new EasyStageUtil();
        //est.easyLoginStage(stage);
        est.easyLoginStage(stage);
    }
}
