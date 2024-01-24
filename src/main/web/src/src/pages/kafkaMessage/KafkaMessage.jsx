import React, { useState, useRef, useEffect } from 'react';
import './kafkaMessage.css';
import * as StompJs from "@stomp/stompjs";
import * as SockJS from "sockjs-client";
import { v4 as uuidv4 } from 'uuid';

const ROOM_SEQ = uuidv4();
let writer = uuidv4();

const KafkaMessage = () => {

    const [message, setMessage] = useState([]);
    const innerRef = useRef(null);
    const client = useRef({});
    const [chatMessage, setChatMessage] = useState([]);

    useEffect(() => {

        if (innerRef.current) {
            innerRef.current.scrollTo(0, innerRef.current.scrollHeight);
        }

        connect();

        return () => disconnect();

    }, []);

    const disconnect = () => {
        client.current.deactivate();
    };

    const subscribe = () => {
        client.current.subscribe(`/sub/chat/${ROOM_SEQ}`, ({ body }) => {

            const parsedBody = JSON.parse(body);

            const newMessage = {
                userKey: parsedBody['userKey'],
                message: parsedBody['message'],
                writer: parsedBody['writer'],
                sessionId: parsedBody['sessionId'],
                time: currentTime(),
                isMine: false,
            };



            if (parsedBody['writer'] !== writer) {
                setChatMessage((_chatMessage) => [..._chatMessage, newMessage]);
            }
        });
    };

    const publish = (message,e) => {
        if (!client.current.connected) {
            return;
        }

        client.current.publish({
            destination: "/pub/chat",
            body: JSON.stringify({ userKey: ROOM_SEQ, message,writer }),
        });

        handleKeyPress(e);
        setMessage("");
    };

    const connect = () => {
        client.current = new StompJs.Client({
            // brokerURL: "ws://localhost:8080/ws-stomp/websocket", // 웹소켓 서버로 직접 접속
            webSocketFactory: () => new SockJS("/ws-stomp"), // proxy를 통한 접속
            connectHeaders: {
                "auth-token": "spring-chat-auth-token",
            },
            debug: function (str) {
                console.log(str);
            },

            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            onConnect: () => {
                subscribe();
            },
            onStompError: (frame) => {
                console.error(frame);
            },
        });

        client.current.activate();
    };

    const handleKeyPress = (e) => {

        if (e.key === 'Enter' && e.target.value.trim() !== '') {
            const newMessage = {
                content: e.target.value.trim(),
                time: currentTime(),
                isMine: true,
            };

            setChatMessage((prevMessage) => [...prevMessage, newMessage]);
            e.target.value = '';

        }
    };

    const currentTime = () => {
        const date = new Date();
        const hh = date.getHours();
        const mm = date.getMinutes();
        const apm = hh > 12 ? '오후' : '오전';
        return `${apm} ${hh}:${mm}`;
    };

    return (
        <div className="chat_wrap2">
            <input className="mymsg"
                   type={"text"}
                   placeholder={"kafka message push"}
                   value={message}
                   onChange={(e) => setMessage(e.target.value)}
                   onKeyPress={(e) => e.which === 13 && publish(message,e)}
            />

            <div>
                {
                    chatMessage.map((_chatMessage, index) => (

                        <div className={`item ${_chatMessage.isMine ? 'mymsg' : ''}`} key={index}>
                            {
                                <div className="box">
                                    <p className="msg">{_chatMessage.content}</p>
                                </div>
                            }

                        </div>
                    ))}
            </div>


        </div>
    );
};

export default KafkaMessage;