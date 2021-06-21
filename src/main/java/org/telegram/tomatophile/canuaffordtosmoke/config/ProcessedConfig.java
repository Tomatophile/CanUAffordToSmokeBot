package org.telegram.tomatophile.canuaffordtosmoke.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.tomatophile.canuaffordtosmoke.bot.processed.command.Command;
import org.telegram.tomatophile.canuaffordtosmoke.bot.processed.textcommand.TextCommand;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProcessedConfig {

    @Bean
    public List<Command> commands(ApplicationContext applicationContext) {
        var commands = new ArrayList<Command>();
        var beanNames = applicationContext.getBeanNamesForType(Command.class);

        for (var name : beanNames) {
            commands.add((Command) applicationContext.getBean(name));
        }

        return commands;
    }

    @Bean
    public List<TextCommand> textCommands(ApplicationContext applicationContext) {
        var textMessages = new ArrayList<TextCommand>();
        var beanNames = applicationContext.getBeanNamesForType(TextCommand.class);

        for(var name : beanNames){
            textMessages.add((TextCommand) applicationContext.getBean(name));
        }

        return textMessages;
    }
}
