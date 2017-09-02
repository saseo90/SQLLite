package lite.sql.backdoor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import lite.sql.frontdoor.loginPage.loginList.LoginUser;
import lite.sql.frontdoor.main.ColType;

public interface DatabaseDao {
	
    public String connection(String url, String user, String pwd);
    
    public Connection getConnection();
    
    public QueryResultVo checkQuery(String query);
    
    public QueryResultVo select(String query, QueryResultVo vo);
    
    public QueryResultVo update(String query, QueryResultVo vo);
    
    public QueryResultVo delete(String query, QueryResultVo vo);
    
    public QueryResultVo insert(String query, QueryResultVo vo);
    
    public QueryResultVo create(String query, QueryResultVo vo);
    
    public QueryResultVo alter(String query, QueryResultVo vo);
    
    public QueryResultVo ect(String query, QueryResultVo vo);
	
    public List<List<String>> getData(String tableName);

	List<String> tableList();

	List<String> collist(String tableName);

	List<ColType> coltype(String colName);
	
	public QueryResultVo modifyData(List<Map> list, String tableName);
	
	public String killSession(LoginUser user);
	
	public List<List<String>> getSession();
	
	public boolean checkAuthority();
	
	public List<List<String>> getQuery();
    
}
