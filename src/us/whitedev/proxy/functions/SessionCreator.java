package us.whitedev.proxy.functions;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import us.whitedev.helpers.MessageHelper;
import us.whitedev.helpers.Socks5Helper;
import us.whitedev.proxy.listeners.BotListener;
import us.whitedev.proxy.listeners.ServerTransferListener;
import us.whitedev.proxy.repository.BotsRep;
import us.whitedev.proxy.repository.OptionsRep;
import us.whitedev.proxy.repository.SessionRep;
import us.whitedev.proxy.utils.RandomHelper;
import us.whitedev.proxy.utils.SessionType;

public class SessionCreator {
    private final SessionRep sessionRep = SessionRep.getInstance();
    private final OptionsRep optionsRep = OptionsRep.getInstance();
    private final BotsRep botsRep = BotsRep.getInstance();
    private final RandomHelper randomHelper = RandomHelper.getRandomHelper();
    private final MessageHelper msgHelper = new MessageHelper();

    public void initSession(Session originalSession, boolean isBot, String name, String password) {
        SessionType sessionType = SessionType.CRACKED;
        if (name == null || name.isEmpty()) {
            this.optionsRep.setUsername(this.randomHelper.getRandomString(this.randomHelper.getRandomInt(6, 12), false));
        } else if (password != null && !password.isEmpty()) {
            this.optionsRep.setUsername(name);
            this.optionsRep.setPassword(password);
            sessionType = SessionType.PREMIUM;
        } else {
            this.optionsRep.setUsername(name);
        }
        Session session = null;
        switch (sessionType) {
            case CRACKED: {
                session = this.createNewSession(originalSession, isBot);
                break;
            }
            case PREMIUM: {
                session = this.createNewPremiumSession(originalSession, isBot);
            }
        }
        if (session != null && session.isConnected()) {
            this.msgHelper.sendMessage("&fProxy &8-> &7A new session has &ajoined &7the server! &8(&f" + this.optionsRep.getUsername() + "&8)", true);
        } else {
            this.msgHelper.sendMessage("&fProxy &8-> &cFailed &7to connect new session!", true);
        }
    }

    public Session createNewSession(Session originalSession, boolean isBot) {
        MinecraftProtocol protocol = new MinecraftProtocol(this.optionsRep.getUsername() + (Serializable)(isBot ? Integer.valueOf(this.botsRep.getConnectedSessions().size()) : ""));
        ProxyInfo proxyInfo = Socks5Helper.getInstance().getPacketLibProxy();
        TcpClientSession session = proxyInfo != null
            ? new TcpClientSession(this.optionsRep.getServerIp(), this.optionsRep.getServerPort(), (PacketProtocol)protocol, proxyInfo)
            : new TcpClientSession(this.optionsRep.getServerIp(), this.optionsRep.getServerPort(), (PacketProtocol)protocol);
        if (isBot) {
            this.botsRep.addSession((Session)session);
            session.connect();
            session.addListener((SessionListener)new BotListener());
        } else {
            this.sessionRep.setSession((Session)session);
            session.connect();
            session.addListener((SessionListener)new ServerTransferListener(originalSession));
        }
        return session;
    }

    public Session createNewPremiumSession(Session originalSession, boolean isBot) {
        MicrosoftAuthResult result;
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            result = authenticator.loginWithCredentials(this.optionsRep.getUsername(), this.optionsRep.getPassword());
        }
        catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }
        Session session = this.getSession(result);
        if (isBot) {
            this.botsRep.addSession(session);
            session.connect();
            session.addListener((SessionListener)new BotListener());
        } else {
            this.sessionRep.setSession(session);
            session.connect();
            session.addListener((SessionListener)new ServerTransferListener(originalSession));
        }
        return session;
    }

    @NotNull
    private Session getSession(MicrosoftAuthResult result) {
        String uuid = result.getProfile().getId();
        BigInteger uuidGen0 = new BigInteger(uuid.substring(0, 16), 16);
        BigInteger uuidGen1 = new BigInteger(uuid.substring(16, 32), 16);
        UUID parsedUuid = new UUID(uuidGen0.longValue(), uuidGen1.longValue());
        GameProfile gameProfile = new GameProfile(parsedUuid, result.getProfile().getName());
        MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, result.getAccessToken());
        ProxyInfo proxyInfo = Socks5Helper.getInstance().getPacketLibProxy();
        if (proxyInfo != null) {
            return new TcpClientSession(this.optionsRep.getServerIp(), this.optionsRep.getServerPort(), (PacketProtocol)protocol, proxyInfo);
        }
        return new TcpClientSession(this.optionsRep.getServerIp(), this.optionsRep.getServerPort(), (PacketProtocol)protocol);
    }
}
