package com.kasp.rankedbot.listener;

import com.kasp.rankedbot.EmbedType;
import com.kasp.rankedbot.config.Config;
import com.kasp.rankedbot.instance.Player;
import com.kasp.rankedbot.instance.Queue;
import com.kasp.rankedbot.instance.cache.PlayerCache;
import com.kasp.rankedbot.instance.cache.QueuesCache;
import com.kasp.rankedbot.instance.embed.Embed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class QueueJoin extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {


        // leave vc
        if (event.getChannelJoined() != null) {
            if (QueuesCache.containsQueue(event.getChannelJoined().getId())) {
                TextChannel alerts = event.getGuild().getTextChannelById(Config.getValue("alerts-channel"));

                String ID = event.getMember().getId();
                Queue queue = QueuesCache.getQueue(event.getChannelJoined().getId());

                Player player = PlayerCache.getPlayer(ID);

                if (player.isBanned()) {
                    event.getGuild().kickVoiceMember(event.getMember()).queue();

                    Embed embed = new Embed(EmbedType.ERROR, "You Cannot Queue", "", 1);
                    embed.addField("It appears that you've been banned", "If this is a mistake, please do `=fix`. If it still doesn't remove your banned role, you can open an appeal ticket", false);
                    alerts.sendMessage(event.getMember().getAsMention()).setEmbeds(embed.build()).queue();
                }
                else {
                    queue.addPlayer(player);
                }
            }
        }
        // left vc
        if (event.getChannelLeft() != null) {
            if (QueuesCache.containsQueue(event.getChannelLeft().getId())) {
                String ID = event.getMember().getId();
                Queue queue = QueuesCache.getQueue(event.getChannelLeft().getId());

                Player player = PlayerCache.getPlayer(ID);

                queue.removePlayer(player);
            }
        }
    }
}
