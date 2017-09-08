package service;

import entity.Websocket;

import java.util.HashSet;
import java.util.Set;

public class WebsocketManager {
    public static Set<Websocket> webSocketSet = new HashSet();

    /**
     * 群发消息
     *
     * @param data
     */
    public static void sendMessageToAll(String data) {
        for (Websocket jettyWebsocket : webSocketSet) {
            jettyWebsocket.sendMessage(data);
        }
    }
}
