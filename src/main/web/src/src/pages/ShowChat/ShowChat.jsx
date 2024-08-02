import React, { useState, useRef, useEffect, useCallback } from 'react';
import '../../Chat.css';
import * as StompJs from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { v4 as uuidv4 } from 'uuid';

const ROOM_SEQ = 1;
const writer = uuidv4();

const ShowChat = () => {
    const [message, setMessage] = useState('');
    const innerRef = useRef(null);
    const client = useRef({});
    const [chatMessage, setChatMessage] = useState([]);

    useEffect(() => {
        if (innerRef.current) {
            innerRef.current.scrollTo(0, innerRef.current.scrollHeight);
        }
    }, [chatMessage]);

    const connect = useCallback(() => {
        client.current = new StompJs.Client({
            webSocketFactory: () => new SockJS('/ws-stomp'), // proxy를 통한 접속
            connectHeaders: {
                'auth-token': 'spring-chat-auth-token',
            },
            debug: (str) => {
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
    }, []);

    useEffect(() => {
        connect();
        return () => disconnect();
    }, [connect]);

    const disconnect = () => {
        if (client.current && client.current.connected) {
            client.current.deactivate();
        }
    };

    const subscribe = () => {
        client.current.subscribe(`/sub/chat/${ROOM_SEQ}`, ({ body }) => {
            const parsedBody = JSON.parse(body);

            const newMessage = {
                userKey: parsedBody.userKey,
                message: parsedBody.message,
                writer: parsedBody.writer,
                sessionId: parsedBody.sessionId,
                time: currentTime(),
                isMine: false,
            };

            if (parsedBody.writer !== writer) {
                setChatMessage((prevMessages) => [...prevMessages, newMessage]);
            }
        });
    };

    const publish = (messageContent, e) => {
        if (!client.current.connected) {
            return;
        }

        client.current.publish({
            destination: '/pub/chat',
            body: JSON.stringify({ userKey: ROOM_SEQ, message: messageContent, writer }),
        });

        handleKeyPress(e);
        setMessage('');
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter' && e.target.value.trim() !== '') {
            const newMessage = {
                content: e.target.value.trim(),
                time: currentTime(),
                isMine: true,
            };

            setChatMessage((prevMessages) => [...prevMessages, newMessage]);
            e.target.value = '';
        }
    };

    const currentTime = () => {
        const date = new Date();
        const hh = date.getHours();
        const mm = date.getMinutes();
        const apm = hh >= 12 ? '오후' : '오전';
        return `${apm} ${hh % 12}:${mm}`;
    };

    return (
        <div className="chat_wrap">
            <div className="inner" ref={innerRef}>
                {chatMessage.map((_chatMessage, index) => (
                    <div className={`item ${_chatMessage.isMine ? 'mymsg' : ''}`} key={index}>
                        <div className="box">
                            <p className="msg">{_chatMessage.isMine ? _chatMessage.content : _chatMessage.message}</p>
                        </div>
                    </div>
                ))}
            </div>

            <input
                className="mymsg"
                type="text"
                placeholder="message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyPress={(e) => e.which === 13 && publish(message, e)}
            />
        </div>
    );
};

export default ShowChat;
