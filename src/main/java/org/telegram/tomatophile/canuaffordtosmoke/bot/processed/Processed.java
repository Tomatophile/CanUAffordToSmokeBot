package org.telegram.tomatophile.canuaffordtosmoke.bot.processed;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Processed {

    public SendMessage answer(String chatId);
}
