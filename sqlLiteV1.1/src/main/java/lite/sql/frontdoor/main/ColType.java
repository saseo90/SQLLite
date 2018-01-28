package lite.sql.frontdoor.main;

public class ColType {
	String COLUMN_NAME;
	String DATE_TYPE;
	String NULLABLE;
	String DATE_DEFAULT;
	String COLUMN_ID;
	String COMMENTS;

	

	public ColType(String cOLUMN_NAME, String dATE_TYPE, String nULLABLE, String dATE_DEFAULT, String cOLUMN_ID,
			String cOMMENTS) {
		this.COLUMN_NAME = cOLUMN_NAME;
		this.DATE_TYPE = dATE_TYPE;
		this.NULLABLE = nULLABLE;
		this.DATE_DEFAULT = dATE_DEFAULT;
		this.COLUMN_ID = cOLUMN_ID;
		this.COMMENTS = cOMMENTS;
	}

	public String getCOLUMN_NAME() {
		return COLUMN_NAME;
	}

	public void setCOLUMN_NAME(String cOLUMN_NAME) {
		COLUMN_NAME = cOLUMN_NAME;
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

}
