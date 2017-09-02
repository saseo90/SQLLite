package lite.sql.frontdoor.checkbox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.frontdoor.main.MainPanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import lite.sql.utility.poi.inputexcel.PoiOut;
import lite.sql.utility.text.FileTxtOut;
import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.OracleDaoImpl;

public class CheckboxController implements Initializable {

	private static DatabaseDao dao = Connect.dao;

	@FXML
	private Button btnText;

	@FXML
	private Button btnExcel;

	@FXML
	private Button btnNonono;
	CheckboxList recode;
	@FXML
	private TableColumn<CheckboxList, Boolean> checkBox;

	@FXML
	private TableView<CheckboxList> table;

	@FXML
	private TableColumn<CheckboxList, String> tableName;
	List<Boolean> check = new ArrayList<Boolean>();
	List<CheckBox> checklist = new ArrayList<CheckBox>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnText.setOnAction(event -> {
			saveText();
		});

		btnExcel.setOnAction(event -> {
			saveExcel();
		});

		btnNonono.setOnAction(event -> {
			nonono(event);
		});

		tableSet();

	}

	@FXML
	void select(ActionEvent event) {
		ObservableList<TablePosition> cells = table.getSelectionModel()
				.getSelectedCells();
		// System.out.println(cells);
	}

	private void saveText() {
	      Alert alert = new Alert(AlertType.INFORMATION);
	      String title = null;
	      String path = null;
	      FileChooser fileChooser = new FileChooser();
	      FileChooser fc = new FileChooser();

	      FileChooser.ExtensionFilter extfilter = new ExtensionFilter(
	            "TXT (UTF-8) 문서", "*.TXT");
	      FileChooser.ExtensionFilter extfilter2 = new ExtensionFilter(
	            "SQL파일", "*.SQL");
	      fc.getExtensionFilters().add(extfilter);
	      fc.getExtensionFilters().add(extfilter2);
	      File file = fc.showSaveDialog(new Stage());
	      try {
	         title = file.getName();
	         path = file.getParent();
	         // System.out.println(title);
	         // System.out.println(path);
	         PoiOut poi = new PoiOut();
	         List<String> selectTable = new ArrayList<String>();
	         for (int i = 0; i < checklist.size(); i++) {
	            System.out.println(checklist.get(i).getId());
	            selectTable.add(checklist.get(i).getId());
	         }
	         FileTxtOut txtout = new FileTxtOut();
	         txtout.filetxt(selectTable, path, title);
	         alert.setTitle("Information Dialog");
	         alert.setHeaderText(null);
	         alert.setContentText("저장되었다 해");

	         alert.showAndWait();
	      } catch (Exception e) {
	         alert.setTitle("Information Dialog");
	         alert.setHeaderText(null);
	         alert.setContentText("취소 되었다 해");

	         alert.showAndWait();
	      }
	   }// saveText

	   /** 엑셀로 저장하기 */
	   private void saveExcel() {
	      Alert alert = new Alert(AlertType.INFORMATION);
	      
	      String title = null;
	      String path = null;
	      // List<CheckBox> list = checkBox;
	      System.out.println();

	      FileChooser fc = new FileChooser();

	      FileChooser.ExtensionFilter extfilter = new ExtensionFilter(
	            "Excel 통합 문서(*.xls)", "*.xls");
	      fc.getExtensionFilters().add(extfilter);
	      File file = fc.showSaveDialog(new Stage());
	      try {
	         title = file.getName();
	         path = file.getParent();
	         // System.out.println(title);
	         // System.out.println(path);
	         PoiOut poi = new PoiOut();
	         List<String> selectTable = new ArrayList<String>();
	         for (int i = 0; i < checklist.size(); i++) {
	            System.out.println(checklist.get(i).getId());
	            selectTable.add(checklist.get(i).getId());
	         }
	         alert.setTitle("Information Dialog");
	         alert.setHeaderText(null);
	         alert.setContentText("저장완료");
	         alert.showAndWait();
	         poi.poiOutput(selectTable, path, title);
	         
	      } catch (Exception e) {
	         alert.setTitle("Information Dialog");
	         alert.setHeaderText(null);
	         alert.setContentText("취소 되었다 해");

	         alert.showAndWait();
	      }
	   }// saveExcel

	/** 창 닫기 */
	private void nonono(Event event) {
		// System.out.println("종료");
		// 프롬트창 띄워서 종료 여부 묻고 true 면 종료하기
		if (true) {
			Parent home_page_parent;
			Scene home_page_scene;
			try {
				home_page_parent = FXMLLoader.load(getClass().getResource(
						"/frontdoor/main/Main.fxml"));
				home_page_scene = new Scene(home_page_parent);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
			app_stage.hide(); // optional

		}

	}

	void tableSet() {

		table.getItems().clear();
		tableName
				.setCellValueFactory(new PropertyValueFactory<CheckboxList, String>(
						"tableName"));
		checkBox.setCellValueFactory(new PropertyValueFactory<CheckboxList, Boolean>(
				"check"));
		List<String> tableList = dao.tableList();
		List<CheckboxList> list = new ArrayList<CheckboxList>();
		// System.out.println(tableList.size());
		for (int i = 0; i < tableList.size(); i++) {
			check.add(new Boolean(false));
			recode = new CheckboxList();
			CheckBox cb = new CheckBox();
			cb.setId(tableList.get(i));
			cb.selectedProperty().addListener(event -> {
				if (cb.isSelected()) {
					checklist.add(cb);
				} else {
					for (int j = 0; j < checklist.size(); j++) {
						if (checklist.get(j).equals(cb))
							checklist.remove(j);
					}

				}
			});
			recode.setCheck(cb);
			recode.setTableName(tableList.get(i));
			list.add(recode);
			// System.out.println(recode.toString());
		}

		table.setItems(FXCollections.observableArrayList(list));
		// list.get().setCheck()
	}
}// class