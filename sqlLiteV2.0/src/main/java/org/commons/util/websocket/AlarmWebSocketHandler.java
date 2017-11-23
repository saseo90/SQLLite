package org.commons.util.websocket;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 알람 전용 웹소켓 엔드포인트
 * @author pc25
 *
 */
public class AlarmWebSocketHandler extends TextWebSocketHandler {
	private Logger log = Logger.getLogger(this.getClass());
	private static ArrayList<WebSocketUserVo> users = new ArrayList<WebSocketUserVo>();
	
	public AlarmWebSocketHandler(){};
	
	/**
	 * 웹소켓 클라이언트와 연결시도 할 때 호출한다.
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		super.afterConnectionEstablished(session);
		this.log.info("[알림 ]["+session +"] 의 소켓연결성공 \n");
	}
	/**
	 * 웹소켓 클라이언트와 연결종료 될 때 호출한다.
	 * 1.클라이언트가 직접 종료하거나 서버에서 타임아웃이 발생해 연결 끊기면 호출한다.
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session ,CloseStatus status) throws Exception{
		super.afterConnectionClosed(session, status);
		for (WebSocketUserVo user : users) {
			if(user.getWebSession()==session){//해당 브라우저의 세션를 종료한다.
				users.remove(user);
				this.log.info("[알림 ]["+session.getId() +"] 의 소켓연결종료성공 \n");
				return;
			}
		}
	}
	/**
	 * 웹소켓 서버에 메시지 전송될 때 호출한다.
	 * 
	 * 나 자신에게 메시지 발송
	 * 예 : session.sendMessage(new TextMessage((String)message.getPayload()))
	 * 
	 * 참고 : http://huskdoll.tistory.com/6
	 */
	@Override
	public void handleTextMessage(WebSocketSession session ,@RequestBody TextMessage message) throws Exception{
		super.handleTextMessage(session, message);
		this.log.info("[알림1]["+session.getId() + "] 로 부터  ["+message.toString()+"] 를 전달받음 \n");

		JsonParser jsonParser = new JsonParser();
		//slsls
        //JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JsonObject jsonObject = (JsonObject) jsonParser.parse(message.getPayload().toString());
        String code     = jsonObject.get("code").toString();
        String receiver = jsonObject.get("receiver").toString();
        String msg      = jsonObject.get("message").toString();
        this.log.info("[알림]["+session.getId() + "] 로 부터  ["+receiver+" , "+code+" , "+msg+"] 를 전달받음 \n");

        if(code.equals("0")){//일반 메시지 파라미터 : 코드 ,유저아이디 ,메시지
        	for (WebSocketUserVo user : users) {
    			if(user.getWebSocketUser().equals(receiver)){//해당 아이디를 소유자의 모든 브라우저 세션을 찾는다.
    				user.getWebSession().sendMessage(new TextMessage(msg));
    			}
    		}
        }else if(code.equals("1")){//로그인 파라미터 : 코드 ,유저아이디 ,세션
        	WebSocketUserVo vo = new WebSocketUserVo();
        	vo.setWebSocketUser(receiver);
        	vo.setWebSession(session);
        	users.add(vo);
        }else if(code.equals("2")){//로그아웃 파라미터 : 코드 ,세션
    		for (WebSocketUserVo user : users) {
    			if(user.getWebSession()==session){//해당 브라우저의 세션를 종료한다.
    				users.remove(user);
    				return;
    			}
    		}
        }
 
		
	}
	/**
	 * 웹소켓 클라이언트와 연결에 문제가 발생하면 호출한다.
	 */
	@Override
    public void handleTransportError(WebSocketSession session ,Throwable exception) throws Exception{
		super.handleTransportError(session, exception);
		this.log.info("[알림 ]["+session.getId() +"] 의 소켓에러발생 ["+exception.getMessage()+"] \n");
    }
	/**
	 * 이벤트 메시지 발송
	 * 파라미터 : 받는 사원번호 ,메시지
	 */
	public void sendEventMessage(String webSocketUser,String message,String senderName) throws Exception {
		//System.out.println("유저들 "+users.toString()+" ,유저 : "+webSocketUser+",메시지"+message);
		for (WebSocketUserVo user : users) {
			if(user.getWebSocketUser().equals(webSocketUser)){
				user.getWebSession().sendMessage(new TextMessage("[알림]["+senderName+"]님으로부터 메시지 도착"));
				this.log.info("[알림]["+senderName+"]가 ["+user+"]에게 "+message+"메시지발신");
			}
		}
	}
	/**
	 * 이벤트 메시지 발송
	 * 문서 결재 올라감
	 * 파라미터 : 받는 사원번호 ,메시지
	 */
	public void sendEventDOC(String webSocketUser,String message,String senderName) throws Exception {
		//System.out.println("유저들 "+users.toString()+" ,유저 : "+webSocketUser+",메시지"+message);
		for (WebSocketUserVo user : users) {
			if(user.getWebSocketUser().equals(webSocketUser)){
				user.getWebSession().sendMessage(new TextMessage("[알림]["+senderName+"]님으로부터 결재서류 도착"));
				this.log.info("[알림]["+senderName+"]가 ["+user+"]에게 "+message+"결재서류 도착");
			}
		}
	}
	/**
	 * 이벤트 메시지 발송
	 * 파일공유신청
	 * 파라미터 : 받는 사원번호 ,메시지
	 */
	public void sendEventFILE(String webSocketUser,String message,String senderName) throws Exception {
		//System.out.println("유저들 "+users.toString()+" ,유저 : "+webSocketUser+",메시지"+message);
		for (WebSocketUserVo user : users) {
			if(user.getWebSocketUser().equals(webSocketUser)){
				user.getWebSession().sendMessage(new TextMessage("[알림]["+senderName+"]님으로부터 파일공유 수락메시지 도착"));
				this.log.info("[알림]["+senderName+"]가 ["+user+"]에게 "+message+" 파일공유 수락메시지 도착");
			}
		}
	}
}
