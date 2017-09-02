package lite.sql.frontdoor.loginPage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.MysqlDaoImpl;
import lite.sql.backdoor.dao.OracleDaoImpl;
import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.frontdoor.loginPage.loginList.LoginList;
import lite.sql.frontdoor.loginPage.loginList.LoginUser;

public class LoginController implements Initializable {
	@FXML
	private TextField logTxtSdi;
	@FXML
	private Button logBtnCnt;
	@FXML
	private VBox loginPane;
	@FXML
	private Text logsid;
	@FXML
	private TextField logTxtId;

	@FXML
	private Button logBtnDel;

	@FXML
	private TableColumn<LoginUser, String> logColId;

	@FXML
	private Button logBtnTest;

	@FXML
	private Button logBtnNew;

	@FXML
	private Label logLblLogin;

	@FXML
	private Button logBtnMod;

	@FXML
	private Button logBtnClose;

	@FXML
	private TableColumn<LoginUser, String> logColIp;

	@FXML
	private PasswordField logTxtPw;
	@FXML
	private TableColumn<LoginUser, String> logColSdi;

	@FXML
	private TableView<LoginUser> logTableView;

	@FXML
	private TextField logTxtIp;
	@FXML
	private TextField logTxtPort;
	@FXML
	private ComboBox dbType;
	String name[] = { "oracle", "MySql" };
	LoginUser lu = new LoginUser();
	private List<LoginUser> list = LoginList.userList();
	private LoginList ll = new LoginList();

	// ////////////////////////
	@FXML
	void handleButtonAction(Event event) {
		try {if(!nullPoint()){
			return;
		}
			System.out.println("?");
			String url;
			DatabaseDao ddao = null;
			if (dbType.getValue().toString().toUpperCase()
					.equals(("oracle").toUpperCase())) {
				url = "jdbc:oracle:thin:@" + logTxtIp.getText() + ":"
						+ logTxtPort.getText() + "/" + logTxtSdi.getText();
				System.out.println("?");
				ddao = new OracleDaoImpl();
				Connect.dao  =ddao;
			} else {
				url = "jdbc:mysql://" + logTxtIp.getText() + ":"
						+ logTxtPort.getText() + "/" + logTxtSdi.getText();
				ddao = new MysqlDaoImpl();
				Connect.dao  =ddao;
			}
			setAdminUser(url, dbType.getValue().toString());
			System.out.println(url);
			String result = ddao.connection(url, logTxtId.getText(),
					logTxtPw.getText());
			if (result.contains("success")) {
				setLoginUser(url, dbType.getValue().toString());
				Parent home_page_parent = FXMLLoader.load(getClass()
						.getResource("/lite/sql/frontdoor/main/Main.fxml"));
				System.out.println("??");
				
				System.out.println(Connect.user.toString());
				Scene home_page_scene = new Scene(home_page_parent);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
						.getWindow();
				logLblLogin.setText("1");
				app_stage.hide(); // optional
				app_stage.setScene(home_page_scene);
				app_stage.show();

				logTxtId.clear();
				logTxtPw.clear();
				logTxtIp.clear();
				logTxtPort.clear();
				logTxtSdi.clear();
				logLblLogin.setText("접속실패");
			} else {

				logLblLogin.setText(result);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginPane.setOnKeyPressed(event -> {

			switch (event.getCode()) {
			case F1:
				handleButtonAction(event);
				break;
			default:
				break;
			}

		});

		logTableView.setOnKeyPressed(event -> {

			switch (event.getCode()) {
			case ENTER:
				handleButtonAction(event);
				break;
			default:
				break;
			}
		});

		logBtnNew.setOnAction(event -> {if(nullPoint())
			addconnect();
		});
		/*
		 * logBtnCnt.setOnAction(event -> { connnet(); });
		 */
		logBtnClose.setOnAction(event -> {
			close(logBtnClose);
		});
		logBtnTest.setOnAction(event -> {if(nullPoint())
			conntest();
		});
		logBtnDel.setOnAction(event -> {
			delete();
		});
		logBtnMod.setOnAction(event -> {if(nullPoint())
			modify();
		});
		dbType.setItems(FXCollections.observableArrayList(name.clone()));
		dbType.setOnAction(event -> {
			sidType(dbType.getValue().toString());
		});
		initTableView();
	}

	private void close(Button logBtnClose2) {
		Stage stage = (Stage) logBtnClose2.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}

	private void modify() {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(lu.toString())) {
				list.set(i,
						new LoginUser(logTxtIp.getText(), logTxtId.getText(),
								logTxtPw.getText(), logTxtSdi.getText(),
								logTxtPort.getText(), dbType.getValue()
										.toString()));

			}

		}

		try {
			ll.makeXml(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTableView();

	}

	private void delete() {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().equals(lu.toString())) {
				list.remove(i);
			}
		}

		try {
			ll.makeXml(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTableView();
	}

	private void addconnect() {

		System.out.println(logTxtIp.getText());
		System.out.println(logTxtId.getText());
		System.out.println(logTxtPw.getText());
		System.out.println(logTxtSdi.getText());
		System.out.println(logTxtPort.getText());

		list.add(new LoginUser(logTxtIp.getText(), logTxtId.getText(), logTxtPw
				.getText(), logTxtSdi.getText(), logTxtPort.getText(), dbType
				.getValue().toString()));
		try {
			ll.makeXml(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTableView();
	}

	private void conntest() {
		// TODO Auto-generated method stub
		DatabaseDao ddao = null;
		String url;

		if (dbType.getValue().toString().toUpperCase()
				.equals(("oracle").toUpperCase())) {
			url = "jdbc:oracle:thin:@" + logTxtIp.getText() + ":"
					+ logTxtPort.getText() + "/" + logTxtSdi.getText();
			System.out.println("?");
			ddao = new OracleDaoImpl();
		} else {
			url = "jdbc:mysql://" + logTxtIp.getText() + ":"
					+ logTxtPort.getText() + "/" + logTxtSdi.getText();
			ddao = new MysqlDaoImpl();
		}
		System.out.println(url);
		String result = ddao.connection(url, logTxtId.getText(),
				logTxtPw.getText());
		String resultSet;
		if (result.indexOf("오류") != -1)
			resultSet = result.substring(result.indexOf("오류"));
		else if (result.indexOf("ORA") != -1)
			resultSet = result.substring(result.indexOf("ORA"));
		else {
			resultSet = "접속 테스트 성공";
		}
		logLblLogin.setText(resultSet);
	}

	private void initTableView() {
		// TODO Auto-generated method stub
		try {
			list = LoginList.userList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list);
		// 조회된 데이터 tableView 에 넣기

		logColIp.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
				"ip"));
		logColId.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
				"id"));
		logColSdi
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"port"));

		logTableView.setItems(FXCollections.observableArrayList(list));
		logTableView.getSelectionModel().select(-1);
		// listView 이벤트 등록
		logTableView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<LoginUser>() {
					public void changed(
							// 토나오는 코드..
							ObservableValue<? extends LoginUser> observable,
							LoginUser oldValue, LoginUser newValue) {
						if (newValue != null) {
							lu = newValue;
							initTextFeild();
						}
					}
				});
		logTableView.addEventFilter(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					// 이거도 토나오네...
					public void handle(MouseEvent mouseEvent) {
						if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
							if (mouseEvent.getClickCount() == 2) {
								handleButtonAction(mouseEvent);
							}
						}
					}
				});

	}

	private void initTextFeild() {
		if (lu == null) {
			logTxtId.setText("");
			logTxtPw.setText("");
			logTxtSdi.setText("");
			logTxtIp.setText("");
			logTxtPort.setText("");
		} else {
			logTxtId.setText(lu.getId());
			logTxtPw.setText(lu.getPw());
			logTxtSdi.setText(lu.getSid());
			logTxtIp.setText(lu.getIp());
			logTxtPort.setText(lu.getPort());
		}
		dbType.setValue(lu.getDataType());
		sidType(dbType.getValue().toString());
	}

	private void sidType(String dbtype) {
		lu.setUrl(dbType.getValue().toString());
		System.out.println(lu.getUrl());
		if (dbtype.toUpperCase().contains("ORACLE")) {
			logsid.setText("SID");
		}
		if (dbtype.toUpperCase().contains("MYSQL")) {
			logsid.setText("DataBase");
		}
		// System.out.println(dbtype);
	}
	
	private void setLoginUser(String url, String dbType){
		LoginUser user = new LoginUser();
		user.setId(logTxtId.getText());
		user.setPw(logTxtPw.getText());
		user.setSid(logTxtSdi.getText());
		user.setIp(logTxtIp.getText());
		user.setPort(logTxtPort.getText());
		user.setUrl(url);
		user.setDataType(dbType);
		
		Connect.user = user;
	}
	
	private void setAdminUser(String url, String dbType){
		LoginUser user = new LoginUser();
		user.setId(logTxtId.getText());
		user.setPw(logTxtPw.getText());
		user.setSid(logTxtSdi.getText());
		user.setIp(logTxtIp.getText());
		user.setPort(logTxtPort.getText());
		user.setUrl(url);
		user.setDataType(dbType);
		
		Connect.adminCheck = user;
	}
	
	private boolean nullPoint() {
	      boolean key = false;
	      System.out.println(logTxtId.getText());
	      System.out.println(logTxtIp.getText());
	      System.out.println(logTxtSdi.getText());
	      System.out.println(logTxtPw.getText());
	      System.out.println(logTxtPort.getText());
	      System.out.println(dbType.getValue());
	      if (logTxtId.getText() == null||logTxtId.getText().trim().isEmpty()) {
	         logLblLogin.setText("ID를 입력하세요");
	         key = false;
	      } else if (logTxtPw.getText() == null||logTxtPw.getText().trim().isEmpty()) {
	         logLblLogin.setText("페스워드를 입력하세요");
	         key = false;
	      } else if (logTxtSdi.getText() == null||logTxtSdi.getText().trim().isEmpty()) {
	         logLblLogin.setText(logsid.getText() + "를 입력하세요");
	         key = false;
	      } else if (logTxtIp.getText() == null||logTxtIp.getText().trim().isEmpty()) {
	         logLblLogin.setText("아이피를 입력하세요");
	         key = false;
	      } else if (logTxtPort.getText() == null||logTxtPort.getText().trim().isEmpty()) {
	         logLblLogin.setText("포트번호를 입력하세요");
	         key = false;
	      } else if (dbType.getValue() == null||dbType.getValue() == "") {
	         logLblLogin.setText("데이터 타입을 입력하세요");
	         key = false;
	      } else {
	         key = true;
	      }
	      System.out.println(key);
	      return key;
	   }

}