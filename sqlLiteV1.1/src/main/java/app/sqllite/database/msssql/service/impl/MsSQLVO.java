package app.sqllite.database.msssql.service.impl;
/**
 * 
 * MsSql VIEW OBJECT 
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
public class MsSQLVO extends MsSQLInfo {
    /** MSSQL 드리아버 */
    private String driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    /** 접속자 driverUrl "jdbc:Microsoft:sqlserver://[IP]:[Port];databasename=[DB이름] */
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
