<%--
  Created by IntelliJ IDEA.
  User: luoxianping
  Date: 2017/9/5
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = "//" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>聊天室</title>
    <style>
        .wrap {
            margin: 100px auto;
            width: 500px;
        }

        .write {
            display: block;
            width: 500px;
            height: 50px;
            resize: none;
            padding: 10px;
            margin-bottom:10px;
            border: 1px solid #34a8ff;
        }

        #message {
            padding: 10px;
            width: 500px;
            height: 300px;
            line-height: 1.8;
            box-sizing: border-box;
            overflow: auto;
            font-size: 14px;
            border:1px solid #e0e0e0;
        }
        .btn {
            background: #34a8ff;
            outline: none;
            border: none;
            cursor: pointer;
            padding:5px 15px;
            border-radius: 5px;
            color: white;
        }
    </style>
</head>
<body>
<img src="http://<%=basePath%>img/top.jpg" alt="" style="display: block;margin: 0 auto;">
<div class="wrap">
    <div id="message"></div>
    <textarea id="text" class="write" placeholder="格式如下：message@username；私聊如下：你是谁？@游客1；群聊如下：你们好@all"></textarea>
    <button class="btn" onclick="send()">发送消息</button>
    <button class="btn" id="online" onclick="reload()">上线</button>
    <button class="btn" id="changeLine" onclick="closeWebSocket()">下线</button>
</div>
</body>
<script src="http:<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript">
    var websocket = null;
    //1.判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws:<%=basePath%>wechat");
    }
    else {
        alert('当前浏览器不支持websocket')
    }
    //2.连接成功建立的回调方法
    websocket.onopen = function () {
        showMessage("您已上线");
    }

    //3.接收到消息的回调方法
    websocket.onmessage = function (event) {
        showMessage(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        showMessage("您已下线");
    }

    //将消息显示在网页上
    function showMessage(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
        $("#text").text().clear;
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        showMessage("WebSocket连接发生错误");
    };
    //刷新页面上线
    function reload() {
        location.reload();
    }
</script>
</html>
