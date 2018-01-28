package com.cmm.utility.impl;
/**
 * 
 * 사용자 INFOMATION OBJECT
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
public class UserInfo {
    /** 레코드 고유값 ::순번호 */private String rowNum=null;
    /** 사용자 고유값 ::아이디 */private String userId=null;
    /** 사용자 비밀번호 ::인코딩 예정*/private String userPw=null;
    /** 사용자 계정 타입 ::ADMIN,USR*/private String userType=null;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRowNum() {
        return rowNum;
    }
    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }
    public String getUserPw() {
        return userPw;
    }
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
    public String getUsrType() {
        return userType;
    }
    public void setUsrType(String userType) {
        this.userType = userType;
    }
    
}
