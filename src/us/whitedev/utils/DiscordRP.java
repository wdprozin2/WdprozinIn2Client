package us.whitedev.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

public final class DiscordRP
implements ReadyCallback {
    public static final String APPLICATION_ID = "1126547571779842158";
    public static boolean rpSwitch = true;
    private static final Minecraft mc = Minecraft.getInstance();
    DiscordRichPresence richPresence = new DiscordRichPresence.Builder("Client Initialization").setBigImage("logo", "WdprozinIn2Client 6.2").setDetails("Loading...").setStartTimestamps(System.currentTimeMillis()).build();
    private boolean enabled = true;

    public void runDiscordRP() {
        this.init();
        this.startTask();
        DiscordRPC.discordUpdatePresence((DiscordRichPresence)this.richPresence);
    }

    public void apply(DiscordUser discordUser) {
        System.out.println("Initialized DiscordRichPresence API.");
    }

    private void init() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator, user.userId)).build();
        DiscordRPC.discordInitialize(APPLICATION_ID, (DiscordEventHandlers)handlers, (boolean)true);
    }

    public void startTask() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            if (rpSwitch) {
                if (!this.enabled) {
                    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator, user.userId)).build();
                    DiscordRPC.discordInitialize(APPLICATION_ID, (DiscordEventHandlers)handlers, (boolean)true);
                    this.enabled = true;
                }
                this.richPresence.details = DiscordRP.mc.player == null ? "Disconnected" : "Connected";
                this.richPresence.state = "Discord: wdprozin_";
                DiscordRPC.discordUpdatePresence((DiscordRichPresence)this.richPresence);
            } else {
                DiscordRPC.discordShutdown();
                this.enabled = false;
            }
        }, 10L, 10L, TimeUnit.SECONDS);
    }
}
