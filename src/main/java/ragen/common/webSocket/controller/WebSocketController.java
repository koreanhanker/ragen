package ragen.common.webSocket.controller;

import ragen.common.util.SessionConst;
import ragen.common.webSocket.dto.WebSocketMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WebSocketController {
    private static final Set<String> SESSION_IDS = new LinkedHashSet<>();
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat") // "/pub/chat"
    public void publishChat(WebSocketMessageDTO chatMessage, SimpMessageHeaderAccessor accessor)
    {
        log.info("publishChat : {} {} {}", chatMessage , accessor.getSessionId());
        chatMessage.setSessionId(accessor.getSessionId());
        messagingTemplate.convertAndSend("/sub/chat/" + chatMessage.getUserKey(), chatMessage);
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event)
    {
        String sessionId = event.getMessage().getHeaders().get(SessionConst.SESSIONID).toString();
        SESSION_IDS.add(sessionId);
        // userId 와 sessionId 를 맵핑하여 저장
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        List<String> tokens = headerAccessor.getNativeHeader(SessionConst.TOKEN);



        log.info("[connect] connections : {} {} {}", SESSION_IDS.size(),sessionId);
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event)
    {
        String sessionId = event.getSessionId();
        // 소켓통신인지 확인후에 로그아웃시간을 남긴다
        log.info("onDisconnect sessionId :"+sessionId);


        SESSION_IDS.remove(sessionId);
        log.info("[disconnect] connections : {} {} {} {}", SESSION_IDS.size(),sessionId , event.getMessage() , event.getUser());
    }
}

