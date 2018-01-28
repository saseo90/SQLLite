package com.cmm.utility;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmm.utility.impl.SessionVO;

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
    public static void afterConnectionAddSession(SessionVO sessionVO) throws Exception{
        Boolean flag = true;
        for (SessionVO user : users) {//기존 정보에 DB 정보 추가
            if(user.getUserId().equals(sessionVO.getUserId())){
                user.setUserPw(sessionVO.getUserPw());
                user.setUsrType(sessionVO.getUsrType());
                user.setDbObjListAddList(sessionVO.getDbObjList());
                log.debug("[알람]["+sessionVO.getUserId()+"]의 접속성공\n");
                flag = false;
            }
        }
        if(flag){//기존 정보가 없는 경우 신규추가
            users.add(sessionVO);
            log.debug("[알람]["+sessionVO.getUserId()+"]의 접속성공\n");
        }
    }
    /** 
     * 접속 종료 후 동작하는 메서드이다. 사용자 유틸에 사용자정보를 제거한다.
     * @param userId 사용자의 고유값
     * @param objt DBVO의 고유값
     */
    public static void afterConnectionCloseDelSessionALL(String userId, long objt) {
        for (SessionVO user : users) {
            if(user.getUserId().equals(userId)){
                log.debug("[알람]["+userId+"]의 접속종료 성공\n");
                return;
            }
        }
    }
    /** 
     * 접속 종료 후 동작하는 메서드이다. 사용자 유틸에 사용자정보를 제거한다.
     * @param userId 사용자의 고유값
     */
    public static void afterConnectionClosed(String userId) {
        for (SessionVO user : users) {
            if(user.getUserId().equals(userId)){//해당 아이디의 모든 세션 종료
                users.remove(user);
                log.debug("[알람]["+userId+"]의 접속종료 성공\n");
            }
        }
    }
    /**
     * 특정 사용자의 모든 세션정보
     * @param userId 사용자 고유값
     * @return ArrayList<SessionVO> 사용자의 모든 세션
     */
    public ArrayList<SessionVO> getSessionALL(String userId) {
        ArrayList<SessionVO> result = new ArrayList<SessionVO>();
        for (SessionVO user : users) {
            if(user.getUserId().equals(userId)){//해당 아이디의 모든 세션 검색
                result.add(user);
            }
        }
        return result;
    }
    /**
     * 특정 사용자의 특정한 DB 세션정보
     * @param userId 사용자 고유값
     * @param accessId DB 고유값 
     * @return SessionVO 사용자의 특정DB 세션
     */
    public ArrayList<SessionVO> getSession(String userId, String accessId) {
        ArrayList<SessionVO> result = new ArrayList<SessionVO>();
        for (SessionVO user : users) {
            if(user.getUserId().equals(userId)&&user.getUserId().equals(accessId)){//해당 아이디의 특정 DB 세션 검색
                result.add(user);
                return result;
            }
        }
        return result;       
    }
    /**
     * 특정 사용자의 세션정보 체크 :: 로그인 여부 확인
     * @param userId 사용자 고유값
     * @return boolean 
     */
    public boolean chkeSessionUSR(String userId) {
        boolean result = true;
        if(getSessionALL(userId).size()<1){
            result=false;
        }
        return result;
    }
    /**
     * 특정 사용자의 특정 DB 세션정보 체크 :: 로그인 후 DB 접속 확인
     * @param userId 사용자 고유값
     * @param dbId DB 고유값 
     * @return boolean
     */
    public boolean chkeSessionUSRDB(String userId, String dbId) {
        boolean result = true;
        if(getSession(userId,dbId).size()<1){
            result=false;
        }
        return result;
    }

}