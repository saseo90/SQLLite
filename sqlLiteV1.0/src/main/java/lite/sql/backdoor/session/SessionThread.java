package lite.sql.backdoor.session;

import java.util.ArrayList;
import java.util.List;

import lite.sql.frontdoor.admin.AdminSesiController;
import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.OracleDaoImpl;
import javafx.application.Platform;


public class SessionThread extends Thread {
	
	
	public static Object currentStageController = null;
	
	public static boolean swicth = true;
	
	
	DatabaseDao dao = Connect.dao;
	List<List<String>> sessionList = new ArrayList<List<String>>();
	List<List<String>> queryList = new ArrayList<List<String>>();
	
	public SessionThread(){
	}
	
	public void run(){
		
		AdminSesiController controller = (AdminSesiController) currentStageController;
		
		try {
			//계속해서 시간을 보여줘야 하기 때문에 while()문 이용
			while(swicth){
				
				Platform.runLater(()->{
					sessionList = dao.getSession();
					
					controller.setTable(sessionList);
					
					queryList = dao.getQuery();
					
					controller.setQueryArea(queryList);
				});
				SessionThread.sleep(10000);//5초(밀리단위)동안 잠시 대기
				
				
			}
		} catch (Exception e) {
			
		}
		
	}
	
	 
}
