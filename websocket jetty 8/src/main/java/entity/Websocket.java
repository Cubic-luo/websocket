package entity;

import org.eclipse.jetty.websocket.WebSocket;
import service.WebsocketManager;

import java.io.IOException;

/**
 * 服务器websocket连接端点
 */
public class Websocket implements WebSocket.OnTextMessage {
    private Connection connection;//记录连接，websocket session（websocket会话）

    /**
     * websocket连接成功时调用此方法
     *
     * @param connection
     */
    public void onOpen(Connection connection) {
        this.connection = connection;
        System.out.println("连接成功");
        try {
            this.connection.sendMessage("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息时调用此方法
     *
     * @param s 客户端消息
     */
    public void onMessage(String s) {
        System.out.println("收到客户端消息：" + s);
        WebsocketManager.sendMessageToAll(s);
    }

    /**
     * 关闭连接是调用此方法
     *
     * @param i
     * @param s
     */
    public void onClose(int i, String s) {
        this.connection.close();
        WebsocketManager.webSocketSet.remove(this);
        System.out.println("连接已关闭");
    }

    /**
     * 向客户端发送消息
     *
     * @param data 消息
     * @throws IOException
     */
    public void sendMessage(String data) throws IOException {
        connection.sendMessage(data);
    }

}
