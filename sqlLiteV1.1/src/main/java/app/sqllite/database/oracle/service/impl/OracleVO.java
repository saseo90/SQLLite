package app.sqllite.database.oracle.service.impl;

/**
 * 
 * 오라클 데이터베이스 접근 VIEW OBJECT
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
public class OracleVO extends OracleInfo  {
    /** 오라클 드리아버 */
    private String driverName = "oracle.jdbc.driver.OracleDriver";
    /** 접속자 driverUrl "jdbc:oracle:thin:@[IP]:[Port]/[SID] */
    private String driverUrl;
    
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }   
    public String getDriverUrl() {
        return this.driverUrl = super.makeDriverURL();
    }
    public String getDriverUrl(String accessIp, String accessPort, String accessSID) {
        return this.driverUrl = super.makeDriverURL(accessIp, accessPort, accessSID);
    }
    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }
}
