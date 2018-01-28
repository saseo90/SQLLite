package com.cmm.utility.impl;

/**
 * 
 * 사용자 가 화면에서 로그인하기 위해 파라미터 전달용으로 사용하는 마스터 객체
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
public class DefaultLoginVo extends UserInfo {
    
    /** DB 접속자 아이디 */private String accessId=null;
    /** DB 접속자 PW */private String accessPw=null; 
    /** DB 접속자 ip */private String accessIp=null;
    /** DB 접속자 포트 */private String accessPort=null;
    /** DB 접속 SID */private String accessSID=null;
    /** DB 접속 dbName */private String accessDBName=null;
    /** 접속자 driverUrl */private String driverUrl=null;
    /** DB 고유값 */private String dbEnum=null;
    /** DB종류 :: ORACLE, MYSQL, MSSQL */private String dbType=null;
    /** DB권한 :: 관리자, 운영자, 파일관리자 등 다양하다. 픽스필요하지만 DB마다 다르기 때문에 고민필요함. */
    private String dbGrant=null;
    public String getAccessId() {
        return accessId;
    }
    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }
    public String getAccessPw() {
        return accessPw;
    }
    public void setAccessPw(String accessPw) {
        this.accessPw = accessPw;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public String getAccessPort() {
        return accessPort;
    }
    public void setAccessPort(String accessPort) {
        this.accessPort = accessPort;
    }
    public String getAccessSID() {
        return accessSID;
    }
    public void setAccessSID(String accessSID) {
        this.accessSID = accessSID;
    }
    public String getAccessDBName() {
        return accessDBName;
    }
    public void setAccessDBName(String accessDBName) {
        this.accessDBName = accessDBName;
    }
    public String getDriverUrl() {
        return driverUrl;
    }
    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }
    public String getDbEnum() {
        return dbEnum;
    }
    public void setDbEnum(String dbEnum) {
        this.dbEnum = dbEnum;
    }
    public String getDbType() {
        return dbType;
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    public String getDbGrant() {
        return dbGrant;
    }
    public void setDbGrant(String dbGrant) {
        this.dbGrant = dbGrant;
    }
    @Override
    public String toString() {
        return "DefaultLoginVo [accessId=" + accessId + ", accessPw=" + accessPw + ", accessIp=" + accessIp
                + ", accessPort=" + accessPort + ", accessSID=" + accessSID + ", accessDBName=" + accessDBName
                + ", driverUrl=" + driverUrl + ", dbEnum=" + dbEnum + ", dbType=" + dbType + ", dbGrant=" + dbGrant
                + ", getAccessId()=" + getAccessId() + ", getAccessPw()=" + getAccessPw() + ", getAccessIp()="
                + getAccessIp() + ", getAccessPort()=" + getAccessPort() + ", getAccessSID()=" + getAccessSID()
                + ", getAccessDBName()=" + getAccessDBName() + ", getDriverUrl()=" + getDriverUrl() + ", getDbEnum()="
                + getDbEnum() + ", getDbType()=" + getDbType() + ", getDbGrant()=" + getDbGrant() + ", getUserId()="
                + getUserId() + ", getRowNum()=" + getRowNum() + ", getUserPw()=" + getUserPw() + ", getUsrType()="
                + getUsrType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}
