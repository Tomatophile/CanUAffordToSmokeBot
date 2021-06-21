package org.telegram.tomatophile.canuaffordtosmoke.bot.processed.textcommand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class CanIAffordCommand implements TextCommand {

    private final ReplyMessageService replyMessageService;

    @Getter
    private final String text = "Что я могу себе позволить?";

    @Override
    public SendMessage answer(String chatId) {
        return replyMessageService.getTextMessage(chatId, "Укажи свой бюджет в формате: 100-150р\nИ я подскажу, на какие сигареты ты можешь рассчитывать.");
    }
}
