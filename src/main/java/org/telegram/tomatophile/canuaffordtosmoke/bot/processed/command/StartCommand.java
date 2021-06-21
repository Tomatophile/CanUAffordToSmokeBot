package org.telegram.tomatophile.canuaffordtosmoke.bot.processed.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.tomatophile.canuaffordtosmoke.bot.keyboard.ReplyKeyboardMarkupBuilder;
import org.telegram.tomatophile.canuaffordtosmoke.bot.processed.textcommand.TextCommand;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final List<TextCommand> textCommands;

    @Getter
    private final String command = "/start";

    @Override
    public SendMessage answer(String chatId) {
        var keyboard = ReplyKeyboardMarkupBuilder.create(chatId);

        keyboard.setText("Привет! \nС моей помошью ты сможешь узнать, какие сигареты тебе сейчас по карману.");

        for (var i = 0; i < textCommands.size(); i++) {
            keyboard.addButton(textCommands.get(i).getText());
            if (i == textCommands.size() - 1) {
                break;
            }
            keyboard.nextRow();
        }

        return keyboard.build();
    }
}
