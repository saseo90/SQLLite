package lite.sql.frontdoor.loginPage.loginList;

import javafx.scene.control.CheckBox;


public class LoginUser {
	String id;
	String ip;
	String pw;
	String sid;
	String port;
	String url;
	String sn;
	String dataType;
	String machine;
	String program;
	CheckBox check;

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public CheckBox getCheck() {
		return check;
	}

	public void setCheck(CheckBox check) {
		this.check = check;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public LoginUser() {
	}

	public LoginUser(String ip, String id, String pw, String sid, String port,
			String dataType) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.id = id;
		this.pw = pw;
		this.sid = sid;
		this.port = port;
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "LoginUser [id=" + id + ", ip=" + ip + ", pw=" + pw + ", sid="
				+ sid + ", port=" + port + ", url=" + url + "]";
	}

}
