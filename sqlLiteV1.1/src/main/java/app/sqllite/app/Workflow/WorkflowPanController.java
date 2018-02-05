package app.sqllite.app.Workflow;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import app.sqllite.database.DefaultTableInfo;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lite.sql.backdoor.dao.DatabaseDao;

/**
 * [두번째 윈도우(Stage)_작업창] --이 클래스가 윈도우(stage) 에 올려지면 지체없이 장면(Scene)을 연결한다. - 상단,
 * 사이드, 컨텐드, 하단으로 구성됬다. 이 클래스는 로그인(접속)했을 떄 보여지는 윈도우다. workMain 의 내용은 Pane 이며
 * Pane 해당하는 장면(Scene)을 show 한다. workPane 의 내용은 BorderPane 이며 MainController에 의해
 * 장면에 올라간다. 이후 반응이 있을 경우 해당 Pane 에 장면 을 교체한다. 이 클래스의 VBOX 의 기본값은 (1440,900) 이고
 * MIN 과 같다. - 윈도우창을 최대화했을 때를 고려해 max 값을 지정하지 않았다. 레이아웃의 기본값은 다음과 같고 Max 와 같다. -
 * 향후 이벤트를 고려해 min 은 지정하지 않았다. - 전체 레이아웃은 가로 X 세로 (1440,900)(CENTER) - 상단 레이아웃은
 * 가로 X 세로 (1440,160)(BOTTOM_CENTER) - 사이드 및 컨텐트 가로 X 세로 (1440,540) - 사이드 가로 X
 * 세로 ( 220,540)(CENTER_RIGHT) - 컨텐드 가로 X 세로 (1220,540)(CENTER_LEFT)
 * MIN(1220,200) MAX(1220,540) - 하단 레이아웃은 가로 X 세로 (1440,200)(TOP_CENTER)
 * MIN(1440,200) MAX(1440,540)
 * 
 * 위의 레이아웃에는 기본 패딩 5,5,5,5 적용함.
 * 
 * --상단 레이아웃 --매뉴영역 가로 x 세로 (1430, 70)(CENTER) MIN(1430, 70) MAX(1430, 70)
 * --상태영역 가로 x 세로 (1430, 70)(CENTER) MIN(1430, 70) MAX(1430, 70) --분리자 X3 가로 X
 * 세로 (1430, 3)(separator) MIN(1430, 3) MAX(1430, 3)
 * 
 * --사이드 레이아웃 --작업영역 가로 X 세로 ( 205,530)(TOP_CENTER) MIN( 205,530) MAX( 205,530)
 * --분리자 X1 가로 X 세로 ( 3,530)(separator) MIN( 3,530) MAX( 3,530)
 * 
 * --컨텐트 레이아웃 --작업영역 가로 X 세로 (1210,530)(TAP_PANE) MIN(1210,190) MAX(1210,530)
 * (1200,500)(TEXTAREA) MIN(1200,160) MAX(1200,500) --하단 레이아웃 --작업영역 가로 x 세로
 * (1430,190)(TAP_PANE) MIN(1430,190) MAX(1430,480) 최대화 했을 때 여유를 20으로 정함.
 * (1420,160)(TEXTAREA) MIN(1420,160) MAX(1420, PRE)
 */
public class WorkflowPanController {
    public WorkflowPanController(Stage stage) {
    }
     // 디자인 - 컨테이너 영역
    /** 작업영역의 컨테이너 객체 */
     @FXML private BorderPane workPane;
     /** BorderPane 의 TOP 영역 */@FXML private VBox upVbox;
     /** BorderPane 의 LEFT 영역 */@FXML private HBox sideHbox;
     /** BorderPane 의 CENTER 영역 */@FXML private VBox contentVbox;
     /** BorderPane 의 Bottem 영역 */@FXML private VBox downVbox;
     // 작업영역
     // 상단1
     /** TOP 영역 의 버튼바 */
     @FXML private ToolBar upToolbarMenu;
     /** 버튼-새문서    */@FXML private Button btnNew;
     /** 버튼-문서열기 */@FXML private Button btnOpen;
     /** 버튼-문서저장 */@FXML private Button btnSave;
     /** 버튼-접속해제 */@FXML private Button btnNonAceess;
     /** 버튼-다른이름으로저장 */@FXML private Button btnSaveAs;
     /** 버튼-(팝업)세선관리 */@FXML private Button btnManageSesi;
     /** 버튼-(팝업)입포트    */@FXML private Button btnImport;
     /** 버튼-(팝업)도움말    */@FXML private Button btnHelp;

     // 상단2
     /** 접속자정보-접속아이디    */@FXML private TextField textfieldAccessUseId;
     /** 접속자정보-접속아이피    */@FXML private TextField textfieldAccessUseIp;
     /** 접속자정보-접속드라이버 */@FXML private TextField textfieldAccessDriver;

     // 사이드 영역 시작
     /** LEFT 영역의 버튼바 */@FXML private ToolBar sideToolbarMenu;
     /** 버튼-DB 기본정보 초기화 */@FXML private Button btnSelectTables;
     /** 트리뷰-(조회)DB 기본정보   */@FXML private StackPane paneStack;

     /** 트리뷰-(조회)테이블 기본정보 */@FXML private Label sideLabelTableName;
     @FXML private TableView<DefaultTableInfo> sideTableColumns;
     @FXML private TableColumn<DefaultTableInfo, String> sideColColumnName;
     @FXML private TableColumn<DefaultTableInfo, String> sideColDateType;
     @FXML private TableColumn<DefaultTableInfo, String> sideColNullable;
     @FXML private TableColumn<DefaultTableInfo, String> sideColDateDefault;
     @FXML private TableColumn<DefaultTableInfo, String> sideColColumnId;
     @FXML private TableColumn<DefaultTableInfo, String> sideColComments;
     // 사이드 컬럼추가 영역
     @FXML private Label sidLabelAppendCol;
     @FXML private TextField sidTextFieldColName;
     @FXML private ComboBox<String> sidComboDateType;
     @FXML private TextField sidTextFieldDefault;
     @FXML private Button btnAppendCol;

     // 컨텐트영역 시작
     // 컨텐트 paneUp 영역
     @FXML private TabPane contentTabpaneUp;
     @FXML private Tab tabBase;
     @FXML private Tab tabQuery;
     @FXML private TextArea textareaQuery;
     @FXML private Tab tabData;
     @FXML private TableView<Object> tableData;
     @FXML private Tab tabSession;
     @FXML private TextArea textareaSession;
     // 컨텐트 버튼바
     @FXML private ToolBar contentToolbarWork;
     @FXML private Button btnRun;

     @FXML private Button btnRunAll; // 전체스크립트 실행

     @FXML private Button btnRepairData;
     @FXML private Button btnSaveAccel;
     @FXML private Button btnSaveSql;
     @FXML private Button btnLordAccel;
     @FXML private Button btnLordSql;
     @FXML private Button btnOpeneRepairContentsheet;

     // 컨텐트 paneDouwn 영역 시작
     @FXML private TabPane contentTabpaneDown;
     @FXML private Tab tabReportSelect;
     @FXML private TableView<Object> tableReportSelect;
     @FXML private Tab tabReportErrorScript;
     @FXML private TextArea textareaReportErrorScript;
     @FXML private Tab tabRepairData;
     @FXML private AnchorPane scrollerRepairData;
     @FXML private FlowPane flowerRepairData;

     // 하단 - 메시지
     @FXML private Label downLabelMessage;

     // bacgroundPane content영역 높이조절
     @FXML private Button btnCloseRepairContentsheet;
     
     /** BorderPane 의 Right 영역 */@FXML private Pane panBacgraound;
     @FXML private ScrollBar scrollContentsheet;
     @FXML private CheckBox chekBaseContentsheet;
     @FXML private Button btnTexCel;

     // 가로조절
     @FXML private VBox LeftVbox;
     @FXML private ScrollBar scrollWidth;

     // 쿼리문 자동완성 실행버튼
     @FXML private Button btnSelTxt;
     @FXML private Button btnUpdateTxt;
     @FXML private Button btnInsTxt;
     @FXML private Button btnDelTxt;
     @FXML private Button btnAltTxt;
     @FXML private Button btnCreTxt;
     @FXML private Button btnDropTxt;
     @FXML private Button btnEpilogue;

     // 전역변수
     private String selectTable;
     private ObservableList<Object> data;
     private static DatabaseDao dao = null;
     private static File selectedSqlFile = null;

     @FXML
     public void handleButtonAction(Event event) {
          try {
               Parent home_page_parent = FXMLLoader.load(getClass().getResource("/app/sqllite/app/login/login.fxml"));
               Scene home_page_scene = new Scene(home_page_parent);
               Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//               Connect.user = null;
//               Connect.connection = null;
               app_stage.hide(); // optional
               app_stage.setScene(home_page_scene);
               app_stage.show();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }// handleButtonAction(Event event)

     public void initialize() {
//     @Override
//     public void initialize(URL location, ResourceBundle resources) {
//          tabRepairDataSetting();
//          dao = Connect.dao;
//          textfieldAccessUseId.setText(Connect.user.getId());
//          textfieldAccessUseIp.setText(Connect.user.getIp());
//          textfieldAccessDriver.setText(Connect.user.getDataType());
//
//          // 마우스 hover 하면 메서드에 대한 설명있습니다.
//          treeView();
//          setRepairContentsheet();
//          comboDateType();
//
//          tableDataAddListener();
//          tableDataDeleteListener();
//
//          addEvent();
//          sessionBtn();
//
//          sqlButton();

     }// initializes

//     private void tabRepairDataSetting() {
//          if(Connect.user.getDataType().toString().toUpperCase()
//                    .equals(("oracle").toUpperCase())){
//               tabRepairData.setDisable(false);
//          }else{
//               tabRepairData.setDisable(true);
//          }
//     }
//
//     /** [관라지계정의 접속여부에 따라 세션관리 버튼을 Disable 하게 한다.] */
//     private void sessionBtn() {
//          DatabaseDao seDao = new SessionDaoImpl();
//          btnManageSesi.setVisible(!seDao.checkAuthority());
//
//     }
//
//     /** [버튼 이벤트] */
//     public void addEvent() {
//          btnEpilogue.setOnAction(event->{
//               openEg();
//          });
//          
//          btnSaveSql.setOnAction(event->{
//                saveInSql();
//          });
//          
//          btnSaveAccel.setOnAction(event -> {
//               saveInExcel();
//          });
//          // 세션관리
//          btnManageSesi.setOnAction(event -> {
//               openSesi((Stage) btnOpen.getScene().getWindow());
//          });
//          // [Content] 매뉴바 의 [데이터변경] 클릭시 동작
//          btnRepairData.setOnAction(event -> {
//               repairData();
//          });
//          // [Left] 매뉴바 의 [테이블 초기화] 클릭시 동작
//          btnSelectTables.setOnAction(event -> {
//               treeView();
//          });
//          
//          // [Content] 메뉴바 의 [스크립트 실행] 클릭시 동작
//          btnRun.setOnAction(event -> {
//               run();
//          });
//
//          btnHelp.setOnAction(event -> {
//               help();
//          });
//
//          btnRunAll.setOnAction(event -> {
//               runAll();
//          });
//
//          btnSave.setOnAction(event -> {
//               saveSql((Stage) btnOpen.getScene().getWindow());
//          });
//
//          btnTexCel.setOnAction(event -> {
//               saveTexCel(event, (Stage) btnOpen.getScene().getWindow());
//          });
//          btnOpen.setOnAction(event -> {
//               openSql((Stage) btnOpen.getScene().getWindow());
//          });
//          btnNew.setOnAction(event -> {
//               tabTab("", lite.sql.frontdoor.loginPage.loginList.Connect.user.getId());
//          });
//          btnImport.setOnAction(event -> {
//               importExcel();
//          });
//
//          workPane.setOnKeyPressed(event -> {
//               KeyCombination ctrlN = KeyCombination.keyCombination("Ctrl+N");
//               KeyCombination ctrlO = KeyCombination.keyCombination("Ctrl+O");
//               KeyCombination ctrlS = KeyCombination.keyCombination("Ctrl+S");
//               if (ctrlO.match(event)) {
//                    openSql((Stage) btnOpen.getScene().getWindow());
//               } else if (ctrlN.match(event)) {
//                    tabTab("", lite.sql.frontdoor.loginPage.loginList.Connect.user.getId());
//               } else if (ctrlS.match(event)) {
//                    saveSql((Stage) btnOpen.getScene().getWindow());
//               }
//          });
//
//     }
//
//     private void openEg() {
//          CopyRightThread.swicth = true;
//          Stage stage = new Stage();
//          FXMLLoader loader = new FXMLLoader(
//          // 경로에 주의 한다//경로의 시작은 project명 밑의 src가 root가 한다. 처음에도 /를 넣어야 한다.
//                    getClass().getResource("lite/sql/frontdoor/copyRight/epilogue.fxml"));
//          // javafx.scene.Parent
//          Parent root;
//          Scene scene;
//          try {
//               root = loader.load();
//               scene = new Scene(root);
//               stage.setScene(scene);
//          } catch (Exception e) {
//               e.printStackTrace();
//          }
//          // javafx.scene.Scene
//          // stage.setFullScreen(true);
//          stage.show();
//          stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//               public void handle(WindowEvent we) {
//                    CopyRightThread.swicth = false;
//               }
//          });
//     }
//
//     private void saveInSql() {
//           Alert alert = new Alert(AlertType.INFORMATION);
//           FileChooser chooser = new FileChooser();
//              FileChooser.ExtensionFilter filter = new ExtensionFilter("SQL Files (*.sql)", "*.sql");
//              chooser.getExtensionFilters().add(filter);
//              File file = chooser.showSaveDialog(new Stage());
//              
//              FileWriter writer = null;
//              List<String> tableList = new ArrayList<String>();
//              tableList.add(tabData.getText());
//              try {
//                 FileTxtOut fileTxt = new FileTxtOut();
//                 fileTxt.filetxt(tableList, file.getParent(), file.getName());
//                 
//
//                 alert.setTitle("Information Dialog");
//                 alert.setHeaderText(null);
//                 alert.setContentText("저장완료");
//                 alert.showAndWait();
//              } catch (Exception e) {
//                 alert.setTitle("Information Dialog");
//                 alert.setHeaderText(null);
//                 alert.setContentText("취소되었습니다.");
//                 alert.showAndWait();
//              }
//        }
//
//        private void importExcel() {
//           Alert alert = new Alert(AlertType.INFORMATION);
//           PoiInput poiInput = new PoiInput();
//           FileChooser fileChooser = new FileChooser();
//           FileChooser.ExtensionFilter extfilter = new ExtensionFilter(
//                 "Excel 통합 문서(*.xls)", "*.xls");
//           fileChooser.getExtensionFilters().add(extfilter);
//           File file = fileChooser.showOpenDialog((Stage) btnImport.getScene()
//                 .getWindow());
//           try {
//               
//              String fileName = file.getName();// 파일 이름
//              String filePath = file.getParent();// 파일 주소
//              
//              poiInput.poiInput(fileName, filePath);
//              
//           } catch (Exception e) {
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("취소되었습니다.");
//              alert.showAndWait();
//           }
//        }
//
//        private void saveInExcel() {
//           Alert alert = new Alert(AlertType.INFORMATION);
//           FileChooser chooser = new FileChooser();
//           FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
//                 "Excel 통합 문서 (*.xls)", "*.xls");
//           chooser.getExtensionFilters().add(filter);
//           File file = chooser.showSaveDialog(new Stage());
//
//           try {
//              List<String> tableList = new ArrayList<String>();
//              tableList.add(tabData.getText());
////              System.out.println(file.getParent());
//              PoiOut poi = new PoiOut();
//              poi.poiOutput(tableList, file.getParent(), file.getName());
//              
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("저장완료");
//              alert.showAndWait();
//           } catch (Exception e) {
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("취소되었습니다.");
//              alert.showAndWait();
//           }
//        }
//
//     // 도움말
//     private void help() {
//          Stage stage = new Stage();
//          FXMLLoader loader = new FXMLLoader(
//          // 경로에 주의 한다//경로의 시작은 project명 밑의 src가 root가 한다. 처음에도 /를 넣어야 한다.
//                    getClass().getResource("lite/sql/utility/help/helpbox.fxml"));
//          // javafx.scene.Parent
//          Parent root;
//          Scene scene;
//          try {
//               root = loader.load();
//               scene = new Scene(root);
//               stage.setScene(scene);
//          } catch (Exception e) {
//               e.printStackTrace();
//          }
//          // javafx.scene.Scene
//          // stage.setFullScreen(true);
//          stage.setResizable(false);
//          stage.show();
//     }
//
//     /** 쿼리문 자동완성 기능 버튼 */
//     private void sqlButton() {
//
//          textareaQuery.setOnKeyPressed(event -> {
//               KeyCombination ctrlE = KeyCodeCombination
//                         .keyCombination("Ctrl+Enter");
//               KeyCombination ctrlAE = KeyCodeCombination
//                         .keyCombination("Ctrl+Alt+Enter");
//               KeyCombination AltS = KeyCodeCombination.keyCombination("Alt+S");// 구문
//                                                                                                    // 실행
//                                                                                                    // 키처리
//                    KeyCombination AltU = KeyCodeCombination
//                              .keyCombination("Alt+U");// 구문 실행 키처리
//                    KeyCombination AltI = KeyCodeCombination
//                              .keyCombination("Alt+I");// 구문 실행 키처리
//                    KeyCombination AltD = KeyCodeCombination
//                              .keyCombination("Alt+D");// 구문 실행 키처리
//                    KeyCombination AltA = KeyCodeCombination
//                              .keyCombination("Alt+A");// 구문 실행 키처리
//                    KeyCombination AltC = KeyCodeCombination
//                              .keyCombination("Alt+C");// 구문 실행 키처리
//                    KeyCombination AltR = KeyCodeCombination
//                              .keyCombination("Alt+R");// 구문 실행 키처리
//                    if (ctrlE.match(event)) {
//                         run();
//                    } else if (ctrlAE.match(event)) {
//                         runAll();
//                    } else if (AltS.match(event)) {
//                         sqlSelect();
//                    } else if (AltU.match(event)) {
//                         sqlUpdate();
//                    } else if (AltI.match(event)) {
//                         sqlInsert();
//                    } else if (AltD.match(event)) {
//                         sqlDelete();
//                    } else if (AltA.match(event)) {
//                         sqlAlter();
//                    } else if (AltC.match(event)) {
//                         sqlCreate();
//                    } else if (AltR.match(event)) {
//                         sqlDrop();
//                    }
//               });
//
//          // select * from 테이블명
//          btnSelTxt.setOnAction(event -> {
//               sqlSelect();
//          });
//
//          // update 테이블명 set 변경할 컬럼 = 변경할 데이터 where 조건
//          btnUpdateTxt.setOnAction(event -> {
//               sqlUpdate();
//          });
//
//          // insert into 테이블명(컬럼값) values
//          btnInsTxt.setOnAction(event -> {
//               sqlInsert();
//          });
//
//          // delete 테이블명 where 조건
//          btnDelTxt.setOnAction(event -> {
//               sqlDelete();
//          });
//
//          // alter table 테이블명 (add/modify/drop) column
//          btnAltTxt.setOnAction(event -> {
//               sqlAlter();
//          });
//
//          // create table 테이블명
//          btnCreTxt.setOnAction(event -> {
//               sqlCreate();
//          });
//
//          // drop table 테이블명
//          btnDropTxt.setOnAction(event -> {
//               sqlDrop();
//          });
//
//          // 컬럼 등록
//          btnAppendCol.setOnAction(event -> {
//               inputColumn();
//          });
//
//     }
//
//     private void comboDateType() // comboDateType 추가
//     {
//          ObservableList<String> selected = FXCollections.observableArrayList();
//
//          selected.addAll("VARCHAR2", "NUMBER", "DATE", "CAHR");
//          sidComboDateType.setItems(selected);
//     }
//
//     private void inputColumn() {
//          DatabaseDao dao = new OracleDaoImpl();
//          String inputColumnQuery = "";
//          if (sidTextFieldColName.getText().equals("")
//                    || sidTextFieldColName == null
//                    || sidComboDateType.getValue() == null) {
////               System.out.println("입력하시오");
//          } else {
//               if (sidTextFieldDefault.getText().equals("")
//                         || sidTextFieldDefault.getText() == null) {
//                    if (sidComboDateType.getValue().equals("VARCHAR2")) {
//                         inputColumnQuery = "ALTER TABLE " + tabData.getText()
//                                   + " ADD (" + sidTextFieldColName.getText() + " "
//                                   + sidComboDateType.getValue() + "(30));";
//                    } else {
//                         inputColumnQuery = "ALTER TABLE " + tabData.getText()
//                                   + " ADD (" + sidTextFieldColName.getText() + " "
//                                   + sidComboDateType.getValue() + ");";
//                    }
//               } else {
//                    if (sidComboDateType.getValue().equals("VARCHAR2")) {
//                         inputColumnQuery = "ALTER TABLE " + tabData.getText()
//                                   + " ADD (" + sidTextFieldColName.getText() + " "
//                                   + sidComboDateType.getValue() + "(30) default '"
//                                   + sidTextFieldDefault.getText() + "');";
//                    } else {
//                         inputColumnQuery = "ALTER TABLE " + tabData.getText()
//                                   + " ADD (" + sidTextFieldColName.getText() + " "
//                                   + sidComboDateType.getValue() + " DEFAULT "
//                                   + sidTextFieldDefault.getText() + " not null);";
//                    }
//               }
//          }
//          QueryResultVo vo = dao.checkQuery(inputColumnQuery);
//          if (vo.getError() != null) {
//               Alert alert = new Alert(AlertType.INFORMATION);
//               alert.setTitle("확인");
////               System.out.println(vo.getError());
//               alert.setContentText(vo.getError());
//               alert.showAndWait().get();
//          }
//     }
//
//     // select 자동완성
//     private void sqlSelect() {
//          String select = "SELECT * FROM 테이블명;";
//          scriptSql(select);
//     }
//
//     // update 자동완성
//     private void sqlUpdate() {
//          String update = "UPDATE 테이블명 SET 변경할 컬럼 = 변경할 데이터 WHERE 조건;";
//          scriptSql(update);
//     }
//
//     // insert 자동완성
//     private void sqlInsert() {
//          String insert = "INSERT INTO 테이블명(컬럼값) VALUES(데이터);";
//          scriptSql(insert);
//     }
//
//     // delete 자동완성
//     private void sqlDelete() {
//          String delete = "DELETE 테이블명 WHERE 조건;";
//          scriptSql(delete);
//     }
//
//     // alter 자동완성
//     private void sqlAlter() {
//          String alter = "ALTER TABLE 테이블명 ADD/MODIFY/DROP;";
//          scriptSql(alter);
//     }
//
//     // create 자동완성
//     private void sqlCreate() {
//          String create = "CREATE TABLE 테이블명;";
//          scriptSql(create);
//     }
//
//     // drop 자동완성
//     private void sqlDrop() {
//          String drop = "DROP TABLE 테이블명;";
//          scriptSql(drop);
//     }
//
//     // 자동완성 메서드
//     private void scriptSql(String Query) {
//          StringBuilder sb = new StringBuilder();
//          String tmp = null;
//          Tab tab = null;
//          VBox vb = null;
//          TextArea ta = null;
//          tab = contentTabpaneUp.selectionModelProperty().get().getSelectedItem();
//          if (tab.getContent().toString().contains("VBox")) {
//               vb = (VBox) tab.getContent();
//          } else {
//               return;
//          }// if
//          for (int i = 0; i < vb.getChildren().size(); i++) {
//               if (vb.getChildren().get(i).toString().contains("TextArea")) {
//                    ta = (TextArea) vb.getChildren().get(i);
//                    tmp = ta.getText();
//                    sb.append(tmp);
//                    sb.append("\n" + Query + "\n");
//                    ta.setText(sb.toString());
//                    return;
//               }
//          }// for
//          if (tmp == null) {
//               return;
//          }// if
//     }// scriptSql
//
//     /**마지막줄 스크립트 실행*/
//        private void run() {
//           //파일 내용선택 입력
//             String allQ = null;
//             Tab tab   = contentTabpaneUp.selectionModelProperty().get().getSelectedItem();
//             VBox vbox =null;
//             if(tab.getContent().toString().contains("VBox")){
//                 vbox = (VBox) tab.getContent();
//             }else{
//                 return;
//             }
//             TextArea area = null;
//             for(int i =0; i<vbox.getChildren().size(); i++){
//                 if(vbox.getChildren().get(i).toString().contains("TextArea")){
//                     area = (TextArea) vbox.getChildren().get(i);
//                     allQ = area.getText().trim();
//                 }
//             }
//             if(allQ==null){
//                 return;
//             }
//             //textArea 안의 전체 구문을 가져온다
//            
//            String[] input = allQ.split(";"); // ;로 잘라서 배열에 넣는다
//            
////               System.out.println();
////               System.out.println(input[input.length-1]);
//               QueryResultVo qry =  dao.checkQuery(input[input.length-1]);
//               setReport(qry);
//        
//        }
//
//
//        /**전체스크립트실행*/
//         private void runAll() {
//             String allQ = null;
//              Tab tab   = contentTabpaneUp.selectionModelProperty().get().getSelectedItem();
//              VBox vbox =null;
//              if(tab.getContent().toString().contains("VBox")){
//                  vbox = (VBox) tab.getContent();
//              }else{
//                  return;
//              }
//              TextArea area = new TextArea();
//              
//              for(int i =0; i<vbox.getChildren().size(); i++){
//                  if(vbox.getChildren().get(i).toString().contains("TextArea")){
//                      area = (TextArea) vbox.getChildren().get(i);
//                      allQ = area.getText().trim();
//                  }
//              }
//              if(allQ==null||allQ.equals("")){
//                  return;
//              }
//             //textArea 안의 전체 구문을 가져온다
//             String[] input = allQ.split(";"); // ;로 잘라서 배열에 넣는다
//             
//             for (int i = 0; i < input.length; i++) {
////                System.out.println(input.length);
////                System.out.println(input[i]);
//                QueryResultVo allqry =  dao.checkQuery(input[i]);
//                setReport(allqry);
//            }
//         }
//
//
//     private void saveTexCel(Event event, Stage primaryStage) {
//          if (true) {
//               Stage stage = new Stage();
//               FXMLLoader loader = new FXMLLoader(
//               // 경로에 주의 한다
//               // 경로의 시작은 project명 밑의 src가 root가 한다. 처음에도 /를 넣어야 한다.
//                         getClass().getResource("/lite/sql/frontdoor/checkbox/checkbox.fxml"));
//
//               Parent root;
//               Scene scene;
//               try {
//                    root = loader.load();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//               } catch (Exception e) {
//                    e.printStackTrace();
//               }
//               stage.setResizable(false);
//               stage.initModality(Modality.WINDOW_MODAL);
//               stage.initOwner(primaryStage);
//               stage.show();
//          }
//
//     }
//
//     private void saveSql(Stage primaryStage) {
//           // 파일저장 경로와 새이름 선택
//           Alert alert = new Alert(AlertType.INFORMATION);
//           FileChooser fc = new FileChooser();
//           FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                 "TXT files(*.txt)", "*.txt");
//           fc.getExtensionFilters().add(extFilter);
//           extFilter = new FileChooser.ExtensionFilter("SQL files(*.SQL)", "*.sql");
//           fc.getExtensionFilters().add(extFilter);
//           File file = fc.showSaveDialog(primaryStage);
//           // 파일 내용선택 입력
//           String saveHelp = null;
//           Tab tab = contentTabpaneUp.selectionModelProperty().get()
//                 .getSelectedItem();
//           VBox vbox = null;
//           if (tab.getContent().toString().contains("VBox")) {
//              vbox = (VBox) tab.getContent();
//           } else {
//                    QueryResultVo vo = new QueryResultVo();
//                 
//                    setReport(vo);
//                    return;
//           }
//           TextArea area = null;
//           for (int i = 0; i < vbox.getChildren().size(); i++) {
//              if (vbox.getChildren().get(i).toString().contains("TextArea")) {
//                 area = (TextArea) vbox.getChildren().get(i);
//                 saveHelp = area.getText();
//              }
//           }
//           // 내용이 없으면 오류보고
//           if(saveHelp==null){
//                 QueryResultVo vo = new QueryResultVo();
//              //   vo.setError("[문서저장오류2]저장할 내용이 없습니다.");
//                 setReport(vo);
//                 return;
//            }
//
//           FileWriter fw = null;
//           try {
//              fw = new FileWriter(file);
////              System.out.println(fw.getEncoding());
//              fw.write(saveHelp);
//              fw.flush();
//              fw.close();
//              
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("저장완료");
//              alert.showAndWait();
//           } catch (Exception e) {
//              
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("취소되었습니다.");
//              alert.showAndWait();
//           }// /try catch
//        }// saveSql
//
//        private void openSql(Stage primaryStage) {
//           // 0.1.입력할 파일 선택
//           Alert alert = new Alert(AlertType.INFORMATION);
//           FileChooser fc = new FileChooser();
//           FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                 "데이터 베이스 파일", "*.sql");
//           FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter(
//                 "텍스트 파일", "*.TXT");
//
//           fc.getExtensionFilters().add(extFilter2);
//           fc.getExtensionFilters().add(extFilter);
//           fc.setInitialDirectory(new File("C:/"));
//           File file = fc.showOpenDialog(primaryStage);
//
//           // 0.3 내용
//           try {
//              FileInputStream fis = new FileInputStream(file);
//              InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//              BufferedReader reader = new BufferedReader(isr);
//              StringBuilder sb = new StringBuilder();
//
//              String str = "";
//              int data = 0;
//              // Fis로 읽은 파일을 더이상 읽을게 없을때 까지 읽는다
//              // 더이상 읽을게 없으면 -1을 리턴
//              while ((data = reader.read()) != -1) {
//                 // 읽을 char를 sb에 append
//                 sb.append((char) data);
//              }
//              // 파일이름
//              String sqlTitle = file.getName();
//              // 파일경로
//              // String[] input = path.split(sqlTitle);
//              // String path = file.getAbsolutePath();
//
//              tabTab(sb.toString(), sqlTitle);
//              fis.close();
//           } catch (Exception ex) {
//              alert.setTitle("Information Dialog");
//              alert.setHeaderText(null);
//              alert.setContentText("취소되었습니다.");
//              alert.showAndWait();
//           }
//        }
//
//     private void tabTab(String content, String title) {
//          // 객체생성
//          Tab tab = new Tab();
//          VBox vb = new VBox();
//          TextArea ta = new TextArea();
//          ToolBar tb = new ToolBar();
//          Button btn = new Button();
//          // 객체구조
//          tab.setContent(vb);
//          vb.getChildren().add(ta);
//          vb.getChildren().add(tb);
//          ta.setPadding(new Insets(10));
//          ta.setPrefSize(875, 375);
//          tb.setPrefSize(200, 40);
//          
//          ta.setStyle("" + "-fx-font-size: 16px;"
//                   + "-fx-background-color: skyblue");
//
//          ta.setOnKeyPressed(event -> {
//               KeyCombination ctrlE = KeyCodeCombination.keyCombination("Ctrl+Enter");
//               KeyCombination ctrlAE = KeyCodeCombination.keyCombination("Ctrl+Alt+Enter");
//               KeyCombination AltS = KeyCodeCombination.keyCombination("Alt+S");// 구문// 실행// 키처리
//               KeyCombination AltU = KeyCodeCombination.keyCombination("Alt+U");// 구문// 실행// 키처리
//               KeyCombination AltI = KeyCodeCombination.keyCombination("Alt+I");// 구문// 실행// 키처리
//               KeyCombination AltD = KeyCodeCombination.keyCombination("Alt+D");// 구문// 실행// 키처리
//               KeyCombination AltA = KeyCodeCombination.keyCombination("Alt+A");// 구문// 실행// 키처리
//               KeyCombination AltC = KeyCodeCombination.keyCombination("Alt+C");// 구문// 실행// 키처리
//               KeyCombination AltR = KeyCodeCombination.keyCombination("Alt+R");// 구문// 실행// 키처리
//               
//               
//               if (ctrlE.match(event)) {
//                    run();
//               } else if (ctrlAE.match(event)) {
//                    runAll();
//               } else if (AltS.match(event)) {
//                    sqlSelect();
//               } else if (AltU.match(event)) {
//                    sqlUpdate();
//               } else if (AltI.match(event)) {
//                    sqlInsert();
//               } else if (AltD.match(event)) {
//                    sqlDelete();
//               } else if (AltA.match(event)) {
//                    sqlAlter();
//               } else if (AltC.match(event)) {
//                    sqlCreate();
//               } else if (AltR.match(event)) {
//                    sqlDrop();
//               }
//          });
//
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnSelTxt.getEventDispatcher());
//          btn.setText("Select");
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnUpdateTxt.getEventDispatcher());
//          btn.setText("Update");
//
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnInsTxt.getEventDispatcher());
//          btn.setText("Insert");
//
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnDelTxt.getEventDispatcher());
//          btn.setText("Delete");
//
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnAltTxt.getEventDispatcher());
//          btn.setText("Alter");
//
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnCreTxt.getEventDispatcher());
//          btn.setText("Create");
//
//          btn = new Button();
//          tb.getItems().add(btn);
//          btn.setEventDispatcher(btnDropTxt.getEventDispatcher());
//          btn.setText("Drop");
//
//          tab.setText(title);
//          contentTabpaneUp.getTabs().add(tab);
//          if (content.equals("")) {
//               return;
//          } else if (!content.equals("")) {
//               ta.setText(content);
//          }
//          tab.setId(title);
//     }
//
//     /** [세션관리-새창] */
//     public void openSesi(Stage primaryStage) {
//          SessionThread.swicth = true;
//          Stage stage = new Stage();
//          FXMLLoader loader = new FXMLLoader(
//          // 경로에 주의 한다//경로의 시작은 project명 밑의 src가 root가 한다. 처음에도 /를 넣어야 한다.
//                    getClass().getResource("/lite/sql/frontdoor/admin/adminSesi.fxml"));
//          // javafx.scene.Parent
//          Parent root;
//          Scene scene;
//          try {
//               root = loader.load();
//               scene = new Scene(root);
//               stage.setScene(scene);
//          } catch (Exception e) {
//               e.printStackTrace();
//          }
//          // javafx.scene.Scene
//          // stage.setFullScreen(true);
//          stage.setResizable(false);
//          stage.initModality(Modality.WINDOW_MODAL);
//          stage.initOwner(primaryStage);
//          stage.show();
//          stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//               public void handle(WindowEvent we) {
//                    SessionThread.swicth = false;
//               }
//          });
//     }// openSesi
//          // =============================================================================
//
//     /** [데이터테이블-리스너해제]리스너를 해제한다. */
//     private void tableDataDeleteListener() {
//          // tableData.addEventHandler(eventType, eventHandler);
//
//     }
//
//     // ======================================================================================================
//     /**
//      * [데이터변경-리스너등록]데이터 데이블에서 선택한 레코드를 데이터변경 시트에서 출력한다. 데이터변경 Pane을 초기화하고, 레코드
//      * 출력 메소드를 호출한다.
//      */
//     private Object selected = null;
//
//     private void tableDataAddListener() {
//          tableData.getSelectionModel().selectedItemProperty()
//                    .addListener(new ChangeListener<Object>() {
//                         @Override
//                         public void changed(
//                                   ObservableValue<? extends Object> observable,
//                                   Object oldValue, Object newValue) {
//                              
//                              ObservableList<String> selected = FXCollections
//                                        .observableArrayList();
//
//                              selected = (ObservableList<String>) newValue;
//                              setRepairData(selected);
//                              contentTabpaneDown.selectionModelProperty().get()
//                                        .select(2);
//                         }
//                    });
//     }// tableDataListener()
//          // ================================================================================================================
//
//     /**
//      * [tableData] 테이블 이름에 해당하는 데이터를 테이블뷰에 출력한다. 테이블 이름을 텝테이블에 setText 한다. 파라미터로
//      * 받은 query 는 테이블의 이름이다.
//      * */
//     private void setTableData(String query) {
//          // 데이터 내용
//          List<List<String>> list = new ArrayList<List<String>>();
//          list = dao.getData(query);
//          // 0.1초기화
//          if (list == null) {
//               return;
//          } else if (list.size() == 0) {
//               tableData.getColumns().clear();
//               tableData.getItems().clear();
//               tabData.setText(query);
//               return;
//          }
//          // 0.2초기화
//          tableData.getColumns().clear();
//          tableData.getItems().clear();
//          tabData.setText(query);
//
//          // 컬럼이
//          List<String> columName = list.get(0);
//          // 컬럼수
//          int colums = columName.size();
//          // =[데이터 변환]======================================================
//          buildData(list, colums, columName);
//
//          // [컬럼 추가, value입력]
//          for (int i = 0; i < colums; i++) {
//               final int j = i;
//               TableColumn col = new TableColumn(columName.get(i));
//
//               col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    public ObservableValue<String> call(
//                              CellDataFeatures<ObservableList, String> param) {
//                         return new SimpleStringProperty(param.getValue().get(j)
//                                   .toString());
//                    }
//               });
//               tableData.getColumns().add(col);
//               if (col.getText().toString().equals("ROWID")) {
//                    col.setVisible(false);
//               }
//          }
//          // [data입력해주기]
//          tableData.setItems(data);
//     } // setTableData
//
//     /** 응답받은 list String 형식의 결과를 ObservableList 로 변환한다. */
//     private void buildData(List<List<String>> list, int colums,
//               List<String> columName) {
//          data = FXCollections.observableArrayList();
//          ObservableList<String> observ;
//          for (int i = 1; i < list.size(); i++) {
//               observ = FXCollections.observableArrayList();
//               List<String> recode = list.get(i);
//               for (int j = 0; j < colums; j++) {
//                    observ.add(recode.get(j));
//               }
//               data.add(observ);
//          }
//     }// buildData
//          // =======================================================================
//          // ======[2016.06.10] 담당 이다정 ,신규등록 이성현
//
//     /** [데이터변경-입력전]메서드 높이사이즈 자동조절됨. */
//     private void setRepairData(ObservableList<String> selected) {
//          // 1.컬럼이 5개일 때,
//          // 1.1.컬럼의 내용
//          flowerRepairData.getChildren().clear();
//          List<String> columns = new ArrayList<>();
//          for (int i = 0; i < tableData.getColumns().size(); i++) {
//               columns.add(tableData.getColumns().get(i).getText());
//          }
//          // 1.2.레코드의 내용
//          List<String> recode = selected;
//
//          // 1.3.컬럼의 길이
//          int colLength = columns.size();
//          // 2.레코드 생성
//          for (int i = 0; i < columns.size(); i++) {
//               // 0.객체생성
//               // 0.1flower > HBOX(Label,TextField) 포함
//               HBox newHbox = new HBox();
//               Label newLabel = new Label();
//               TextField newTextField = new TextField();
//               newHbox.getChildren().add(0, newLabel);
//               newHbox.getChildren().add(1, newTextField);
//               flowerRepairData.getChildren().add(i, newHbox);
//               // 0.2HBOX 크기지정
//               newHbox.setPrefSize(210, 40);// 너비,높이
//               newHbox.setSpacing(2);// 내부 객체간 수평간격
//               newLabel.setCenterShape(true);
//               newLabel.setPrefSize(100, 40);
//               newTextField.setPrefSize(100, 40);
//               // 1.데이터 입력 ,아이디부여 ,컬럼이름부여
//               newTextField.setId("아이디" + (i));
//               newTextField.setText(recode.get((i) % colLength));
//               newLabel.setText(columns.get((i) % colLength));
//               // 2.rowid 수정불가
//               if (newLabel.getText().toString().equals("ROWID")) {
//                    newTextField.setDisable(true);
//               }
//          }
//     }// setRepairData
//
//        private void repairData() {
//            Tab tab = contentTabpaneUp.selectionModelProperty().get().getSelectedItem();
//            if(tab.getContent().toString().contains("VBox")){
//                VBox vbox= (VBox) tab.getContent();
//                for (int z = 0; z < vbox.getChildren().size(); z++) {
//                   if(vbox.getChildren().get(z).toString().contains("TableView")){
//                       
//                       List<Map> modify = new ArrayList<Map>();
//
//                       Map<String, String> gd = new HashMap<String, String>();
//
//                       for (int i = 0; i < tableData.getColumns().size(); i++) {
//
//                           HBox hbox = new HBox();
//                           TextField textF = new TextField();
//                           hbox = (HBox) flowerRepairData.getChildren().get(i);
//                           textF = (TextField) hbox.getChildren().get(1);
//
//                           gd.put(tableData.getColumns().get(i).getText(), textF.getText());
//
//                       }
//
//                       modify.add(gd);
//
//                       String tableName = tabData.getText();
//
//                       QueryResultVo vo2 = dao.modifyData(modify, tableName);
//
//                       if (vo2 != null) {
//                           setTableData(tableName);
//                           setReport(vo2);
//
//                       }
//                      
//                   }
//                 }
//            }
//
//           
//
//           // dao.updat(repairQuery);
//        }// repairData
//          // ====================================================================================
//
//     // ======[2016.06.11] 담당 이성현 ,신규등록 이성현
//
//        /** [사용자기능-컨텐트영역 높이변경]메서드 (리스너 호출 메서드 포함) */
//        private void setRepairContentsheet() {
//             // 컨텐트영역 높이변경 창의 버튼 1.열기(버튼) 2.닫기(버튼) 3.닫기(배경) 4.기본값체크
//             btnOpeneRepairContentsheet.setOnAction(event -> {
//                panBacgraound.setVisible(true);
//             });
//             btnCloseRepairContentsheet.setOnAction(event -> {
//                panBacgraound.setVisible(false);
//             });
//             panBacgraound.setOnMouseClicked(event -> {
//                panBacgraound.setVisible(false);
//             });
//             chekBaseContentsheet.setOnAction(event -> {
//                 System.out.println("실행");
//             if (chekBaseContentsheet.selectedProperty().getValue()) {
//                   scrollContentsheet.setDisable(true);
//                   scrollWidth.setDisable(true);
//                   contentTabpaneUp.setPrefHeight(450);
//                   contentTabpaneDown.setPrefHeight(230);
//                   LeftVbox.setPrefWidth(530);
//                   contentTabpaneUp.setPrefWidth(875);
//                   contentTabpaneDown.setPrefWidth(875);
//                   contentToolbarWork.setPrefWidth(900);
//                   VBox vbox =null;
//                   for(int i=0; i<contentTabpaneUp.getTabs().size();i++){
//                         if(contentTabpaneUp.getTabs().get(i).getContent().toString().contains("VBox")){
//                             vbox = (VBox) contentTabpaneUp.getTabs().get(i).getContent();                        
//                             for (int j = 0; j < vbox.getChildren().size(); j++) {
//                                 if(vbox.getChildren().get(j).toString().contains("TextArea")){
//                                     TextArea ta = (TextArea) vbox.getChildren().get(j);
//                                     ta.setPrefHeight(410);
//                                 }else if(vbox.getChildren().get(j).toString().contains("TableView")){
//                                     TableView<String> tv = (TableView<String>) vbox.getChildren().get(j);
//                                     tv.setPrefHeight(410);
//                                 }//if
//                             }//for
//                         }//if
//                   }//for              
//                } else if (!chekBaseContentsheet.selectedProperty().getValue()) {
//                   scrollContentsheet.setDisable(false);
//                   scrollWidth.setDisable(false);
//                }//if
//             });
//             setLisenar();
//             setLisenar2();
//          }// setRepairWorksheet
//     // ==============================================================================================
//
//     /** [컨텐트영역-가로변경]리스너 */
//     private void setLisenar2() {
//          scrollWidth.valueProperty().addListener(new ChangeListener<Number>() {
//               @Override
//               public void changed(ObservableValue<? extends Number> observable,
//                         Number oldValue, Number newValue) {
//                    // 컨텐트 가로1000 ,
//
//                    // 높이 6 변화한다.
//                    // 0.초기회
//                    if (oldValue == null)
//                         oldValue = 0;
//                    // 0.에러방지
//
////                    System.out.println("올드" + oldValue.intValue());
////                    System.out.println("뉴" + newValue.intValue());
//
//                    if (oldValue.intValue() == newValue.intValue()) {
//                         // 1.증가
//                    } else if ((!chekBaseContentsheet.selectedProperty().getValue())
//                              && ((oldValue.intValue() < newValue.intValue()) || (oldValue
//                                        .intValue() > newValue.intValue()))) {
//                         LeftVbox.setPrefWidth(340 + 300 - 4 * newValue.intValue());
//                         contentTabpaneUp.setPrefWidth(765 + 4 * newValue.intValue());
//                         contentTabpaneDown.setPrefWidth(765 + 4 * newValue
//                                   .intValue());
//                         contentToolbarWork.setPrefWidth(765 + 4 * newValue
//                                   .intValue());
//
//                    }
//               }
//          });
//     }// setLisenar
//
//     // ==============================================================================================
//     /** [컨텐트영역-높이변경]리스너 */
//
//     private void setLisenar() {
//          scrollContentsheet.valueProperty().addListener(
//                    new ChangeListener<Number>() {
//                         @Override
//                         public void changed(
//                                   ObservableValue<? extends Number> observable,
//                                   Number oldValue, Number newValue) {
//                              // 컨텐트 높이 740 ,버튼바 높이 40 ,contentTabpaneUp 440
//                              // ,contentTabpaneDown 230
//                              // 탭의 매뉴영역 높이 잠정 70 예상함. ,실제 유동가능높이 600 이다. newValue 1당
//                              // 높이 6 변화한다.
//                              // 0.초기회
//                              if (oldValue == null)
//                                   oldValue = 0;
//                              // 0.에러방지
//                              if (oldValue.intValue() == newValue.intValue()) {
//                                   // 1.증가
//                              } else if ((!chekBaseContentsheet.selectedProperty()
//                                        .getValue())
//                                        && ((oldValue.intValue() < newValue.intValue()) || (oldValue
//                                                  .intValue() > newValue.intValue()))) {
//                                   // newValue 는 기본 0~100의 값을 가진다.
//                                   // 35는 탭의 Title 영역이고, 600 을 기준으로 100%로 분배된다.
//                                   contentTabpaneUp
//                                             .setPrefHeight(35 + 600 - 4.9 * newValue
//                                                       .intValue());
//                                   contentTabpaneDown
//                                             .setPrefHeight(35 + 50 + 4.9 * newValue
//                                                       .intValue());
//
//                                   for (int i = 0; i < contentTabpaneUp.getTabs()
//                                             .size(); i++) {
//                                        String str = contentTabpaneUp.getTabs().get(i)
//                                                  .getContent().toString();
////                                        System.out.println(str.contains("VBox"));
//                                        if (str.contains("VBox")) {
//                                             VBox vb = (VBox) contentTabpaneUp.getTabs()
//                                                       .get(i).getContent();
//                                             for (int j = 0; j < vb.getChildren().size(); j++) {
//                                                  String str2 = vb.getChildren().get(j)
//                                                            .toString();
//                                                  if (str2.contains("Text")) {
//                                                       TextArea ta = (TextArea) vb
//                                                                 .getChildren().get(j);
//                                                       ta.setPrefHeight(550 - 4.9 * newValue
//                                                                 .intValue());
//                                                  }// if
//                                                  if (str2.contains("Table")) {
//                                                       TableView<Object> tv = (TableView<Object>) vb
//                                                                 .getChildren().get(j);
//                                                       tv.setPrefHeight(550 - 4.9 * newValue
//                                                                 .intValue());
//                                                  }
//                                             }// for
//                                        }// if
//                                   }// for
//
//                                   for (int i = 0; i < contentTabpaneDown.getTabs()
//                                             .size(); i++) {
//                                        String str = contentTabpaneDown.getTabs()
//                                                  .get(i).getContent().toString();
////                                        System.out.println(str.contains("VBox"));
//                                        if (str.contains("VBox")) {
//                                             VBox vb = (VBox) contentTabpaneDown
//                                                       .getTabs().get(i).getContent();
//                                             for (int j = 0; j < vb.getChildren().size(); j++) {
//                                                  String str2 = vb.getChildren().get(j)
//                                                            .toString();
//                                                  if (str2.contains("Scroll")) {
//                                                       ScrollPane scr = (ScrollPane) vb
//                                                                 .getChildren().get(j);
//                                                       scr.setPrefHeight(200 + 4.9 * newValue
//                                                                 .intValue());
//
//                                                  }// if
//                                             }// for
//                                        }// if
//                                   }// for
//
//                              }// if
//                         }// changed
//                    });
//     }// setLisenar
//
//     // ====================================================================================
//     /** [테이블 목록조회-리스너포함] */
//     private void treeView() {
//          sideLabelTableName.setText("");
//          sidLabelAppendCol.setText("");
//          // rootItem 생성
//          TreeItem<String> user = new TreeItem<String>();
//          user.setExpanded(true); // treeview를 펼쳐보이게 설정
//          // Expanded 설정을 안하거나 false로 설정하면 접힌상태로 나타난다.
//          List<String> list = dao.tableList();
//
//          for (int i = 0; i < list.size(); i++) {
//               TreeItem<String> item = new TreeItem<String>(list.get(i));
//               user.getChildren().add(item);
//               List<String> colList = dao.collist(list.get(i));
//               for (int j = 0; j < colList.size(); j++) {
//                    TreeItem<String> column = new TreeItem<String>(colList.get(j));
//                    item.getChildren().add(column);
//               }
//          }
//          // treeview인스턴스생성
//          TreeView<String> treeview = new TreeView<String>(user);
//          // treeTables.getChildrenUnmodifiable().replaceAll(rootItem);
//          treeview.setShowRoot(false);
//          paneStack.getChildren().add(treeview);
//
//          treeview.getSelectionModel().selectedItemProperty()
//                    .addListener(new ChangeListener<TreeItem<String>>() {
//                         @Override
//                         public void changed(
//                                   ObservableValue<? extends TreeItem<String>> observable,
//                                   TreeItem<String> oldValue, TreeItem<String> newValue) {
//                              List<ColType> coltpe = null;
//                              
//                              if (newValue.getParent().getValue() == null) {
//                                   setTableData(newValue.getValue());
//                                   coltpe = dao.coltype(newValue.getValue());
//                                   dao.getData(newValue.getValue());
//                                   sideLabelTableName.setText(newValue.getValue()
//                                             + "의 컬럼목록 및 컬럼속성");
//                                   sidLabelAppendCol.setText(newValue.getValue()
//                                             + "의 컬럼추가");
//
//                              } else {
//                                   setTableData(newValue.getParent().getValue());
//                                   coltpe = dao.coltype(newValue.getParent()
//                                             .getValue());
//                                   dao.getData(newValue.getParent().getValue());
//                                   sideLabelTableName.setText(newValue.getParent()
//                                             .getValue() + "의 컬럼목록 및 컬럼속성");
//                                   sidLabelAppendCol.setText(newValue.getParent()
//                                             .getValue() + "의 컬럼추가");
//                              }
//                              sideColColumnName
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "COLUMN_NAME"));
//                              sideColDateType
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "DATE_TYPE"));
//                              sideColNullable
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "NULLABLE"));
//                              sideColDateDefault
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "DATE_DEFAULT"));
//                              sideColColumnId
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "COLUMN_ID"));
//                              sideColComments
//                                        .setCellValueFactory(new PropertyValueFactory<ColType, String>(
//                                                  "COMMENTS"));
//                              sideTableColumns.setItems(FXCollections
//                                        .observableArrayList(coltpe));
//                              contentTabpaneUp.selectionModelProperty().get()
//                                        .select(2);
//                              ;
//                              // if
//                         }
//                    });
//     }// treeView()
//          // ===============================================================================================
//
//     /** [통합-결과출력] */
//     private void setReport(QueryResultVo vo2) {
//          // 1.질의하고 응답을 받는다.
//          QueryResultVo vo = vo2;
//          // 2.응답내용을 검사한다.
//          // 검사결과를 바탕으로 출력방식을 결정한다.
//          // 선택된 출력방식으로 출혁한다.
//          boolean autoTabs = true;
//          boolean reportSelect = false;
//          String tmp = "";
//          if (vo.getInsert() > 0) {
//               tmp = vo.getInsert() + "행이 입력됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getDelete() > 0) {
//               tmp = vo.getDelete() + "행이 삭제됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getUpdate() > 0) {
//               tmp = vo.getUpdate() + "행이 수정됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getModify() > 0) {
//               tmp = vo.getModify() + "테이블 또는 행이 수정됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getCreate() > 0) {
//               tmp = vo.getCreate() + "개의 테이블이 생성됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getDrop() > 0) {
//               tmp = vo.getDrop() + "테이블이 삭제됐습니다.\n";
//               addReportScript(tmp);
//          }
//          if (vo.getAlter() > 0) {
//               tmp = "테이블 구조가 변경되었습니다.\n";
//               addReportScript(tmp);
//          }
//          if (!(vo.getError() == null || vo.getError().equals(""))) {
//               addReportScript(vo.getError());
//          }
//          if (vo.getEctResultSet() != null) {
//               addReportScript(vo.getEctResultSet().toString());
//          }
//
//          if (vo.getSelectResultSet() != null) {
//               reportSelect = true;
//
//          }
//          if (reportSelect) {
//               List<List<String>> result = new ArrayList<List<String>>();
//               ResultSet rs = vo.getSelectResultSet();
//               int tmpq = 0;
//
//               try {
//                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
//                    List<String> row = new ArrayList<String>();
//                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
//                         // 컬럼값 한개씩 가져오기
//                         row.add(resultSetMetaData.getColumnName(i));
//                    }
//                    result.add(row);
//                    while (rs.next()) {
//                         row = new ArrayList<String>();
//
//                         for (int j = 1; j <= resultSetMetaData.getColumnCount(); j++) {
//                              String getString = rs.getString(resultSetMetaData
//                                        .getColumnName(j));
//                              row.add(getString != null ? getString : "null");
//                         }
//
//                         result.add(row);
//                    }
//               } catch (SQLException e) {
//                    e.printStackTrace();
//               } catch (Exception e1) {
//                    e1.printStackTrace();
//               } finally {
//                    try {
//
//                         if (vo.getStatement() != null) {
//                              // statement 닫기
//                              vo.getStatement().close();
////                              System.out.println("statememt 종료");
//                         }
//                    } catch (SQLException e) {
////                         System.out.println("[닫기 오류]\n" + e.getStackTrace());
//                         e.getStackTrace();
//                    }
//               }
//
//               addReportSelect(result);
//               autoTabs = false;
//          }
//
//          if (autoTabs) {
//               contentTabpaneDown.selectionModelProperty().get().select(1);
//               ;
//          } else {
//               contentTabpaneDown.selectionModelProperty().get().select(0);
//               ;
//          }
//     }// setReport
//
//     /** [스크립트 결과 및 오류보고] */
//     public void addReportScript(String result) {
//          StringBuilder sd = new StringBuilder();
//          sd.append(textareaReportErrorScript.getText());
//          sd.append("\n");
//          sd.append(result);
//          textareaReportErrorScript.setText(sd.toString());
//          textareaReportErrorScript.setEditable(false);
//          textareaReportErrorScript.selectPositionCaret(textareaReportErrorScript
//                    .getLength());
//          textareaReportErrorScript.deselect();
//
//     }// addReport
//
//     /** [실행결과-SELECT] 테이블 형식의 실행결과시 동작 */
//     public void addReportSelect(List<List<String>> result) {
//          // 데이터 내용
////          System.out.println(result.toString() + "g111");
//          List<List<String>> list = result;
//          // 0.1초기화
//          if (list.size() == 0) {
//               tableReportSelect.getColumns().clear();
//               tableReportSelect.getItems().clear();
//               return;
//          }
//          // 0.2초기화
//          tableReportSelect.getColumns().clear();
//          tableReportSelect.getItems().clear();
//          // 컬럼이름
//          List<String> columName = list.get(0);
//          // System.out.println(columName);
//          // 컬럼수
//          int colums = columName.size();
//          // =[데이터 변환]======================================================
//          buildData(list, colums, columName);
//
//          // [컬럼 추가, value입력]
//          for (int i = 0; i < colums; i++) {
//               final int j = i;
//               TableColumn col = new TableColumn(columName.get(i));
//               col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    public ObservableValue<String> call(
//                              CellDataFeatures<ObservableList, String> param) {
//                         return new SimpleStringProperty(param.getValue().get(j)
//                                   .toString());
//                    }
//               });
//               tableReportSelect.getColumns().add(col);
//          }
//          // // [data입력해주기]
//          tableReportSelect.setItems(data);
//     }// addReportSelect
//          // =====================================================================
}// CLASS