package us.whitedev.helpers;

import com.github.steveice10.packetlib.ProxyInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.proxy.Socks5ProxyHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import net.minecraft.network.protocol.PacketFlow;
import us.whitedev.commands.CommandManager;
import us.whitedev.commands.impl.Socks5Command;

public class Socks5Helper {
    private static Socks5Helper instance;
    private boolean enabled = false;
    private String host = "127.0.0.1";
    private int port = 1080;
    private String username = "";
    private String password = "";
    private final File configFile = new File("xynis_socks5.txt");

    private Socks5Helper() {
        this.load();
    }

    public static synchronized Socks5Helper getInstance() {
        if (instance == null) {
            instance = new Socks5Helper();
        }
        return instance;
    }

    public static void initHook() {
        try {
            CommandManager.getManager().addCommands(new Socks5Command(), new us.whitedev.commands.impl.UpdateCommand());
            System.out.println("[WdprozinIn2Client] SOCKS5 and Update commands registered successfully!");
            us.whitedev.updater.AutoUpdater.getInstance().checkOnStartup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyProxy(ChannelPipeline pipeline, PacketFlow flow) {
        try {
            Socks5Helper s5 = getInstance();
            if (flow == PacketFlow.CLIENTBOUND && s5.isEnabled()) {
                System.out.println("[Xynis SOCKS5] Routing connection through " + s5.getHost() + ":" + s5.getPort());
                InetSocketAddress addr = new InetSocketAddress(s5.getHost(), s5.getPort());
                if (s5.hasAuth()) {
                    pipeline.addFirst("socks5", (ChannelHandler)new Socks5ProxyHandler(addr, s5.getUsername(), s5.getPassword()));
                } else {
                    pipeline.addFirst("socks5", (ChannelHandler)new Socks5ProxyHandler(addr));
                }
            }
        } catch (Exception e) {
            System.err.println("[Xynis SOCKS5] Error applying proxy handler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isEnabled() {
        return this.enabled && this.host != null && !this.host.isEmpty() && this.port > 0;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.save();
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean hasAuth() {
        return this.username != null && !this.username.isEmpty();
    }

    public void setProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.username = "";
        this.password = "";
        this.save();
    }

    public void setProxy(String host, int port, String user, String pass) {
        this.host = host;
        this.port = port;
        this.username = user != null ? user : "";
        this.password = pass != null ? pass : "";
        this.save();
    }

    public ProxyInfo getPacketLibProxy() {
        if (!this.isEnabled()) {
            return null;
        }
        InetSocketAddress addr = new InetSocketAddress(this.host, this.port);
        if (this.hasAuth()) {
            return new ProxyInfo(ProxyInfo.Type.SOCKS5, addr, this.username, this.password);
        }
        return new ProxyInfo(ProxyInfo.Type.SOCKS5, addr);
    }

    public void load() {
        if (!this.configFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(this.configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                String key = parts[0].trim().toLowerCase();
                String val = parts[1].trim();
                switch (key) {
                    case "enabled":
                        this.enabled = Boolean.parseBoolean(val);
                        break;
                    case "host":
                        this.host = val;
                        break;
                    case "port":
                        try {
                            this.port = Integer.parseInt(val);
                        } catch (NumberFormatException ignored) {}
                        break;
                    case "username":
                    case "user":
                        this.username = val;
                        break;
                    case "password":
                    case "pass":
                        this.password = val;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(this.configFile))) {
            writer.println("# Xynis SOCKS5 Proxy Configuration");
            writer.println("enabled=" + this.enabled);
            writer.println("host=" + (this.host != null ? this.host : "127.0.0.1"));
            writer.println("port=" + this.port);
            writer.println("username=" + (this.username != null ? this.username : ""));
            writer.println("password=" + (this.password != null ? this.password : ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStatusDescription() {
        if (!this.enabled) {
            return "&cDesativado";
        }
        String authStr = this.hasAuth() ? " &8(&7user: &f" + this.username + "&8)" : "";
        return "&aAtivado &8-> &f" + this.host + ":" + this.port + authStr;
    }
}
