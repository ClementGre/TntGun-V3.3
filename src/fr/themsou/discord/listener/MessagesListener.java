package fr.themsou.discord.listener;

import fr.themsou.discord.*;
import fr.themsou.discord.vocal.Music;
import fr.themsou.main.main;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class MessagesListener extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e){
        if(e.getAuthor().isBot()) return;

        new Link().userSendPrivateMessage(e);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e){
        if(e.getAuthor().isBot()) return;

        main.config.set("discord.list.users." + e.getAuthor().getAsTag() + ".msgsend", main.config.getInt("discord.list.users." + e.getAuthor().getAsTag() + ".msgsend") + 1);

        if(e.getChannel().getIdLong() == Support.channelId){
            new Support().userSendMessage(e);

        }else if(e.getChannel().getIdLong() == Chat.channelId){
            new Chat().userSendMessage(e);

        }else if(e.getChannel().getIdLong() == Music.channelId){
            new Music().userSendMessage(e);

        }else if(e.getChannel().getIdLong() == 584732396352569360L){

            e.getMessage().addReaction("❌").queue();
            e.getMessage().addReaction("✅").queue();

        }else if(e.getChannel().getIdLong() == 584732175363211274L){

            e.getMessage().addReaction("👍").queue();
            e.getMessage().addReaction("👎").queue();

        }else if(e.getChannel().getIdLong() == 457198130811764737L){

            e.getMessage().addReaction("❤").queue();
            e.getMessage().addReaction("💔").queue();
        }

    }
    @Override
    public void onMessageDelete(MessageDeleteEvent e){


    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e){
        if(e.getUser().isBot()) return;


        if(e.getChannel().getIdLong() == Music.channelId){
            new Music().userReact(e);

        }else if(e.getChannel().getIdLong() == 532139944140079104L && e.getMessageIdLong() == 586206292042055681L){ // Règles lues


            Role memberRole = main.jda.getRolesByName("Membre", true).get(0);

            main.guild.addRoleToMember(e.getMember(), memberRole).queue();
            main.guild.getTextChannelById(414143183996452864L).sendMessage(":gift: Bienvenue <@" + e.getMember().getId() + "> sur TntGun !").addFile(new File("plugins/TntGun/files/bienvenue.gif")).queue();
            new Counter().refreshCounters();


        }else if(e.getChannel().getIdLong() == Roles.channelId && e.getMessageIdLong() == Roles.messageId){

            new Roles().onReact(e);

        }
    }
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e){
        if(e.getUser().isBot()) return;

        if(e.getChannel().getIdLong() == Roles.channelId && e.getMessageIdLong() == Roles.messageId){

            new Roles().onUnreact(e);

        }

    }


}
