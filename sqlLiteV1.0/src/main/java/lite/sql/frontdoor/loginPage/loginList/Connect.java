package lite.sql.frontdoor.loginPage.loginList;

import java.sql.Connection;

import lite.sql.backdoor.dao.DatabaseDao;

public class Connect {
	public static LoginUser user=null;
	public static LoginUser adminCheck=null;
	public static Connection connection = null;
	public static DatabaseDao dao = null;
}
