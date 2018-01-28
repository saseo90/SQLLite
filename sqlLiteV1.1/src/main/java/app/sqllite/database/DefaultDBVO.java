package app.sqllite.database;

import java.sql.Connection;

/**
 * 
 *  데이터베이스 접근 Default VIEW OBJECT
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
public class DefaultDBVO {
    /** DB 고유값 */
    private String dbEnum;
    /** DB종류 :: ORACLE, MYSQL, MSSQL */
    private String dbType;
    /** DB 커넥션 객체 */
    private Connection connection;

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
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
