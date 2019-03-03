package udvari.HobbyTicTacToe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendAutomaticListService {

    @Autowired
    private SimpMessagingTemplate webSocket;


    @Async
    public void pushAnHello (String message){
        System.out.println("Sending Websocket message");

        webSocket.convertAndSend("/topic/changeinlist", message);

    }

}
