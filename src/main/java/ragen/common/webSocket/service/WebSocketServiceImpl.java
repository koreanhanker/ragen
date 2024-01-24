package ragen.common.webSocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketService{

    @Value("${server.redis.host}")
    private String redisHost;

    @Value("${server.redis.port}")
    private int redisPort;

    @Value("${server.redis.channel}")
    private String redisChannel;
//
//    @Autowired
//    private WebSocketController webSocketController;
//
//    @Autowired
//    private UsrService usrService;
//
//    @Autowired
//    private KaKaoChatService kaKaoChatService;
//
//    @Autowired
//    private TkTMstService tkTMstService;
//
//    @Autowired
//    private CompOrgService compOrgService;
//
//    public boolean publish(String type)
//    {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("type", type);
//            String resultJson = objectMapper.writeValueAsString(resultMap);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//            List<UsrDTO> loginUserList = usrService.getLoginUsrList();
//
//            for (UsrDTO item : loginUserList) {
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(resultJson);
//
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean publish(String type, String value)
//    {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("type", type);
//            resultMap.put("value", value);
//            String resultJson = objectMapper.writeValueAsString(resultMap);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//            List<UsrDTO> loginUserList = usrService.getLoginUsrList();
//
//            for (UsrDTO item : loginUserList) {
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(resultJson);
//
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//    public boolean publishKaKaoEndSession(String userKey)
//    {
//        try {
//
//            KaKaoChatSessionEndDTO reqDTO = new KaKaoChatSessionEndDTO();
//            reqDTO.setUser_key(userKey);
//
//            List<KaKaoChatSessionEndDTO> expriedSessionList = kaKaoChatService.getKaKaoExpiredSessionList(reqDTO);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//            Map<String, Object> resultMap = new HashMap<>();
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            for(KaKaoChatSessionEndDTO item : expriedSessionList)
//            {
//                resultMap = new HashMap<>();
//                objectMapper = new ObjectMapper();
//                resultMap.put("type", "kakaoSessionEnd");
//                resultMap.put("tkt_num", item.getTkt_num());
//                String resultJson = objectMapper.writeValueAsString(resultMap);
//
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(resultJson);
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean todayTktPublish(String tkt_num)
//    {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("type", "ticket");
//            resultMap.put("value", tkt_num);
//            String resultJson = objectMapper.writeValueAsString(resultMap);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//
//            TkTDTO tkTDTO = new TkTDTO(); // ★ 티켓에 배정된 usr_id로 검색하는 방법
//            tkTDTO.setTkt_num(tkt_num);
//            tkTDTO.setDateFlag(CommonConstant.TODAY);
//            tkTDTO = tkTMstService.getTktMstInfo(tkTDTO);
//
//            chatMessage = new WebSocketMessageDTO();
//            chatMessage.setUserKey(tkTDTO.getUsr_id());
//            chatMessage.setMessage(resultJson);
//            webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean orgPublish(String type, String org_cd)
//    {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("type", type);
//            resultMap.put("org_cd", org_cd);
//            String resultJson = objectMapper.writeValueAsString(resultMap);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//
//            // 해당 조직코드가 있는 usr_id들만 push
//            List<CompOrgDTO> orgUserList = compOrgService.getRecursiveOrg(org_cd); // 조직코드로 재귀함수 실행
//
//            for (CompOrgDTO item : orgUserList) {
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(resultJson);
//
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean orgPublish(String type, String org_cd, String value)
//    {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("type", type);
//            resultMap.put("org_cd", org_cd);
//            resultMap.put("value", value);
//            String resultJson = objectMapper.writeValueAsString(resultMap);
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//
//            // 해당 조직코드가 있는 usr_id들만 push
//            List<CompOrgDTO> orgUserList = compOrgService.getRecursiveOrg(org_cd); // 조직코드로 재귀함수 실행
//
//            for (CompOrgDTO item : orgUserList) {
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(resultJson);
//
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean pushInCallsWaiting(String message)
//    {
//        try {
//
//            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//            WebSocketMessageDTO chatMessage = new WebSocketMessageDTO();
//            List<UsrDTO> loginUserList = usrService.getLoginUsrList();
//
//            for (UsrDTO item : loginUserList) {
//                chatMessage = new WebSocketMessageDTO();
//                chatMessage.setUserKey(item.getUsr_id());
//                chatMessage.setMessage(message);
//
//                webSocketController.publishChat(chatMessage, accessor);//연결된소켓에 push
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
    public boolean redisReadSocketPush(String message)
    {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(message);
        String type = jsonObject.get("type").getAsString();
//        String org_cd = StringUtils.getStringOrNull(jsonObject, "org_cd");
//        String tkt_num = StringUtils.getStringOrNull(jsonObject, "tkt_num");
//        String notiTitle = StringUtils.getStringOrNull(jsonObject, "title");
//
//        if(StringUtils.equals(type,"comCode") || StringUtils.equals(type,"comMsg"))
//        {
//            if(StringUtils.isNotEmpty(org_cd)){
//                this.orgPublish(type,org_cd);
//            }else {
//                this.publish(type);
//            }
//        }
//        else if(StringUtils.equals(type,"stnd"))
//        {
//            this.orgPublish(type,org_cd);
//        }
//        else if(StringUtils.equals(type,"ticket"))
//        {
//            this.todayTktPublish(tkt_num);
//        }
//        else if(StringUtils.equals(type,"call_waiting"))
//        {
//            this.pushInCallsWaiting(message);
//        }
//        else if(StringUtils.equals(type,"noti"))
//        {
//            if(StringUtils.isNotEmpty(org_cd)){
//                this.orgPublish(type,org_cd,notiTitle);
//            }else {
//                this.publish(type,notiTitle);
//            }
//        }
        return true;
    }

    public void pushSocketJedis(HashMap<String, Object> resultMap){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String resultJson = objectMapper.writeValueAsString(resultMap);
            this.pushJedis(resultJson);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pushJedis(String message) throws JsonProcessingException {

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            // PUBLISH 명령어 실행
            jedis.publish(redisChannel, message);
        }
    }
}
