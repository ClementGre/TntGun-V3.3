package fr.themsou.discord.listener;

import fr.themsou.discord.Counter;
import fr.themsou.discord.vocal.Music;
import fr.themsou.main.main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ChannelsListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e){

        if(e.getChannelLeft().getIdLong() == Music.vocalId){
            new Music().playerVocalDisconect(e.getChannelLeft().getMembers().size() - 1);
        }

    }
    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e){

        if(e.getChannelLeft().getIdLong() == Music.vocalId){
            new Music().playerVocalDisconect(e.getChannelLeft().getMembers().size() - 1);
        }

    }

    @Override
    public void onReady(ReadyEvent e){

        if(!main.isBotStarted){
            main.isBotStarted = true;

            System.out.println("[TntGun] : Le BOT est connecté à Discord !");

            main.jda = fr.themsou.diffusion.main.jda;
            main.guild = main.jda.getGuildById(317629373404413953L);

            new Thread(() -> {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                main.jda.addEventListener(new MessagesListener());
                main.jda.addEventListener(new UsersListener());

                new Counter().refreshCounters();
                //main.jda.addEventListener(new ChannelsListener()); Already added with diffusion

                /*for(String playerName : main.config.getConfigurationSection("").getKeys(false)){

                    if(main.config.contains(playerName + ".discord")) {
                        String userName = main.config.getString(playerName + ".discord");

                        List<Member> members = main.guild.getMembersByName(userName, false);
                        if (members.size() >= 1) {
                            Member member = members.get(0);
                            System.out.println("translating " + member.getUser().getAsTag());
                            main.config.set(playerName + ".discord", member.getUser().getAsTag());
                        } else {
                            System.out.println("can't translate" + userName);
                            main.config.set(playerName + ".discord", null);
                        }
                    }
                }*/

            }).start();
        }
    }
}
