package com.cmm.utility.impl;

import java.util.ArrayList;
import java.util.Iterator;

import app.sqllite.database.msssql.service.impl.MsSQLVO;
import app.sqllite.database.mysql.service.impl.MySQLVO;
import app.sqllite.database.oracle.service.impl.OracleVO;

/**
 * 
 * 사용자 세션정보
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
public class SessionVO extends UserInfo {
    
    private static final long serialVsessionUID = -655155667987646465L;
    
    /** DB 접속 정보 */
    private ArrayList<Object> dbObjList=null;
    
    public ArrayList<Object> getDbObjList() {
        return dbObjList;
    }
    public void setDbObjList(ArrayList<Object> dbObjList) {
        this.dbObjList = dbObjList;
    }
    public void setDbObjListAdd(Object dbObjt) {
        initDBObjList();
        this.dbObjList.add(dbObjt);
    }
    public void setDbObjListAddList(ArrayList<Object> dbObjtList) {
        initDBObjList();
        this.dbObjList.addAll(dbObjtList);
    }
    private void initDBObjList() {
        if(this.dbObjList==null){
            this.dbObjList = new ArrayList<Object>();
        }
    }
    
    public int getDBObjtCount() {
        if(dbObjList==null){
            return 0;
        }else{
            return dbObjList.size(); 
        }
    }

    public Boolean delDBObjt(String dbEnum) {
        Boolean result = true;
        if(dbObjList==null){
            return result;
        }
        Iterator<Object> itr = dbObjList.iterator();  
        while (itr.hasNext()) {
            Object user = itr.next();
            if(user instanceof OracleVO){
                OracleVO oraVO = (OracleVO) user;
                if(oraVO.getDbEnum().equals(dbEnum)){
                    itr.remove();
                    return result;
                }
            }else if(user instanceof MsSQLVO){
                MsSQLVO mssVO = (MsSQLVO) user;
                if(mssVO.getDbEnum().equals(dbEnum)){
                    itr.remove();
                    return result;
                }
            }else if(user instanceof MySQLVO){
                MySQLVO mysVO = (MySQLVO) user;
                if(mysVO.getDbEnum().equals(dbEnum)){
                    itr.remove();
                    return result;
                }
            }
        }
        return false; 
    }
    
    
}
