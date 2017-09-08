package service;

import entity.Websocket;

import java.io.IOException;
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
            try {
                jettyWebsocket.sendMessage(data);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("出错了");
            }
        }
    }
}
