package entity;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import service.WebsocketManager;

import java.io.IOException;

/**
 * 服务器websocket连接端点
 */
public class Websocket implements WebSocketListener {
    private Session session;//记录连接，websocket session（websocket会话）


    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
        //客户端发来二进制消息时调用
    }

    @Override
    public void onWebSocketText(String s) {
        System.out.println("客户端发来消息" + s);
        WebsocketManager.sendMessageToAll(s);
    }

    @Override
    public void onWebSocketClose(int i, String s) {
        this.session.close();
        this.session = null;

    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        WebsocketManager.webSocketSet.add(this);
    }

    @Override
    public void onWebSocketError(Throwable throwable) {
        throwable.printStackTrace(System.err);
        WebsocketManager.webSocketSet.remove(this);
        System.out.println("发生错误");
    }

    /**
     * 发送消息
     *
     * @param data
     */
    public void sendMessage(String data) {
        try {
            this.session.getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
