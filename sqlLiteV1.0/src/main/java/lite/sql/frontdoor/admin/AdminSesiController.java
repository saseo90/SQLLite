package lite.sql.frontdoor.admin;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.OracleDaoImpl;
import lite.sql.backdoor.dao.QueryResultVo;
import lite.sql.backdoor.session.SessionThread;
import lite.sql.frontdoor.checkbox.CheckboxList;
import lite.sql.frontdoor.loginPage.loginList.LoginUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminSesiController implements Initializable {

	@FXML
	private TableView<LoginUser> tabSesi;
	@FXML
	private TableColumn<LoginUser, String> colSesiSid;
	@FXML
	private TableColumn<LoginUser, CheckBox> colSesiChek;
	@FXML
	private TableColumn<LoginUser, String> colSesiNum;
	@FXML
	private TableColumn<LoginUser, String> colSesiIp;
	@FXML
	private TableColumn<LoginUser, String> colMachine;
	@FXML
	private TableColumn<LoginUser, String> colProgram;

	@FXML
	private TableColumn<LoginUser, String> colCloseSid;
	@FXML
	private TableColumn<LoginUser, String> colCloseIp;
	@FXML
	private TableColumn<LoginUser, String> colCloseNum;
	@FXML
	private TableColumn<LoginUser, String> colCloseProgram;
	@FXML
	private TableColumn<LoginUser, String> colCloseMachine;

	@FXML
	private Button btnCloseSesi;

	@FXML
	private TableView<LoginUser> tabCloseSesi;
    @FXML
    private TextArea queryArea;


	List<Boolean> check = new ArrayList<Boolean>();
	List<LoginUser> checklist = new ArrayList<LoginUser>();

	DatabaseDao dao = new OracleDaoImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Scene scene = btnCloseSesi.getScene();
		// Window window = scene.getWindow();

		SessionThread.currentStageController = this;

		SessionThread sessionThread = new SessionThread();
		sessionThread.setDaemon(true);
		sessionThread.start();

		btnCloseSesi.setOnAction(event -> {
			sessionKill();
		});

	}

	private void sessionKill() {
		StringBuilder message = new StringBuilder();
		QueryResultVo result = new QueryResultVo();
		int fail = 0;
		for (int i = 0; i < checklist.size(); i++) {
			String sid = checklist.get(i).getSid();
			String sn = checklist.get(i).getSn();
			StringBuilder querys = new StringBuilder(
					"ALTER SYSTEM KILL SESSION");
			querys.append(" '" + sid + "," + sn + "'");
			String query = new String(querys);
			System.out.println(query);
			result = dao.checkQuery(query);

			if (result.getAlter() != 0) {
				message.append("IP : " + checklist.get(i).getIp()
						+ " - MACHINE : " + checklist.get(i).getMachine()
						+ "\n");
			} else {
				fail++;
			}
		}

		if (message.length() == 0) {
			message = new StringBuilder("선택된 사용자가 없습니다.");
		} else {
			message.append("위 사용자의 접속을 해제하였습니다." + "\n");
		}

		if (fail != 0) {
			message.append(fail + " 명의 접속해제에 실패하였습니다.");
		}

		String resultMessage = new String(message);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("확인");
		alert.setContentText(resultMessage);
		alert.showAndWait().get();

		try {
			if (result.getStatement() != null)
				result.getStatement().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			checklist = new ArrayList<LoginUser>();
			setCloseTable();
			setTable(dao.getSession());
		}
	}

	private void setCloseTable() {

		tabCloseSesi.getItems().clear();

		tabCloseSesi.setItems(FXCollections.observableArrayList(checklist));

		colCloseSid
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"sid"));
		colCloseNum
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"sn"));
		colCloseIp
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"ip"));
		colCloseMachine
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"machine"));
		colCloseProgram
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"program"));

	}

	//
	public void setTable(List<List<String>> userList) {

		tabSesi.getItems().clear();

		List<List<String>> request = userList;
		List<String> list = null;

		List<LoginUser> result = new ArrayList<LoginUser>();
		LoginUser userRecord;
		for (int i = 0; i < request.size(); i++) {
			list = request.get(i);
			userRecord = new LoginUser();
			userRecord.setSid(list.get(0));
			userRecord.setSn(list.get(1));
			userRecord.setIp(list.get(2));
			userRecord.setMachine(list.get(3));
			userRecord.setProgram(list.get(4));
			// CheckBox cb = new CheckBox();
			CheckBox cb = new CheckBox();
			cb.setId(userRecord.getSn() + "&" + userRecord.getSid() + "&"
					+ userRecord.getIp() + "&" + userRecord.getMachine() + "&"
					+ userRecord.getProgram());
			cb.selectedProperty().addListener(
					event -> {
						if (cb.isSelected()) {
							String[] total = cb.getId().split("&");
							String sn = total[0];
							String sid = total[1];
							String ip = total[2];
							String machine = total[3];
							String program = total[4];
							for (int j = 0; j < checklist.size(); j++) {
								if (checklist.get(j).getSn().equals(sn)
										&& checklist.get(j).getSid()
												.equals(sid)) {
									checklist.remove(j);
									setCloseTable();
									return;
								}
							}
							LoginUser closeUser = new LoginUser();
							closeUser.setSid(sid);
							closeUser.setSn(sn);
							closeUser.setIp(ip);
							closeUser.setMachine(machine);
							closeUser.setProgram(program);

							checklist.add(closeUser);
							System.out.println(checklist.toString());
							setCloseTable();
						} else {
							String[] total = cb.getId().split("&");
							String sn = total[0];
							String sid = total[1];
							for (int j = 0; j < checklist.size(); j++) {
								if (checklist.get(j).getSn().equals(sn)
										&& checklist.get(j).getSid()
												.equals(sid)) {
									checklist.remove(j);
									setCloseTable();
									return;
								}
							}

						}
					});
			userRecord.setCheck(cb);
			result.add(userRecord);
		}

		colSesiSid
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"sid"));
		colSesiNum
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"sn"));
		colSesiIp
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"ip"));
		colMachine
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"machine"));
		colProgram
				.setCellValueFactory(new PropertyValueFactory<LoginUser, String>(
						"program"));
		colSesiChek
				.setCellValueFactory(new PropertyValueFactory<LoginUser, CheckBox>(
						"check"));

		tabSesi.setItems(FXCollections.observableArrayList(result));
		// list.get().setCheck()

	}
	
	public void setQueryArea(List<List<String>> list){
		queryArea.setText("");
		List<List<String>> request = list;
		List<String> setList = null;

		for (int i = 0; i < request.size(); i++) {
			setList = request.get(i);
			queryArea.appendText(setList.get(1)+" : "+setList.get(0)+"\n");
		}
		
	}

}