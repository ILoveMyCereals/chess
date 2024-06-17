package net;


import com.google.gson.Gson;
import ui.InGame;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.swing.*;
import javax.websocket.*;
import java.net.URI;

public class WSServerFacade extends Endpoint {

    private Session session;

    private String uristub;

    public WSServerFacade(Integer port, InGame ui) {
        try {
            uristub = "ws://localhost:" + Integer.toString(port) + "/ws";
            URI uri = new URI(uristub);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    ServerMessage firstMessage = new Gson().fromJson(message, ServerMessage.class);
                    if (firstMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                        NotificationMessage secondMessage = new Gson().fromJson(message, NotificationMessage.class);
                        ui.notify(secondMessage);
                    } else if (firstMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)) {
                        LoadGameMessage secondMessage = new Gson().fromJson(message, LoadGameMessage.class);
                        ui.notify(secondMessage);
                    } else if (firstMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)) {
                        ErrorMessage secondMessage = new Gson().fromJson(message, ErrorMessage.class);
                        ui.notify(secondMessage);
                    }
                    //deserialize message and put in notify
                }
            });
        } catch (Exception ex) {
            return;
        }
    }

    public void sendConnectCommand(ConnectCommand command) {
        String jsonCommand = new Gson().toJson(command);
        try {
            this.session.getBasicRemote().sendText(jsonCommand);
        } catch (Exception ex) {
            return;
        }
    }

    public void sendMoveCommand(MakeMoveCommand command) {
        String jsonCommand = new Gson().toJson(command);
        try {
            this.session.getBasicRemote().sendText(jsonCommand);
        } catch (Exception ex) {
            return;
        }
    }

    public void sendLeaveCommand(LeaveCommand command) {
        String jsonCommand = new Gson().toJson(command);
        try {
            this.session.getBasicRemote().sendText(jsonCommand);
        } catch (Exception ex) {
            return;
        }
    }

    public void sendResignCommand(ResignCommand command) {
        String jsonCommand = new Gson().toJson(command);
        try {
            this.session.getBasicRemote().sendText(jsonCommand);
        } catch (Exception ex) {
            return;
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}
}
