package org.telegram.tomatophile.canuaffordtosmoke.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.tomatophile.canuaffordtosmoke.bot.processed.command.Command;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandMessageHandler {

    private final List<Command> commands;

    private final ReplyMessageService replyMessageService;

    public List<SendMessage> handleCommand(Message message) {
        var messages = new ArrayList<SendMessage>();
        for(var command : commands){
            if(message.getText().equals(command.getCommand())){
                messages.add(command.answer(message.getChatId().toString()));
                return messages;
            }
        }
        messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), "Не понимаю тебя"));
        return messages;
    }
}
