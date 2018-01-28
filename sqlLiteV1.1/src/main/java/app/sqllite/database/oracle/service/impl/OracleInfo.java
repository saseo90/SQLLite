package app.sqllite.database.oracle.service.impl;

import app.sqllite.database.DefaultDBVO;

/**
 * 
 * 오라클 데이터베이스 INFOMATION OBJECT
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
public class OracleInfo extends DefaultDBVO {
    
    /** DB 접속자 아이디 */
    private String accessId;
    /** DB 접속자 PW */
    private String accessPw; 
    /** DB 접속자 ip */
    private String accessIp;
    /** DB 접속자 포트 */
    private String accessPort;
    /** DB 접속 SID */
    private String accessSID;
    
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
    protected String makeDriverURL() {
        return makeDriverURL(this.accessIp, this.accessPort, this.accessSID);
    }
    protected String makeDriverURL(String accessIp, String accessPort, String accessSID) {
        this.accessIp=accessIp; this.accessPort=accessPort; this.accessSID=accessSID;
        return "jdbc:oracle:thin:@"+accessIp+":"+accessPort+"/"+accessSID;
    }
}
