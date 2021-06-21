package org.telegram.tomatophile.canuaffordtosmoke.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.tomatophile.canuaffordtosmoke.bot.SmokingBot;
import org.telegram.tomatophile.canuaffordtosmoke.entity.Cigarette;
import org.telegram.tomatophile.canuaffordtosmoke.service.CigaretteService;
import org.telegram.tomatophile.canuaffordtosmoke.service.ReplyMessageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HtmlTableHandler {
    private final ReplyMessageService replyMessageService;
    @Autowired
    private SmokingBot smokingBot;

    private final CigaretteService cigaretteService;

    public List<SendMessage> handleHtmlTable(Message message) {
        var messages = new ArrayList<SendMessage>();

        Path path;
        String file = null;

        try {
            var getFile = new GetFile();
            getFile.setFileId(message.getDocument().getFileId());

            path = smokingBot.downloadFile(smokingBot.execute(getFile)).toPath();

            file = Files.readString(path);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }

        if (file.startsWith("<!DOCTYPE html>")) {
            var table = file
                    .split(".{1,2}tbody.")[1]
                    .replaceAll("\\t|\\n", "")
                    .replaceAll(">\\s+<", "><")
                    .replace(" class=\"align-right\"", "")
                    .split("</tr><tr class=\"\\w{0,3}\">");

            var count = -1;
            for (var str : table) {
                if (count < 0) {
                    count++;
                    continue;
                }

                var arrStr = str.split("</td><td>");
                var newCigarette = new Cigarette();
                newCigarette.setName(arrStr[2]);
                newCigarette.setDate(LocalDate.parse(arrStr[4], DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                newCigarette.setPrice(Float.parseFloat(arrStr[6].replaceAll("</td>.*", "")));

                if (newCigarette.getDate().getYear() < LocalDate.now().getYear() || newCigarette.getPrice()<50) {
                    continue;
                }

                var cigaretteFromDB = cigaretteService.findByName(newCigarette.getName());

                if (cigaretteFromDB != null) {
                    if (cigaretteFromDB.getDate().isAfter(newCigarette.getDate())) {
                        continue;
                    }
                    newCigarette = cigaretteFromDB.setDate(newCigarette.getDate()).setPrice(newCigarette.getPrice());
                }
                    cigaretteService.save(newCigarette);

                    count++;
            }

            if (count == 0) {
                messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), "Похоже, что ничего не изменилось."));
                return messages;
            }
            messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), "Спасибо! Я обновил " + count + " наименований."));
            return messages;
        }
        messages.add(replyMessageService.getTextMessage(message.getChatId().toString(), "Похоже этот документ неподходящего формата. Я не могу его прочитать"));
        return messages;
    }
}
