package com.daolv.socketserver;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;


public class SocketserverApplication {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(9092);
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        config.setOrigin(":*:");
//        config.setOrigin("*");
//        config.setOrigin("https://admsu.you88.club");




        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });
        server.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.stop();
    }

}
