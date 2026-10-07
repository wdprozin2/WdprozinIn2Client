/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  de.florianmichael.viamcp.ViaMCP
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.main.Main
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.ResourceLocation
 */
package us.whitedev;

import com.mojang.blaze3d.platform.NativeImage;
import de.florianmichael.viamcp.ViaMCP;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import us.whitedev.commands.CommandManager;
import us.whitedev.commands.impl.AuthorsCommand;
import us.whitedev.commands.impl.BypassListCommand;
import us.whitedev.commands.impl.ConfigCommand;
import us.whitedev.commands.impl.CrashCommand;
import us.whitedev.commands.impl.DetectCommand;
import us.whitedev.commands.impl.ExploitCommand;
import us.whitedev.commands.impl.FakeGmCommand;
import us.whitedev.commands.impl.HelpCommand;
import us.whitedev.commands.impl.ProxyCommand;
import us.whitedev.commands.impl.Socks5Command;
import us.whitedev.commands.impl.StopCommand;
import us.whitedev.crashers.CrashManager;
import us.whitedev.crashers.Crasher;
import us.whitedev.crashers.impl.Cipher1;
import us.whitedev.crashers.impl.Creative1;
import us.whitedev.crashers.impl.Echo1;
import us.whitedev.crashers.impl.Echo2;
import us.whitedev.crashers.impl.Flare1;
import us.whitedev.crashers.impl.Lux1;
import us.whitedev.crashers.impl.Nova1;
import us.whitedev.crashers.impl.Nova2;
import us.whitedev.crashers.impl.Onyx1;
import us.whitedev.crashers.impl.Onyx2;
import us.whitedev.crashers.impl.Pulse1;
import us.whitedev.crashers.impl.Rogue1;
import us.whitedev.crashers.impl.Rogue2;
import us.whitedev.crashers.impl.Shift1;
import us.whitedev.crashers.impl.Shift2;
import us.whitedev.crashers.impl.Shock1;
import us.whitedev.crashers.impl.Smite1;
import us.whitedev.crashers.impl.Spectre1;
import us.whitedev.crashers.impl.Spectre2;
import us.whitedev.crashers.impl.Spectre3;
import us.whitedev.crashers.impl.Strong1;
import us.whitedev.crashers.impl.Void1;
import us.whitedev.crashers.impl.Vortex1;
import us.whitedev.crashers.impl.Vortex2;
import us.whitedev.crashers.impl.Vortex3;
import us.whitedev.crashers.impl.Vortex4;
import us.whitedev.exploits.ExploitManager;
import us.whitedev.exploits.impl.BundleExploit;
import us.whitedev.exploits.impl.BungeeExploit;
import us.whitedev.exploits.impl.CommandsExploit;
import us.whitedev.exploits.impl.ConsoleSpammer;
import us.whitedev.exploits.impl.EntityExploit;
import us.whitedev.exploits.impl.EssentialsExploit;
import us.whitedev.exploits.impl.FaweExploit;
import us.whitedev.exploits.impl.ForceOpExploit;
import us.whitedev.exploits.impl.LPXExploit;
import us.whitedev.exploits.impl.Log4JExploit;
import us.whitedev.exploits.impl.LogsExploit;
import us.whitedev.exploits.impl.LuckPermsExploit;
import us.whitedev.exploits.impl.MVCExploit;
import us.whitedev.exploits.impl.PVExploit;
import us.whitedev.exploits.impl.PexExploit;
import us.whitedev.exploits.impl.PluginExploit;
import us.whitedev.exploits.impl.PurpurExploit;
import us.whitedev.exploits.impl.SignExploit;
import us.whitedev.exploits.impl.WorldEditExploit;
import us.whitedev.gui.clickgui.ClickGui;
import us.whitedev.helpers.AccountHelper;
import us.whitedev.utils.ClasspathScanner;
import us.whitedev.utils.DiscordRP;

public class Main2PacketsClient {
    private final AccountHelper accountHelper = new AccountHelper();
    public static Main2PacketsClient instance;
    public static boolean DEBUG_MODE;
    public static final String VERSION = "6.2";

    public static Main2PacketsClient getInstance() {
        if (instance == null) {
            instance = new Main2PacketsClient();
        }
        return instance;
    }

    public void start() {
        // Enforce cryptographic license verification before starting
        us.whitedev.security.LicenseManager.getInstance().verify();

        try {
            ViaMCP.create();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.LOGGER.info("WdprozinIn2Client Initialization...");
        this.initializeClient();
        new Thread(ClickGui::initGui).start();
    }

    private void initializeClient() {
        Minecraft.LOGGER.info("Registration Commands...");
        CommandManager.getManager().addCommands(new StopCommand(), new AuthorsCommand(), new HelpCommand(), new CrashCommand(), new ExploitCommand(), new BypassListCommand(), new FakeGmCommand(), new DetectCommand(), new ProxyCommand(), new ConfigCommand());
        Minecraft.LOGGER.info("Registration Crash Methods...");
        try {
            ArrayList<Crasher> instances = new ArrayList<>();
            for (Class<?> cls : ClasspathScanner.getClassesInPackage("us.whitedev.crashers.impl")) {
                if (!Crasher.class.isAssignableFrom(cls) || cls.isInterface()) continue;
                try {
                    Crasher inst = (Crasher) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    instances.add(inst);
                }
                catch (NoSuchMethodException ignored) {
                    Minecraft.LOGGER.warn("Crasher class without no-arg constructor: {}", (Object)cls.getName());
                }
            }
            if (!instances.isEmpty()) {
                CrashManager.getManager().addMethod(instances.toArray(new Crasher[0]));
            }
        }
        catch (Exception e) {
            Minecraft.LOGGER.warn("Auto-registration of crash methods failed, falling back to manual registration: {}", (Object)e.getMessage());
            CrashManager.getManager().addMethod(new Strong1(), new Rogue1(), new Rogue2(), new Pulse1(), new Shift1(), new Shift2(), new Nova1(), new Nova2(), new Creative1(), new Vortex1(), new Vortex2(), new Vortex3(), new Vortex4(), new Onyx1(), new Onyx2(), new Echo1(), new Echo2(), new Cipher1(), new Spectre1(), new Spectre2(), new Spectre3(), new Smite1(), new Shock1(), new Void1(), new Lux1(), new Flare1());
        }
        Minecraft.LOGGER.info("Registration Crash Exploits...");
        ExploitManager.getManager().addExploit(new BungeeExploit(), new ConsoleSpammer(), new CommandsExploit(), new EntityExploit(), new EssentialsExploit(), new FaweExploit(), new ForceOpExploit(), new Log4JExploit(), new LuckPermsExploit(), new BundleExploit(), new MVCExploit(), new PexExploit(), new SignExploit(), new PluginExploit(), new LogsExploit(), new LPXExploit(), new WorldEditExploit(), new PVExploit(), new PurpurExploit());
        this.registerBGImage();
        new DiscordRP().runDiscordRP();
        Minecraft.LOGGER.info("Loading accounts...");
        this.accountHelper.readAccountsMap();
    }

    private void registerBGImage() {
        Minecraft.LOGGER.info("Registration Custom Images...");
        InputStream inputStream = Main.class.getResourceAsStream("/client/images/background.png");
        try {
            if (inputStream != null) {
                NativeImage image = NativeImage.read((InputStream)inputStream);
                ResourceLocation customBackground = new ResourceLocation("xynis", "textures/gui/custom_background.png");
                Minecraft.getInstance().getTextureManager().register(customBackground, (AbstractTexture)new DynamicTexture(image));
                inputStream.close();
            }
        }
        catch (IOException e) {
            Minecraft.LOGGER.info("Error while reading images!");
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return "Main2PacketsClient [1.20.1] - 6.2";
    }

    static {
        DEBUG_MODE = false;
    }
}

