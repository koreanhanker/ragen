package ragen.common.webSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebSocketMessageDTO {
    private String userKey;
    private String message;
    private String writer;
    private String sessionId;
}
