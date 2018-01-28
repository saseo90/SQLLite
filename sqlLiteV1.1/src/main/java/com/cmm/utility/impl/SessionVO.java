package com.cmm.utility.impl;

import java.util.ArrayList;

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
        for (Object dbObjt : dbObjtList) {
            this.dbObjList.add(dbObjt);
        }       
    }
    private void initDBObjList() {
        if(this.dbObjList==null){
            this.dbObjList = new ArrayList<Object>();
        }
    }

}
