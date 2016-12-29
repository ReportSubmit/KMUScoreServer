package kmu.itsp.score.scoring;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
 * web socket or stomp(using sockjs)
 * 
 * */
/**
 * 미구현
 * @author WJ
 *
 */
public class ScoringProgressHandler extends TextWebSocketHandler{

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
	}
	
	
}
