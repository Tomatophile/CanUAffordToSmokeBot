package org.telegram.tomatophile.canuaffordtosmoke.bot;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.tomatophile.canuaffordtosmoke.bot.handler.CommandMessageHandler;
import org.telegram.tomatophile.canuaffordtosmoke.bot.handler.HtmlTableHandler;
import org.telegram.tomatophile.canuaffordtosmoke.bot.handler.PriceHandler;
import org.telegram.tomatophile.canuaffordtosmoke.bot.handler.TextCommandHandler;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UpdateReceiver {

    private final PriceHandler priceHandler;
    private final TextCommandHandler textCommandHandler;
    private final CommandMessageHandler commandMessageHandler;
    @Autowired
    private HtmlTableHandler htmlTableHandler;

    private final ReplyMessageService replyMessageService;

    public List<SendMessage> handleUpdate(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();

            if (message.hasText()) {
                if (message.getText().matches("\\d+-\\d+р")) {
                    return priceHandler.handlePrice(message);
                } else if (message.getText().startsWith("/")) {
                    return commandMessageHandler.handleCommand(message);
                } else {
                    return textCommandHandler.handleText(message);
                }
            } else if (message.hasDocument()) {
                return htmlTableHandler.handleHtmlTable(message);
            }
        }

        var messages = new ArrayList<SendMessage>();
        messages.add(replyMessageService.getTextMessage(update.getMessage().getChatId().toString(), "Такой формат сообщений я не могу обработать"));
        return messages;
    }
}
