package net;


import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WSServerFacade extends Endpoint { //I have to include an overriding onOpen method, I won't have to write anything for the method itself

    private Session session;

    private String uristub;

    public WSServerFacade(Integer port) {
        uristub = "ws://localhost:" + Integer.toString(port);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    }
}
