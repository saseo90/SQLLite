package com.cmm.utility;

import java.util.ArrayList;
import java.util.List;

import com.cmm.utility.impl.DefaultLoginVo;
import com.cmm.utility.impl.SessionVO;

import app.sqllite.database.msssql.service.impl.MsSQLVO;
import app.sqllite.database.mysql.service.impl.MySQLVO;
import app.sqllite.database.oracle.service.impl.OracleVO;

/**
 * 
 * VIEW OBJECT 의 형변환 유틸
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
public class ComvertUtil {

    /**
     * 문자열을 숫자형으로 번환할 수 있는지 검사한다.
     * 정수형과 실수형일 경루 true
     * xml 생성시 totalcount 형변환 할 떄 사용한다.
     * @param str 검사대상 문자열
     * @return
     */
    public static boolean isStringDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 세션 VO 객체를  화면에 출력한 VO 로 변환한다.
     * @param sessionList
     * @return
     */
    public static List<DefaultLoginVo> comvertFormater(List<SessionVO> sessionList) {
        List<DefaultLoginVo> result = new ArrayList<DefaultLoginVo>();
        DefaultLoginVo temp = null;
        for (SessionVO sessionVO : sessionList) {
            String rowNum = sessionVO.getRowNum();
            String userId = sessionVO.getUserId();
            String userPw = sessionVO.getUserPw();
            String userType= sessionVO.getUsrType();
            ArrayList<Object> dbObjList = sessionVO.getDbObjList();
            for (Object dbObjt : dbObjList) {
                String driverUrl =null;
                String accessId =null;
                String accessPw =null;
                String accessIp =null;
                String accessPort =null;
                String accessSID =null;
                String accessDBName =null;
                String dbEnum =null;
                String dbType =null;
                if(dbObjt instanceof OracleVO){
                    OracleVO oraVo = (OracleVO) dbObjt;
                    driverUrl =oraVo.getDriverUrl();
                    accessId =oraVo.getAccessId();
                    accessPw =oraVo.getAccessPw();
                    accessIp =oraVo.getAccessIp();
                    accessPort =oraVo.getAccessPort();
                    accessSID =oraVo.getAccessSID();
//                    accessDBName =oraVo.getDriverName();
                    dbEnum =oraVo.getDbEnum();
                    dbType =oraVo.getDbType();
                }else if(dbObjt instanceof MsSQLVO){
                    MsSQLVO msVo = (MsSQLVO) dbObjt;
                    driverUrl =msVo.getDriverUrl();
                    accessId =msVo.getAccessId();
                    accessPw =msVo.getAccessPw();
                    accessIp =msVo.getAccessIp();
                    accessPort =msVo.getAccessPort();
//                    accessSID =msVo.getAccessSID();
                    accessDBName =msVo.getDriverName();
                    dbEnum =msVo.getDbEnum();
                    dbType =msVo.getDbType();
                }else if(dbObjt instanceof MySQLVO){
                    MySQLVO myVo = (MySQLVO) dbObjt;
                    driverUrl =myVo.getDriverUrl();
                    accessId =myVo.getAccessId();
                    accessPw =myVo.getAccessPw();
                    accessIp =myVo.getAccessIp();
                    accessPort =myVo.getAccessPort();
//                    accessSID =myVo.getAccessSID();
                    accessDBName =myVo.getDriverName();
                    dbEnum =myVo.getDbEnum();
                    dbType =myVo.getDbType();
                }
                temp = new DefaultLoginVo();
                temp.setRowNum(rowNum);
                temp.setUserId(userId);
                temp.setUserPw(userPw);
                temp.setUsrType(userType);
                temp.setDriverUrl(driverUrl);
                temp.setAccessId(accessId);
                temp.setAccessPw(accessPw);
                temp.setAccessIp(accessIp);
                temp.setAccessPort(accessPort);
                temp.setAccessSID(accessSID);
                temp.setAccessDBName(accessDBName);
                temp.setDbEnum(dbEnum);
                temp.setDbType(dbType);
                temp.setDriverUrl(driverUrl);
                result.add(temp);
            }
        }
        return result;
    }
}
