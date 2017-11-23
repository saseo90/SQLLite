package org.commons.util.websocket;

import org.springframework.web.socket.WebSocketSession;

/**
 * 
 * 웹소켓 서버에 접속한 유저정보 View Object 
 * webSocketUser : 내사원번호
 * webSession : 내세션정보
 */
public class WebSocketUserVo {
	
	private String webSocketUser;//내 유저정보 고유번호 ID et
	private WebSocketSession webSocketSession;//내 세션정보
	
	public WebSocketUserVo(){};
	public String getWebSocketUser() {
		return webSocketUser;
	}
	public void setWebSocketUser(String webSocketUser) {
		this.webSocketUser = webSocketUser;
	}
	public WebSocketSession getWebSession() {
		return webSocketSession;
	}
	public void setWebSession(WebSocketSession webSocketSession) {
		this.webSocketSession = webSocketSession;
	}
	@Override
	public String toString() {
		return "WebSocketUserVo [webSocketUser=" + webSocketUser + ", webSession=" + webSocketSession + "]";
	}
}
