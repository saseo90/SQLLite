package com.cmm.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmm.utility.impl.SessionVO;

import app.sqllite.database.msssql.service.impl.MsSQLVO;
import app.sqllite.database.mysql.service.impl.MySQLVO;
import app.sqllite.database.oracle.service.impl.OracleVO;

/**
 * 
 * 다중 사용자 처리를 위한 세션유틸 구현 클래스
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
public class SessionUtil {
//    private  Logger log = Logger.getLogger(this.getClass());
    private static Logger log = LoggerFactory.getLogger(SessionUtil.class);
    private static ArrayList<SessionVO> users = new ArrayList<SessionVO>();
    private static int userRowNum = 0;
    private static int userDBEnum = 0;
    
    /**
     * 유저고유번호생성
     * --빠른사용자정보 생성, 접속성공 시 사용한다.
     * @return
     */
    public static String getUserRowNum() {
        ++userRowNum;
        return String.format("UID%05d",userRowNum);        
    }
    /**
     * DB고유번호생성
     * --초기값을 난수로 설정하고 이후에 호출 시 1증가한다.
     * --호출은 LoginController 에서 빠른사용자 정보 추가/수정/삭제 시 사용한다. 
     * @return
     */
    public static String getUserDBEnum() {
        if(userDBEnum==0){
            Random rd = new Random();
            userDBEnum=(int)rd.nextInt(99999);
        }else{
            ++userDBEnum;
        }
        return String.format("DID%05d",userDBEnum);        
    }
    
    /**
     * 접속 성공 후 동작하는 메서드이다. 사용자 유틸에 사용자정보 추가한다.
     * @param sessionVO
     * @throws Exception
     */
    public static void connectionAdd(SessionVO sessionVO) throws Exception{
        Boolean flag = true;
        for (SessionVO user : users) {//기존 정보에 DB 정보 추가
            if(user.getRowNum().equals(sessionVO.getRowNum())){
                user.setUserId(sessionVO.getUserId());
                user.setUserPw(sessionVO.getUserPw());
                user.setUsrType(sessionVO.getUsrType());
                user.setDbObjListAddList(sessionVO.getDbObjList());
                log.debug("[알람]["+sessionVO.getUserId()+"]의 접속성공\n");
                flag = false;
                break;
            }
        }
        if(flag){//기존 정보가 없는 경우 신규추가
            users.add(sessionVO);
            log.debug("[알람]["+sessionVO.getUserId()+"]의 접속성공\n");
        }
    }
    /** 
     * 특정 사용자의 모든 새션종료
     * 접속 종료 후 동작하는 메서드이다. 사용자 유틸에 사용자정보를 제거한다.
     * @param rowNum 사용자의 고유값
     */
    public static void disconnectionClosedALL(String rowNum) {
        Iterator<SessionVO> itr = users.iterator(); 
        SessionVO user =null;
        while (itr.hasNext()) {
            user = itr.next();
            if(user.getRowNum().equals(rowNum)){
                itr.remove();
                log.debug("[알람]["+user.getUserId()+"]의 접속종료 성공\n");
                return;
            }
        }
        log.debug("[알람]["+rowNum+"]의 접속종료 실패\n");
    }
    /** 
     * 특정 사용자의 특정 DB 세션만 종료한다.
     * DB세션이 남아있지 않으면 모든세션을 종료한다.
     * 접속 종료 후 동작하는 메서드이다. 사용자 유틸에 사용자정보를 제거한다.
     * @param rowNum 사용자 고유값
     * @param dbEnum DB 고유값 
     */
    public static void disconnectionClosed(String rowNum, String dbEnum) {
        Iterator<SessionVO> itr = users.iterator();
        SessionVO user=null;
        while (itr.hasNext()) {
            user = itr.next();
            if(user.getRowNum().equals(rowNum)){
                int count = user.getDBObjtCount();
                String userName =user.getUserId();
                if(1<count){
                    if(!user.delDBObjt(dbEnum)){
                        log.debug("[알람]["+userName+"]의 접속종료 실패\n");
                        return;
                    }
                }else{//DB 1 개이하일 떄
                    itr.remove();
                }
                log.debug("[알람]["+userName+"]의 접속종료 성공\n");
                return;
            }
        }
    }
    
    /**
     * 특정 사용자의 모든 세션정보
     * @param rowNum 사용자 고유값
     * @return SessionVO 사용자의 모든 세션
     */
    public SessionVO getSession(String rowNum) {
        for (SessionVO user : users) {
            if(rowNum.equals(user.getRowNum())){
                return user;
            }
        }
        return null;
    }
    /**
     * 특정 사용자의 특정 DB 세션정보
     * @param rowNum 사용자 고유값
     * @param dbEnum DB 고유값 
     * @return
     */
    public Object getSessionDBALL(String rowNum, String dbEnum) {
        for (SessionVO user : users) {
            if(rowNum.equals(user.getRowNum())){
                ArrayList<Object> temp =  user.getDbObjList();
                if(temp == null)return null;
                for (Object object : temp) {
                    if(object instanceof OracleVO){
                        OracleVO oraVO = (OracleVO) object;
                        if(oraVO.getDbEnum().equals(dbEnum)){
                            return object;
                        }
                    }else if(object instanceof MsSQLVO){
                        MsSQLVO mssLVO = (MsSQLVO) object;
                        if(mssLVO.getDbEnum().equals(dbEnum)){
                            return object;
                        }
                    }else if(object instanceof MySQLVO){
                        MySQLVO mysVO = (MySQLVO) object;
                        if(mysVO.getDbEnum().equals(dbEnum)){
                            return object;
                        }
                    }
                }
                break;
            }
        }
        return null;
    }

    /**
     * 특정 사용자의 세션정보 체크 :: 로그인 여부 확인
     * @param rowNum 사용자 고유값
     * @return boolean 
     */
    public boolean chkeSessionUSR(String rowNum) {
        if(getSession(rowNum)==null){
            return false;
        }
        return true;
    }
    /**
     * 특정 사용자의 특정 DB 세션정보 체크 :: 로그인 후 DB 접속 확인
     * @param rowNum 사용자 고유값
     * @param dbEnum DB 고유값 
     * @return boolean
     */
    public boolean chkeSessionUSRDB(String rowNum, String dbEnum) {
        if(getSessionDBALL(rowNum,dbEnum)==null){
            return false;
        }
        return true;
    }

}