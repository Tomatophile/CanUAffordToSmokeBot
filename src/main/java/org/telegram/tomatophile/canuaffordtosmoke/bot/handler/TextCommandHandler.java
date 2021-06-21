package org.telegram.tomatophile.canuaffordtosmoke.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.tomatophile.canuaffordtosmoke.bot.processed.textcommand.TextCommand;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TextCommandHandler {

    private final List<TextCommand> textCommands;

    private final ReplyMessageService replyMessageService;

    public List<SendMessage> handleText(Message message) {
        var messages = new ArrayList<SendMessage>();

        for(var textMessage : textCommands){
            if(message.getText().equals(textMessage.getText())){
                messages.add(textMessage.answer(message.getChatId().toString()));
                return messages;
            }
        }
        messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), "Даже не знаю, что сказать"));
        return messages;
    }
}
