package app.sqllite.database;

import java.sql.Connection;

/**
 * 
 * 데이터베이스 접근 기본 인터페이스
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
public interface DAO {
    /** 접속준비 */
    public void setup() throws Exception;
    /** 접속
     * @param driverURL
     * @param accessId
     * @param pwd
     * @throws Exception
     */
    public Connection connect(String driverURL, String accessId, String pwd) throws Exception;
    /** 접속해제 */
    public void close() throws Exception;
}
