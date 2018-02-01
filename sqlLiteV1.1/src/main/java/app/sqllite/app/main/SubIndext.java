package app.sqllite.app.main;

import app.sqllite.app.Workflow.WorkflowController;
import javafx.stage.Stage;

public class SubIndext {

    public WorkflowController buildController(Stage stage) {
        return new WorkflowController(stage);
    }
}
