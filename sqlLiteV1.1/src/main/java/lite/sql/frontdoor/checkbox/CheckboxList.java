package lite.sql.frontdoor.checkbox;

import javafx.scene.control.CheckBox;

public class CheckboxList {
	
	private CheckBox check;
	private String tableName;
	
	public CheckboxList(){
		
	}
	
	public CheckBox getCheck() {
		return check;
	}
	public void setCheck(CheckBox check) {
		this.check = check;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Override
	public String toString() {
		return "CheckboxList [check=" + check + ", tableName=" + tableName
				+ "]";
	}

}
