package lite.sql.utility;

public class Regular {
	public final static String regexUpdate = "^(UPDATE)(?:[^;]|(?:'.*?'))+\\s*$";
	public final static String regexInsert = "^(INSERT INTO)(?:[^;]|(?:'.*?'))+\\s*$";
	public final static String regexSelect = "^(SELECT)(?:[^;]|(?:'.*?'))+\\s*$";
	public final static String regexDelete = "^(DELETE)(?:[^;]|(?:'.*?'))+\\s*$";
	public final static String regexCreate = "^(CREATE)(?:[^;]|(?:'.*?'))+\\s*$";
	public final static String regexAlter  = "^(ALTER)(?:[^;]|(?:'.*?'))+\\s*$";	
	public final static String regexDrop  = "^(DROP)(?:[^;]|(?:'.*?'))+\\s*$";	
	public final static String regexDate = "^([1-9][0-9][0-9][0-9])-([0-9][0-9])-([0-9][0-9])(?:[^;]|(?:'.*?'))+\\s*$";
	
}
