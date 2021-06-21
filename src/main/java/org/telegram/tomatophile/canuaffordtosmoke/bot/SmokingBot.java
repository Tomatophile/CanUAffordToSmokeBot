package org.telegram.tomatophile.canuaffordtosmoke.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
@Setter
@Component
@RequiredArgsConstructor
public class SmokingBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private UpdateReceiver updateReceiver;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        var responseToUser = updateReceiver.handleUpdate(update);

        for (var sendMessage : responseToUser){
            execute(sendMessage);
        }
    }
}
