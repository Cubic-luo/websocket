package service;

import entity.Websocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * 继承org.eclipse.jetty.websocket.servlet.WebSocketServlet的方式只能运行在jetty 9.*服务器中，8.*是不行的，tomcat也不行
 * jetty官网 http://www.eclipse.org/jetty/documentation/current/jetty-websocket-server-api.html
 */
@SuppressWarnings("serial")
@WebServlet(name = "MyEcho WebSocket Servlet", urlPatterns = {"/wechat"})
public class WebsocketServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        // set a 10 second timeout
        factory.getPolicy().setIdleTimeout(10000);

        // register MyEchoSocket as the WebSocket to create on Upgrade
        factory.register(Websocket.class);
    }
}
