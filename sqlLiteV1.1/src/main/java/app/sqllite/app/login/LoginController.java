package app.sqllite.app.login;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmm.EasyStageUtil;
import com.cmm.utility.impl.DefaultLoginVo;

import app.sqllite.app.login.service.LoginService;
import app.sqllite.app.login.service.impl.LoginServiceImpl;
import app.sqllite.database.msssql.service.impl.MsSQLVO;
import app.sqllite.database.mysql.service.impl.MySQLVO;
import app.sqllite.database.oracle.service.impl.OracleVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
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
import javafx.stage.Window;
import lite.sql.frontdoor.loginPage.loginList.LoginUser;
/**
 * 
 * 로그인화면 콘트롤러 구현 클래스  
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
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    private LoginService loginService = new LoginServiceImpl();
    private Window stage = null;
//    private Object stage = null;
    public LoginController(Stage stage) {
        this.stage = stage;
        //stage.setOnCloseRequest(e -> model.close());
    }
    public LoginController(Dialog stage) {
        this.stage = stage.getOwner();
        //stage.setOnCloseRequest(e->stage.close());
    }
    
    /** VBOX : 메인(로그인화면) 의 출력할 parents 다. */ 
	@FXML private VBox loginPane;
	/** 라밸 : 하단에 메시지(요류,성공) 표시 목적 으로 사용한다. */
	@FXML private Label logLblLogin;
	/** 텍스트 : SID/DBNAME 등 DB 특성에 따라 입력해야하는 추가정보의 종류를 표시할 목적으로 사용한다. */
	@FXML private Text logsid;
	/** 버튼 : 메인(로그인 화면) 종료 */
	@FXML private Button logBtnClose;
	/** 버튼 : 접속테스트 */
	@FXML private Button logBtnTest;
	/** 버튼 : 접속시도 */
    @FXML private Button logBtnCnt;
    /** 버튼 : 빠른 로그인 의 사용자정보 신규생성 */
    @FXML private Button logBtnNew;
    /** 버튼 : 빠른 로그인 의 사용자정보 수정 */
    @FXML private Button logBtnMod;
    /** 버튼 : 빠른 로그인 의 사용자정보 삭제 */
    @FXML private Button logBtnDel;
	/** 테이블 : 빠른로그인 정보 출력하는 곳. 추가/수정/삭제 시 즉시 반영된다. 화면애는 특정 컬럼만 표시한다. */
	@FXML private TableView<DefaultLoginVo> logTableView;
	@FXML private TableColumn<DefaultLoginVo, String> logColId;
	@FXML private TableColumn<DefaultLoginVo, String> logColIp;
	@FXML private TableColumn<DefaultLoginVo, String> logColSdi;
//사용자 정보 입력폼
	@FXML private TextField logTxtId;
	@FXML private PasswordField logTxtPw;
	@FXML private TextField logTxtIp;
	@FXML private TextField logTxtPort;
	/** 텍스트필드 : SID/DBNAME 등 DB 특성에 따라 입력해야하는 추가정보의 종류를 표시할 목적으로 사용한다. */
	@FXML private TextField logTxtSdi;
	@FXML private ComboBox dbType;
	String name[] = { "Oracle", "MySql" , "MSSql" };
/** 메인(로그인화면)에서 동작할 키 이벤트로 접속시도를 구현한 메소드 
 * - 실행방법은 1.빠른사용자에서 더블클릭/2.키보드 엔터키/3.F1키/4.(접속)버튼 
 */
	@FXML
	void handleButtonAction(Event event) {
		connect(event);
	}
/** 이니셜라이즈 : 초기화 */
	public void initialize() {
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
		logBtnCnt.setOnAction(event -> { 
		    if(nullPoint()){connect(event);} 
		});
		logBtnClose.setOnAction(event -> {
			close(logBtnClose);
		});
		logBtnTest.setOnAction(event -> {
		    if(nullPoint())conntest();
		});
		logBtnDel.setOnAction(event -> {
			delete();
		});
		logBtnMod.setOnAction(event -> {
		    if(nullPoint()){modify();}
		});
        logBtnNew.setOnAction(event -> {
            if(nullPoint()){save();}
        });
		dbType.setItems(FXCollections.observableArrayList(name.clone()));
		dbType.setOnAction(event -> {iniDBType(dbType.getValue().toString());});
		initTableView();
	}
    //---------------- 기능 ------------------------------
	/**
	 * 접속시도
	 * @throws IOException 
	 */
	private void connect(Event event) {
          String result = "";
          DefaultLoginVo connVO = getDefaultLoginVO();
          connVO.setUserId("admin");
          connVO.setUserPw("1234");
          connVO.setUsrType("admin");
          result = loginService.connection(connVO);
        if (result.contains("[접속성공]")) {
           try {
               EasyStageUtil esu = new EasyStageUtil();
               esu.easyWorkStage();
               logLblLogin.setText(result);
//              logTxtId.clear();
//              logTxtPw.clear();
//              logTxtIp.clear();
//              logTxtPort.clear();
//              logTxtSdi.clear();
            } catch (IOException e) {
                e.printStackTrace();
                result="[접속성공][화면실패]"+e.getMessage();
                logLblLogin.setText(result);
            }
        } else {
          logLblLogin.setText(result);
        }
	}
	/**
	 * 접속테스트
	 */
	private void conntest() {
	    DefaultLoginVo conntVO = getDefaultLoginVO();
		String result = loginService.connectTest(conntVO);
		logLblLogin.setText(result);
	}
//	------------- 빠른 사용자 기능------------------------
	/** 빠른 사용자 정보 중 선택한 정보  한 건 수정 */
	private void modify() {
	    DefaultLoginVo selectVO = getSelectVO();
	    if(selectVO==null){
	        logLblLogin.setText("[경고]수정할 항목을 선택하세요.");
	    }else{
	        DefaultLoginVo modifyVO = getDefaultLoginVO();
	        modifyVO.setRowNum(selectVO.getRowNum());
	        modifyVO.setDbEnum(selectVO.getDbEnum());
	        String result = loginService.modifyUser(modifyVO);
	        if(result.contains("[수정성공]")){//성공
	            logLblLogin.setText(result);
	        }else {
	            logLblLogin.setText(result);
	        };
	        initTableView();
	    }
	}
	/** 빠른 사용자 정보 중 선택한 정보 한 건 삭제 */
	private void delete() {
	    DefaultLoginVo selectVO = getSelectVO();
        if(selectVO==null){
            logLblLogin.setText("[경고]수정할 항목을 선택하세요.");
        }else{
            DefaultLoginVo deleteVO = getSelectVO();
            deleteVO.setRowNum(selectVO.getRowNum());
            deleteVO.setDbEnum(selectVO.getDbEnum());
            String result = loginService.deleteUser(deleteVO);
            if(result.contains("[삭제성공]")){//성공
                logLblLogin.setText(result);
            }else {
                logLblLogin.setText(result);
            };
        }
//            initTextFeild();//초기화:입력폼
        initTableView();//초기화:빠른사용자정보
	    
	    
	}
	/** 입력폼의 사용자정보를 빠른사용자 정보에 저장한다.
	 * -- XML FILE CRAETIING 후 VIEW TABLE INIT 실행한다.
	 */
	private void save() {
	    String result = loginService.saveUser(getDefaultLoginVO());
	    if(result.contains("[저장성공]")){//성공
	        logLblLogin.setText("[저장성공]");
	    }else {
	        logLblLogin.setText(result);
	    };
	    initTableView();
	}

//---------------- 초기화 -------------------------------------
	/**
	 * 빠른 로그인 사용자 정보폼에 초기화
	 * --조회된 데이터 tableView 에 넣기
	 */
	private void initTableView() {
//		1.컬럼 이름 생성
		logColId.setCellValueFactory(new PropertyValueFactory<DefaultLoginVo, String>("accessId"));
		logColIp.setCellValueFactory(new PropertyValueFactory<DefaultLoginVo, String>("accessIp"));
		logColSdi.setCellValueFactory(new PropertyValueFactory<DefaultLoginVo, String>("accessPort"));
//		2.
		List<DefaultLoginVo> list = loginService.readUserALL();
		logTableView.setItems(FXCollections.observableArrayList(list));
		logTableView.getSelectionModel().select(-1);
//		3.1.listView 이벤트 리스너 등록
		logTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DefaultLoginVo>() {
            public void changed(ObservableValue<? extends DefaultLoginVo> observable, DefaultLoginVo oldValue, DefaultLoginVo newValue) {
            	if (newValue != null) {
            	    initTextFeild(newValue);
            	}
            }
        });
//		3.2.listView 이벤트 리스너 등록 :: 더블 클릭 시 접속하도록 하는 이벤드
        logTableView.addEventFilter(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() {
        	@Override
    		public void handle(MouseEvent mouseEvent) {
    			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
    				if (mouseEvent.getClickCount() == 2) {
    					handleButtonAction(mouseEvent);
    				}
    			}
    		}
    	});
    }

//--------------- 화면기능  -----------------------
	/**
	 * 메인(로그인화면) 종료
	 * @param logBtnClose2
	 */
    private void close(Button logBtnClose2) {
        Stage stage = (Stage) logBtnClose2.getScene().getWindow();
        stage.close();
    }
	/** 사용자 정보 및 DB정보 입력폼 초기화 */ 
    private void initTextFeild() {
        DefaultLoginVo usr = new DefaultLoginVo();
        usr.setAccessId("");
        usr.setAccessPw("");
        usr.setAccessIp("");
        usr.setAccessPort("");
        usr.setAccessSID("");
        usr.setDbType("Oracle");    
        initTextFeild(usr);
    }
   /** 사용자 정보 및 DB정보 입력폼 초기화  
    * @param loginUser
    */
	private void initTextFeild(DefaultLoginVo loginUser) {
		logTxtId.setText(loginUser.getAccessId());
		logTxtPw.setText(loginUser.getAccessPw());
		logTxtIp.setText(loginUser.getAccessIp());
		logTxtPort.setText(loginUser.getAccessPort());
		dbType.setValue(loginUser.getDbType());
		
		String dbtype = loginUser.getDbType();
        if (dbtype.toUpperCase().contains("ORACLE")) {
            logsid.setText("SID");
            logTxtSdi.setText(loginUser.getAccessSID());
        }else if (dbtype.toUpperCase().contains("MYSQL")) {
            logsid.setText("DataBase");
            logTxtSdi.setText(loginUser.getAccessDBName());
        }else if(dbtype.toUpperCase().contains("MSSQL")){
            logsid.setText("DataBase");
            logTxtSdi.setText(loginUser.getAccessDBName());
        }
	}
    /**
     * DB 종류 선택폼 초기화
     * @param dbtype
     */
	private void iniDBType(String dbtype) {
		if (dbtype.toUpperCase().contains("ORACLE")) {
			logsid.setText("SID");
		}else if (dbtype.toUpperCase().contains("MYSQL")) {
			logsid.setText("DataBase");
		}else if(dbtype.toUpperCase().contains("MSSQL")){
		    logsid.setText("DataBase");
		}
	}
	/**
	 * 정보입력
	 * --화면의 사용자 정보 입력폼의 정보를 객체화한다.
	 * @return
	 */
	private DefaultLoginVo getDefaultLoginVO(){
	    DefaultLoginVo user = new DefaultLoginVo();
	    user.setAccessId(logTxtId.getText());
	    user.setAccessPw(logTxtPw.getText());
	    user.setAccessIp(logTxtIp.getText());
	    user.setAccessPort(logTxtPort.getText());
	    user.setAccessSID(logTxtSdi.getText());
	    user.setAccessDBName(logTxtSdi.getText());
	    user.setDbType(dbType.getValue().toString());
	    return user;
	}
	/**
	 * 정보입력
	 * --화면에서 빠른 사용자테이블의 선택된 해당 정보를 반환한다.
	 * @return
	 */
	private DefaultLoginVo getSelectVO(){
	    return logTableView.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * 
	 * @param dbType DB종류
	 * @param accessId DB 접속 아이디
	 * @param pwd DB 접속 비밀번호
	 * @param ip  
	 * @param port 
	 * @param sid oracle 서비스 ID
	 * @return DB VIEW Object 
	 */
	private Object setObjectVo(String dbType, String accessId, String pwd,String ip,String port,String sid){
	    if(dbType.toUpperCase().equals("ORACLE")){
	        OracleVO oraVO = new OracleVO();
	        oraVO.setAccessId(accessId);
	        oraVO.setAccessPw(pwd);
	        return oraVO;
	    }else if(dbType.toUpperCase().equals("MYSQL")){
	        MySQLVO mysql = new MySQLVO();
	        mysql.setAccessId(accessId);
	        mysql.setAccessPw(pwd);
            return mysql;
	    }else if(dbType.toUpperCase().equals("MSSQL")){
	        MsSQLVO mssql = new MsSQLVO();
	        mssql.setAccessId(accessId);
	        mssql.setAccessPw(pwd);
            return mssql;
	    }
	    return null; 
	}
	
	/** 
	 * 입력값 정규검사
	 * @return boolean
	 */
	private boolean nullPoint() {
	      boolean key = true;
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
	      }
	      return key;
     }
//	-------[사용안함...]이전버전에서 사용했던 메소드 -------------------
    /**
     * ?????????????????????????????????
     * 접속 :: 1.0버전에서 관리자 로그인 시 사용했던 메서드 
     * @param url
     * @param dbType
     */
    private void setLoginUser(String url, String dbType){
        LoginUser user = new LoginUser();
        user.setId(logTxtId.getText());
        user.setPw(logTxtPw.getText());
        user.setSid(logTxtSdi.getText());
        user.setIp(logTxtIp.getText());
        user.setPort(logTxtPort.getText());
        user.setUrl(url);
        user.setDataType(dbType);
//      Connect.user = user;
    }
    
    /**
     * ?????????????????????????????????
     * 접속 :: 1.0버전에서 관리자 로그인 시 사용했던 메서드 
     * @param url
     * @param dbType
     */
    private void setAdminUser(String url, String dbType){
        LoginUser user = new LoginUser();
        user.setId(logTxtId.getText());
        user.setPw(logTxtPw.getText());
        user.setSid(logTxtSdi.getText());
        user.setIp(logTxtIp.getText());
        user.setPort(logTxtPort.getText());
        user.setUrl(url);
        user.setDataType(dbType);
//      Connect.adminCheck = user;
    }

}