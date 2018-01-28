package app.sqllite.database.mysql.service.impl;
/**
 * 
 * MySql VIEW OBJECT 
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
public class MySQLVO extends MySQLInfo {
    /** MySQL 드리아버 */
    private String driverName = "org.gjt.mm.mysql.Driver";
    /** 접속자 driverUrl "jdbc:mysql://[IP]:[Port]/[DB이름] */
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
    public String getDriverUrl(String accessIp, String accessPort, String accessDBName) {
        return this.driverUrl = super.makeDriverURL(accessIp, accessPort, accessDBName);
    }
    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }
    
    
    
}
