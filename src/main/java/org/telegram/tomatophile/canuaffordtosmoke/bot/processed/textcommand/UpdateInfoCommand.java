package org.telegram.tomatophile.canuaffordtosmoke.bot.processed.textcommand;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

@Component
@RequiredArgsConstructor
public class UpdateInfoCommand implements TextCommand {

    private final ReplyMessageService replyMessageService;

    @Getter
    private final String text = "Обновить устаревшую информацию!";

    @Override
    public SendMessage answer(String chatId) {
        return replyMessageService.getTextMessage(chatId, "К сожалению Федеральная Налоговая Служба закрыла доступ к этим данным для роботов," +
                " поэтому я больше не могу обновлять цены автоматически. Но ты можешь мне помочь! \nПерейди по ссылке https://service.nalog.ru/tabak.do \n" +
                "Выбери всех производителей и введи капчу, после чего сохрани страницу (CTRL + S) и отправь мне." +
                " Если я найду на ней более свежие цены, то сразу же их обновлю!");
    }
}
