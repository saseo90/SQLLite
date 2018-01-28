package lite.sql.backdoor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.frontdoor.loginPage.loginList.LoginUser;
import lite.sql.frontdoor.main.ColType;
import lite.sql.utility.Regular;

public class OracleDaoImpl implements DatabaseDao {

	Connection conn;
	StringBuilder sb;
	Statement statement;
	PreparedStatement psmt = null;
	ResultSet resultSet;

	public Connection getConnection() {
		return conn;
	}
	
	public void closeStatement() {
		try {
			
			if (statement != null) {
				// statement 닫기
				statement.close();
			}
		} catch (SQLException e) {
			System.out.println("[닫기 오류]\n" + e.getStackTrace());
			e.getStackTrace();
		}
	}

	@Override
	public String connection(String url, String user, String pwd) {
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver";			
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pwd);
			statement = conn.createStatement();
			Connect.connection = conn;
			System.out.println("success!");
		} catch (Exception e) {
			return e.toString();
		}
		return "success!";
	}
	
	
	@Override
	public QueryResultVo checkQuery(String query) {
		QueryResultVo vo = new QueryResultVo();
		String check;
		String[] input = query.split(";");
		Matcher matcher;
		Pattern p;
		int index = 0;
		for (String txt : input) {
			check = txt.toUpperCase().trim();
			p = Pattern.compile(Regular.regexUpdate);
			matcher = p.matcher(check);

			if (matcher.matches()) {
				vo = update(input[index], vo);
				index++;
				continue;
			}

			p = Pattern.compile(Regular.regexDelete);
			matcher = p.matcher(check);

			if (matcher.matches()) {
				vo = delete(input[index], vo);
				index++;
				continue;
			}

			p = Pattern.compile(Regular.regexInsert);
			matcher = p.matcher(check);

			if (matcher.matches()) {
				vo = insert(input[index], vo);
				index++;
				continue;
			}

			p = Pattern.compile(Regular.regexSelect);
			matcher = p.matcher(check);

			if (matcher.matches()) {
				vo = select(input[index], vo);
				index++;
				continue;
			}
			
			p = Pattern.compile(Regular.regexAlter);
			matcher = p.matcher(check);
			
			if (matcher.matches()) {
				vo = alter(input[index], vo);
				index++;
				continue;
			}

			p = Pattern.compile(Regular.regexCreate);
			matcher = p.matcher(check);

			if (matcher.matches()) {
				vo = create(input[index], vo);
				index++;
				continue;
			}else{
				vo = ect(input[index], vo);
				index++;
				continue;
			}
			
			

		}
		return vo;

	}
	public QueryResultVo ect(String query, QueryResultVo vo) {
		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(query);
			vo.setEctResultSet(resultSet);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
			
		}finally{
			vo.setStatement(statement);
		}
		
		return vo;
	}

	public List<List<String>> getSession() {
	    List<List<String>> result = new ArrayList<List<String>>();
		try {
			statement = Connect.connection.createStatement();
			resultSet =statement.executeQuery("SELECT SID as sid ,SERIAL# as sn , "
					+ "utl_inaddr.get_host_address(substr(machine,instr(machine,'\')+1)) as ip"
					+" , machine , program FROM v$session where USERNAME not like  ' '");
					
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
//			System.out.println(resultSetMetaData.getColumnCount());
			//컬럼값 읽어오기
		 
			while (resultSet.next()) {
				List<String> sesslist = new ArrayList<String>();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					// 컬럼값 한개씩 가져오기
				    
					sesslist.add(resultSet.getString(resultSetMetaData.getColumnName(i)));
					
				}
				result.add(sesslist);
//				System.out.println();
			}
		
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()+e.getMessage());
		}finally{
			closeStatement();
		}
		return result;
	}
	
	@Override
	public QueryResultVo select(String query, QueryResultVo vo) {
		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			vo.setSelectResultSet(resultSet);
			
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			vo.setStatement(statement);
		}
		
		return vo;
	}
	

	@Override
	public QueryResultVo update(String query, QueryResultVo vo) {
		int result = 0;
		try {
			statement = Connect.connection.createStatement();
			result = statement.executeUpdate(query);
			if (result > 0) {
				System.out.println(result + " 행 이(가) 업데이트되었습니다.");
			}
			vo.setUpdate(vo.getUpdate()+result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		
		return vo;

	}
	
	@Override
	public QueryResultVo alter(String query, QueryResultVo vo) {
		int result = 0;
		try {
			statement = Connect.connection.createStatement();
			ResultSet re = statement.executeQuery(query);
			if (re != null) {
				result++;
				System.out.println(result + " 행 이(가) 변경되었습니다.");
			}
			vo.setAlter(vo.getAlter()+result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		
		return vo;
		
	}

	@Override
	public QueryResultVo delete(String query, QueryResultVo vo) {
		int result = 0;
		try {
			statement = Connect.connection.createStatement();
			result = statement.executeUpdate(query);
			if (result > 0) {
				System.out.println(result + " 행 이(가) 삭제되었습니다.");
			}
			vo.setDelete(vo.getDelete()+result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		
		return vo;
	}

	@Override
	public QueryResultVo insert(String query, QueryResultVo vo) {
		int result = 0;
		try {
			statement = Connect.connection.createStatement();
			result = statement.executeUpdate(query);
			if (result > 0) {
				System.out.println(result + " 행 이(가) 추가되었습니다.");
			}
			vo.setInsert(vo.getInsert()+result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		
		return vo;
	}

	@Override
	public QueryResultVo create(String query, QueryResultVo vo) {
		int result = 0;
		try {
			statement = Connect.connection.createStatement();
			ResultSet re = statement.executeQuery(query);
			if (re != null) {
				System.out.println("테이블이 정상적으로 생성되었습니다.");
				result++;
			}
			vo.setCreate(vo.getCreate()+result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		return vo;
	}

	@Override
	public List<List<String>> getData(String tableName) {
		String get = "select rowid, a.* from " + tableName + " a";
		// String get = "select rowid, a.* from MEMBER a";
		Matcher matcher;
		Pattern p;
		p = Pattern.compile(Regular.regexDate);
		
		List<List<String>> list = new ArrayList<List<String>>();

		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(get);

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			List<String> row = new ArrayList<String>();
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				row.add(resultSetMetaData.getColumnName(i));
			}
				list.add(row);
				
			while (resultSet.next()) {
				row = new ArrayList<String>();
				for (int j = 1; j <= resultSetMetaData.getColumnCount(); j++) {
					String text = resultSet.getString(resultSetMetaData
							.getColumnName(j));
					if(text != null){
						matcher = p.matcher(text);
						if (matcher.matches()) {
							text = text.substring(0, 10);
						}else{
						}
					}
					
					row.add( text != null ? text.trim() : "null");
				}

				list.add(row);

			}

		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + e.getMessage());
			list = null;
			return list;
		}finally{
			closeStatement();
		}

		return list;
	}

	@Override
	public List<String> tableList() {
		List<String> list = new ArrayList<String>();
		try {
			statement = Connect.connection.createStatement();
			System.out.println(statement.getConnection().toString());
			resultSet = statement.executeQuery("select table_name from tabs");

			// 데이터 베이스 내에 있는 컬럼 리스트 가져오기
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				list.add(resultSet
						.getString(resultSetMetaData.getColumnName(1))
						.toString());
			}
		} catch (Exception e) {
			System.out.println("테이블 에러");
			System.out.println(e.getStackTrace());
		} finally {
			closeStatement();
		}
		return list;
	}

	@Override
	public List<String> collist(String tableName) {
		List<String> collist = new ArrayList<String>();
		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery("select * from " + tableName);

			// 데이터 베이스 내에 있는 컬럼 리스트 가져오기
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			// 칼럼의 개수 알기
//			System.out.println(resultSetMetaData.getColumnCount());

			// 컬럼명 가져오기
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				// 컬럼 리스트 출력
				collist.add(resultSetMetaData.getColumnName(i));
			}

		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println("컬럼 에러");
		}finally{
			closeStatement();
		}
		return collist;
	}

	@Override
	public List<ColType> coltype(String colName) {

		List<ColType> collist = new ArrayList<ColType>();
		try {
			statement = Connect.connection.createStatement();
			String qu = "SELECT USER_COL_COMMENTS.COLUMN_NAME,"
					+ "USER_TAB_COLUMNS.DATA_TYPE,"
					+ "USER_TAB_COLUMNS.NULLABLE,"
					+ "USER_TAB_COLUMNS.DATA_DEFAULT,"
					+ "USER_TAB_COLUMNS.COLUMN_ID,"
					+ "USER_COL_COMMENTS.COMMENTS "
					+ "from USER_TAB_COLUMNS,USER_COL_COMMENTS "
					+ "where USER_TAB_COLUMNS.TABLE_NAME = '"
					+ colName
					+ "' AND USER_TAB_COLUMNS.TABLE_NAME=USER_COL_COMMENTS.TABLE_NAME AND USER_TAB_COLUMNS.COLUMN_NAME=USER_COL_COMMENTS.COLUMN_NAME";

			resultSet = statement.executeQuery(qu);
			// 데이터 베이스 내에 있는 컬럼 리스트 가져오기
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			// 칼럼의 개수 알기
//			System.out.println(resultSetMetaData.getColumnCount());
			while (resultSet.next()) {
				String typeNo4 = resultSet.getString(resultSetMetaData.getColumnName(4));
				String typeNo6 = resultSet.getString(resultSetMetaData.getColumnName(6));
				
				collist.add(new ColType(
						resultSet.getString(resultSetMetaData.getColumnName(1)),
						resultSet.getString(resultSetMetaData.getColumnName(2)),
						resultSet.getString(resultSetMetaData.getColumnName(3)),
						typeNo4 != null ? typeNo4 : "NULL",
						resultSet.getString(resultSetMetaData.getColumnName(5)),
						typeNo6 != null ? typeNo6 : "NULL"));
			}
		} catch (SQLException e) {
			System.out.printf("에러 :");
			System.out.println(e.getErrorCode() + e.getMessage());
		}finally{
			closeStatement();
		}
		return collist;
	}

	public QueryResultVo modifyData(List<Map> list, String tableName) {
		QueryResultVo vo = new QueryResultVo();
		StringBuilder querys = new StringBuilder(
				"UPDATE (SELECT rowid row_ida, a.* from " + tableName
						+ " a) SET");
		String rowid = null;

		for (Map<String, String> map : list) {
			Set set = map.entrySet(); // entrySet을 반환한다. (key와 value를 쌍으로 묶은 값)
			Iterator it = set.iterator();// set에 들어간 entrySet을 Iterator로
											// 받는다.(배열로 전환)
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();// next를 이용하여 하나씩 값을 반환받는다.
				if(e.getKey().equals("ROWID")){
					rowid = (String) e.getValue();
				}else if (e.getValue() != null && !e.getValue().equals("null")){
					querys.append(" "+e.getKey()+" = '"+e.getValue()+"' ,");
				}else {
					querys.append(" "+e.getKey()+" = null ,");
				}

			}
		}
		
		querys.deleteCharAt(querys.lastIndexOf(","));
		
		querys.append("WHERE row_ida = '" + rowid+"'");
		
		String query = new String(querys);
		
		System.out.println(query);
		int result = 0;
		
		try {
			statement = Connect.connection.createStatement();
			result= statement.executeUpdate(query);
			vo.setModify(result);
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			if(vo.getError() != null){
				vo.setError(vo.getError()+error);
			}else{
				vo.setError(error);
			}
		}finally{
			closeStatement();
		}
		
		return vo;
	}
	
	public String killSession(LoginUser user){
		String sid = user.getSid();
		String serial = user.getSn();
		StringBuilder querys = new StringBuilder("ALTER SYSTEM KILL SESSION '"+sid+"' , '"+serial+"'");
		String query = new String(querys);
		StringBuilder results = new StringBuilder();
		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(query);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while(resultSet.next()){
				for (int j = 0; j < resultSetMetaData.getColumnCount(); j++) {
					results.append(resultSet.getString(resultSetMetaData.getCatalogName(j))) ;
				}
				
			}
		} catch (SQLException e) {
			StringBuilder errors = new StringBuilder(e.getErrorCode() + e.getMessage());
			String error = new String(errors);
			return error;
		}finally{
			closeStatement();
		}
		
		String result = new String (results);
		
		return result;
		
	}
	
	public boolean checkAuthority(){
		String query = "select grantee, granted_role from dba_role_privs";
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
				System.out.println("false");
				return false;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()+e.getMessage());
			System.out.println("true");
			return true;
		} catch (Exception e) {
			System.out.println(e.getStackTrace()+e.getMessage());
			System.out.println("true");
			return true;
		}finally{
			closeStatement();
		}
		
		
	}
	
	public List<Map<String, String>> getColType(String tableName){
		String coltype =  "SELECT USER_COL_COMMENTS.COLUMN_NAME,"
				+ "USER_TAB_COLUMNS.DATA_TYPE,"
				+ "from USER_TAB_COLUMNS,USER_COL_COMMENTS "
				+ "where USER_TAB_COLUMNS.TABLE_NAME = '"
				+ tableName
				+ "' AND USER_TAB_COLUMNS.TABLE_NAME=USER_COL_COMMENTS.TABLE_NAME AND USER_TAB_COLUMNS.COLUMN_NAME=USER_COL_COMMENTS.COLUMN_NAME";
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(coltype);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 1; j <= resultSetMetaData.getColumnCount(); j++) {
					String text = resultSet.getString(resultSetMetaData
							.getColumnName(j));
					map.put(resultSetMetaData.getColumnName(j),
							text != null ? text : "null");
				}

				list.add(map);

			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeStatement();
		}
	
		return list;
	}
	
	public List<List<String>> getQuery(){
		String query = "select sql_text, first_load_time as load_time from"
		+"(select *	from v$sql order by first_load_time desc) where SQL_TEXT != 'select * from"
		+"(select *	from v$sql order by first_load_time desc)%' and rownum < 6";
		
		 List<List<String>> result = new ArrayList<List<String>>();
			try {
				statement = Connect.connection.createStatement();
				resultSet =statement.executeQuery(query);
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				
//				System.out.println(resultSetMetaData.getColumnCount());
				//컬럼값 읽어오기
			 
				while (resultSet.next()) {
					List<String> queryList = new ArrayList<String>();
					for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
						// 컬럼값 한개씩 가져오기
					    
						queryList.add(resultSet.getString(resultSetMetaData.getColumnName(i)));
						
					}
					result.add(queryList);
//					System.out.println();
				}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeStatement();
		}
		return result;
	}
	
	

	
}