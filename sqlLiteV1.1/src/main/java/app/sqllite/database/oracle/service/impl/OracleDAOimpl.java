package app.sqllite.database.oracle.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.dbutils.QueryRunner;

import app.sqllite.database.oracle.service.OracleDAO;

/**
 * 
 * 오라클 데이터베이스 접근 기본 구현 클래스
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
public class OracleDAOimpl implements OracleDAO {
    
    private Connection connection=null;
    private Statement statement=null;
    private QueryRunner dbAccess = new QueryRunner();
    
//    private StringBuilder sb;
//    private Statement statement;
//    private PreparedStatement psmt = null;
//    private ResultSet resultSet;

    @Override
    public void setup() throws Exception {
        OracleVO oraVO = new OracleVO();
        connection = DriverManager.getConnection(oraVO.getDriverUrl());
//        connection = DriverManager.getConnection("jdbc:derby:books.db;create=true");
    }

    public Connection connectTest(String driverURL, String accessId, String pwd) throws Exception {
        OracleVO oraVO = new OracleVO();
        Class.forName(oraVO.getDriverName());
        connection = DriverManager.getConnection(driverURL, accessId, pwd);
//        statement = connection.createStatement();
        return connection;
  }
    
    @Override
    public Connection connect(String driverURL, String accessId, String pwd) throws Exception {
        OracleVO oraVO = new OracleVO();
        Class.forName(oraVO.getDriverName());
         connection = DriverManager.getConnection(driverURL, accessId, pwd);
         return connection;
//        statement = connection.createStatement();
//        Connect.connection = connection;
//        System.out.println("success!");
    }
    

    @Override
    public void close() throws Exception {
        connection.close();
//        DriverManager.getConnection("jdbc:derby:books.db;shutdown=true");
    }

}
