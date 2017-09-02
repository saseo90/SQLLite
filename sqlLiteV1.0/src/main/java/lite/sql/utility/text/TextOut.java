package lite.sql.utility.text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lite.sql.utility.Regular;
import lite.sql.frontdoor.loginPage.loginList.Connect;

public class TextOut {
	Connection conn;
	StringBuilder sb;
	Statement statement;
	PreparedStatement psmt = null;
	ResultSet resultSet;
	ResultSet resultSet1;

	public StringBuilder table(String table) {
		StringBuilder query2 = new StringBuilder();
		String sql = "";
		StringBuilder sqlBuilder = new StringBuilder();
		// 테이블 생성
		String query3 = " SELECT COLUMN_NAME, DATA_TYPE, NVL(CHAR_COL_DECL_LENGTH, DATA_PRECISION), REPLACE(NULLABLE,'N','NOT NULL') "
				+ " FROM USER_TAB_COLUMNS TAB "
				+ "WHERE TABLE_NAME = '"
				+ table + "' ";

		try {
			statement = Connect.connection.createStatement();
			resultSet = statement.executeQuery(query3);

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			while (resultSet.next()) {

				sql += ", ";
				sql += resultSet.getString(resultSetMetaData.getColumnName(1))
						+ " ";
				sql += resultSet.getString(resultSetMetaData.getColumnName(2));
				sql += "("
						+ resultSet.getString(resultSetMetaData
								.getColumnName(3)) + ")" + " ";
				sql += resultSet.getString(resultSetMetaData.getColumnName(4))
						.replace("Y", "NULL") + " \n" + "\n";

			}

			sql=sql.replace("DATE(null)", "DATE").replace("NUMBER(null)", "NUMBER");
			sqlBuilder.append(sql).replace(0, 1, "");

			query2.append("CREATE TABLE " + table + " ( " + "\n" + "\n"
					+ sqlBuilder + " );  " + "\n");
			// System.out.println(" << 테이블 생성 >> ");
			System.out.println(query2);

		} catch (Exception e) {
			e.printStackTrace();

		}

		// table 데이터
		String data = " SELECT * from " + table + " ";

		// 날짜 제거 패턴
		Matcher matcher;
		Pattern p;
		p = Pattern.compile(Regular.regexDate);

		List<String> strq = new ArrayList<String>();
		List<String> strw = new ArrayList<String>();
		try {

			resultSet = statement.executeQuery(data);

			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			while (resultSet.next()) {
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					strq.add(resultSet.getString(resultSetMetaData
							.getColumnName(i)));
					strw.add(resultSetMetaData.getColumnName(i));
				}
			}

			// 여기부터 배열에서 꺼내어 1개의 로우를 하나의 INSERT문으로 만든다.
			// Pattern p;
			// Matcher matcher;
			// p = Pattern
			// .compile("^([1-9][0-9][0-9][0-9])-([0-9][0-9])-([0-9][0-9])(?:[^;]|(?:'.*?'))+\\s*$");

			int process = 0;
			StringBuilder resultQuery = new StringBuilder();
			for (int i = 0; i < (strq.size() / resultSetMetaData
					.getColumnCount()); i++) {
				StringBuilder sb = new StringBuilder("INSERT INTO " + table
						+ " (");
				for (int k = process; k < resultSetMetaData.getColumnCount()
						+ process; k++) {
					sb.append(strw.get(k) + ", ");

				}
				sb.delete(sb.lastIndexOf(","), sb.length());

				sb.append(")  VALUES(");

				for (int k = process; k < resultSetMetaData.getColumnCount()
						+ process; k++) {
					// matcher = p.matcher(strq.get(k));
					// if (matcher.matches()) {
					// sb.append("'" + strq.get(k).substring(0, 10) + "' ,");
					// } else {
					String text = strq.get(k);
					if (text != null) {
						matcher = p.matcher(text);
						if (matcher.matches()) {
							text = text.substring(0, 10);
						}
					}

					sb.append("'" + text + "' ,");

					// }

				}
				sb.delete(sb.lastIndexOf(","), sb.length());
				sb.append(");" + "\n");

				process += resultSetMetaData.getColumnCount();
				resultQuery.append(sb + "\n");
			}

			// System.out.println("< " + table + " 데이터> ");
			System.out.println(resultQuery);
			query2.append(resultQuery);

		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}

		return query2;

	}

}
