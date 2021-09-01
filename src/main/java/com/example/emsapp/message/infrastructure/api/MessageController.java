package com.example.emsapp.message.infrastructure.api;

import com.example.emsapp.message.domain.MessageCouldNotBeSentException;
import com.example.emsapp.message.domain.MessageManagementFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MessageController {
    private final MessageManagementFacade messageManagementFacade;

    public MessageController(MessageManagementFacade messageManagementFacade) {
        this.messageManagementFacade = messageManagementFacade;
    }

    @PostMapping("/{text}")
    public ResponseEntity<String> postMessageToQueue(@PathVariable(value="text")String text){
        messageManagementFacade.sendMessageToQueue(text);
        return ResponseEntity.accepted().build();
    }

    @GetMapping()
    public ResponseEntity<String> getMessageFromQueue(@RequestParam(value="is_redundancy_enabled")boolean isRedundancyEnabled){
        if(isRedundancyEnabled){
            return ResponseEntity.ok(messageManagementFacade.receiveMessageWithRedundancyEnabledFromQueue());
        }else {
            return ResponseEntity.ok(messageManagementFacade.receiveMessageWithRedundancyDisabledFromQueue());
        }
    }

    @PostMapping("/topic/{text}")
    public ResponseEntity<String> postMessageToTopic(@PathVariable(value="text")String text){
        messageManagementFacade.sendMessageToTopic(text);
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler(MessageCouldNotBeSentException.class)
    ResponseEntity<String> handleMessageCouldNotBeSentException(MessageCouldNotBeSentException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
