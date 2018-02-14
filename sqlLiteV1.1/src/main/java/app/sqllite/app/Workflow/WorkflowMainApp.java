package app.sqllite.app.Workflow;

import com.cmm.EasyStageUtil;

import javafx.application.Application;
import javafx.stage.Stage;

public class WorkflowMainApp extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        EasyStageUtil est = new EasyStageUtil();
        est.easyWorkStage(stage);
        est.easyLoginStage(stage);
    }
    
}
