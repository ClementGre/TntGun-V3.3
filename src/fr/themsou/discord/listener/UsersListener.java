package fr.themsou.discord.listener;

import fr.themsou.discord.Counter;
import fr.themsou.discord.Link;
import fr.themsou.main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.function.Consumer;

public class UsersListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){
        if(e.getUser().isBot()) return;

        new Counter().refreshCounters();


        main.guild.getTextChannelById(585009111201087488L).sendMessage("Bienvenue " + e.getUser().getAsMention() + ", pour accéder au discord complet, vous devez accepter le règlement dans <#532139944140079104> (Réagissez avec :white_check_mark:)").queue();

        PrivateChannel privateChannel = e.getUser().openPrivateChannel().complete();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle("Bienvenue sur TntGun !");
        embed.addField("Ce serveur Discord est liée à un serveur Minecraft.", "Vous pouvez voir les informations du serveur dans le salon #ℹ-infos-ℹ", false);
        embed.addField("Vous pouvez transférer vos grades Minecraft sur Discord en liant vos 2 comptes.", "Pour cela, vous devez entrer la commande /discord en jeu.", false);
        embed.addField("Le serveur TntGun vous souhaite de très bon moments sur le serveur.", "Nous vous remercions d'avoir rejoins notre communauté.", false);

        privateChannel.sendMessage(embed.build()).queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});
        privateChannel.sendMessage("Pour accéder au discord complet, vous devez accepter le règlement dans #⚠-règles-⚠  (Réagissez avec :white_check_mark:)").queue(new Consumer<Message>(){public void accept(Message message){}},new Consumer<Throwable>(){public void accept(Throwable throwable){}});



    }
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e){
        if(e.getUser().isBot()) return;

        main.guild.getTextChannelById(585009111201087488L).sendMessage("Au revoir **" + e.getUser().getName() + "**, tu vas nous manquer \uD83D\uDE22").queue();

        main.config.set("discord.list.users." + e.getUser().getAsTag(), null);
        new Link().userLeaveServer(e.getUser());

        new Counter().refreshCounters();

    }
    @Override
    public void onUserUpdateName(UserUpdateNameEvent e){
        if(e.getUser().isBot()) return;

        new Link().userChangeName(e);
    }
    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent e){
        if(e.getUser().isBot()) return;

        new Link().userChangeNick(e);
    }


}
