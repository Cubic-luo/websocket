package service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class Websocket {
    private static int onlineCount = 0;//记录在线人数
    //ConcurrentHashMap线程安全的map，记录每条连接，key：用户名 value：该用户的连接
    private static ConcurrentHashMap<String, Websocket> websockets = new ConcurrentHashMap();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String username;//用户名

    /**
     * 建立连接成功时调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        this.username = "游客" + session.getId();
        websockets.put(username, this);//存储到在线连接中
        onlineCount++;//在线人数加1
        this.sendMessage("您的名称是："+this.username);
        if (!websockets.isEmpty()) {
            for (String key : websockets.keySet()) {
                websockets.get(key).sendMessage("用户" + username + "已上线，当前在线人数为" + Websocket.onlineCount);
            }
        }

        System.out.println("用户" + username + "已上线，当前在线人数为" + Websocket.onlineCount);
    }

    /**
     * 连接关闭时调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        websockets.remove(username);//移除在此连接
        onlineCount--;//在线人数减1
        for (String key : websockets.keySet()) {
            websockets.get(key).sendMessage(this.username + "已下线");
        }
        System.out.println("用户" + username + "已下线，当前在线人数为" + Websocket.onlineCount);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String[] messageArray = message.split("@");
        String messageTag = messageArray[0];//获取消息内容
        String targetTag = messageArray[1];//获取消息发送对象
        //如果对象为all，则表示发送群消息
        if (targetTag.equals("all")) {
            System.out.println(username + "发送群消息:" + messageTag);
            for (String key : websockets.keySet()) {
                websockets.get(key).sendMessage(this.username + " @all："  + messageTag);
            }

        } else {
            //发送给名字为targetTag的私聊
            System.out.println(this.username + "@" + targetTag + messageTag);
            for (String key : websockets.keySet()) {
                if (key.equals(targetTag)) {
                    websockets.get(key).sendMessage(this.username + "给你发送私聊：" + messageTag);
                }
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 自定义的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
