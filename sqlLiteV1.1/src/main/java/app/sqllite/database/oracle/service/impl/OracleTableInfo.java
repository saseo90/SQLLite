package app.sqllite.database.oracle.service.impl;

import app.sqllite.database.DefaultTableInfo;
/**
 * 오라클 톄이블 기본정보 객체 
 * --테이블의 구조에 대한 정보 및 컬럼의 구성에 대한 정보를 담는 클래스이다.
 * --이전 버전에서 ColType 클래스로 쓰였던 부분인다.
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
public class OracleTableInfo extends DefaultTableInfo{
    private String column_name;
    private String DATE_TYPE;
    private String NULLABLE;
    private String DATE_DEFAULT;
    private String COLUMN_ID;
    private String COMMENTS;
    
    public String getColumn_name() {
        return column_name;
    }
    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }
    public String getDATE_TYPE() {
        return DATE_TYPE;
    }
    public void setDATE_TYPE(String dATE_TYPE) {
        DATE_TYPE = dATE_TYPE;
    }
    public String getNULLABLE() {
        return NULLABLE;
    }
    public void setNULLABLE(String nULLABLE) {
        NULLABLE = nULLABLE;
    }
    public String getDATE_DEFAULT() {
        return DATE_DEFAULT;
    }
    public void setDATE_DEFAULT(String dATE_DEFAULT) {
        DATE_DEFAULT = dATE_DEFAULT;
    }
    public String getCOLUMN_ID() {
        return COLUMN_ID;
    }
    public void setCOLUMN_ID(String cOLUMN_ID) {
        COLUMN_ID = cOLUMN_ID;
    }
    public String getCOMMENTS() {
        return COMMENTS;
    }
    public void setCOMMENTS(String cOMMENTS) {
        COMMENTS = cOMMENTS;
    }
    @Override
    public String toString() {
        return "OracleTableInfo [column_name=" + column_name + ", DATE_TYPE=" + DATE_TYPE + ", NULLABLE=" + NULLABLE
                + ", DATE_DEFAULT=" + DATE_DEFAULT + ", COLUMN_ID=" + COLUMN_ID + ", COMMENTS=" + COMMENTS + "]";
    }
}
