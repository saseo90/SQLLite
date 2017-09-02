package lite.sql.frontdoor.loginPage;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.w3c.dom.Node;

import lite.sql.frontdoor.loginPage.loginList.LoginList;
import lite.sql.frontdoor.loginPage.loginList.LoginUser;

public class Test extends Application {



    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tree View Sample");        
        List<LoginUser> list = null;
		LoginList ll = new LoginList();
		list = ll.userList();
        TreeItem<String> rootItem = new TreeItem<String> ("Inbox");
        rootItem.setExpanded(true);
        for (int i = 1; i < list.size(); i++) {
            TreeItem<String> item = new TreeItem<String> (list.get(i).getId().toString());            
            rootItem.getChildren().add(item);
        }        
        TreeView<String> tree = new TreeView<String> (rootItem);        
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}