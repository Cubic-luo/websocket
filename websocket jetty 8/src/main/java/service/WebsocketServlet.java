package service;

import entity.Websocket;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:用继承org.eclipse.jetty.websocket.WebSocketServlet的方式实现服务端websocket连接，
 * 目前只能跑在jetty 8.*服务器里，tomcat和jetty 9.*里都跑不了
 * 原因：jetty 9.*对websocket有部分改动，不是继承的WebSocketServlet
 * 如果改成使用JSR 365接口实现websocket则可以跑在tomcat和jetty里，版本是7以上，具体的看官方信息，忘了
 */
public class WebsocketServlet extends WebSocketServlet {

    public WebSocket doWebSocketConnect(HttpServletRequest request, String s) {
        Websocket webSocket = new Websocket();
        WebsocketManager.webSocketSet.add(webSocket);
        return webSocket;
    }

}
