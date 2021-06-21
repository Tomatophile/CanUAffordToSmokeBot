package org.telegram.tomatophile.canuaffordtosmoke.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.tomatophile.canuaffordtosmoke.service.CigaretteService;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceHandler {

    private final CigaretteService cigaretteService;

    private final ReplyMessageService replyMessageService;

    public List<SendMessage> handlePrice(Message message) {
        var priceRange = message.getText().split("[-р]");

        var minPrice = Integer.parseInt(priceRange[0]);
        var maxPrice = Integer.parseInt(priceRange[1]);

        var cigarettes = cigaretteService.findAllInRange(minPrice, maxPrice);

        var messages = new ArrayList<SendMessage>();
        var str = new StringBuilder();

        if (!cigarettes.isEmpty()) {
            str.append("Тебе по карману:\n");

            var count = 0;
            for (var cigarette : cigarettes) {
                str
                        .append(cigarette.getName())
                        .append(" - ")
                        .append(cigarette.getPrice())
                        .append("р\n");
                count++;
                if (count == 60) {
                    messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), str.toString()));
                    str = new StringBuilder();
                    count = 0;
                }
            }
            messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), str.toString()));
        } else {
            str.append("Я очень старался, но не нашел ничего подходящего :(");
        }

        return messages;
    }
}
