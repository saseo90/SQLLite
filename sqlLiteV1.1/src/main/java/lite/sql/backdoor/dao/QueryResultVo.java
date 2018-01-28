package lite.sql.backdoor.dao;

import java.sql.ResultSet;
import java.sql.Statement;

public class QueryResultVo {
	
	private ResultSet selectResultSet;
	private int update;
	private int delete;
	private int insert;
	private int create;
	private int modify;
	private int drop;
	private int alter;
	private String error;
	private ResultSet ectResultSet;
	private Statement statement;
	
	
	public ResultSet getSelectResultSet() {
		return selectResultSet;
	}
	public void setSelectResultSet(ResultSet selectResultSet) {
		this.selectResultSet = selectResultSet;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ResultSet getEctResultSet() {
		return ectResultSet;
	}
	public void setEctResultSet(ResultSet ectResultSet) {
		this.ectResultSet = ectResultSet;
	}
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public int getInsert() {
		return insert;
	}
	public void setInsert(int insert) {
		this.insert = insert;
	}
	public int getCreate() {
		return create;
	}
	public void setCreate(int create) {
		this.create = create;
	}
	public int getModify() {
		return modify;
	}
	public void setModify(int modify) {
		this.modify = modify;
	}
	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	public int getDrop() {
		return drop;
	}
	public void setDrop(int drop) {
		this.drop = drop;
	}
	public int getAlter() {
		return alter;
	}
	public void setAlter(int alter) {
		this.alter = alter;
	}

	
}
