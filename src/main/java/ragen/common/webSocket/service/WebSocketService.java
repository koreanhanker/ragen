package ragen.common.webSocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

public interface WebSocketService {
//    public boolean publish(String type);
//
//    public boolean publishKaKaoEndSession(String userKey);
//
//    public boolean todayTktPublish(String tkt_num);
//
//    public boolean orgPublish(String type, String org_cd);
//
//    public boolean pushInCallsWaiting(String message);

    public boolean redisReadSocketPush(String message);

    public void pushJedis(String message) throws JsonProcessingException;

    public void pushSocketJedis(HashMap<String, Object> resultMap);
}
