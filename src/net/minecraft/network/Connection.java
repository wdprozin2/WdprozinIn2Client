/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.mojang.logging.LogUtils
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.connection.UserConnectionImpl
 *  com.viaversion.viaversion.protocol.ProtocolPipelineImpl
 *  de.florianmichael.vialoadingbase.ViaLoadingBase
 *  de.florianmichael.vialoadingbase.netty.event.CompressionReorderEvent
 *  de.florianmichael.viamcp.MCPVLBPipeline
 *  io.netty.bootstrap.Bootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelPipeline
 *  io.netty.channel.DefaultEventLoopGroup
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.SimpleChannelInboundHandler
 *  io.netty.channel.epoll.Epoll
 *  io.netty.channel.epoll.EpollEventLoopGroup
 *  io.netty.channel.epoll.EpollSocketChannel
 *  io.netty.channel.local.LocalChannel
 *  io.netty.channel.local.LocalServerChannel
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.SocketChannel
 *  io.netty.channel.socket.nio.NioSocketChannel
 *  io.netty.handler.timeout.TimeoutException
 *  io.netty.util.AttributeKey
 *  io.netty.util.concurrent.GenericFutureListener
 *  javax.annotation.Nullable
 *  net.minecraft.Util
 *  net.minecraft.network.CipherDecoder
 *  net.minecraft.network.CipherEncoder
 *  net.minecraft.network.CompressionDecoder
 *  net.minecraft.network.CompressionEncoder
 *  net.minecraft.network.Connection$PacketHolder
 *  net.minecraft.network.ConnectionProtocol
 *  net.minecraft.network.PacketBundlePacker
 *  net.minecraft.network.PacketBundleUnpacker
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.PacketSendListener
 *  net.minecraft.network.SkipPacketException
 *  net.minecraft.network.TickablePacketListener
 *  net.minecraft.network.Varint21FrameDecoder
 *  net.minecraft.network.Varint21LengthFieldPrepender
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.BundlerInfo
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketFlow
 *  net.minecraft.network.protocol.game.ClientboundDisconnectPacket
 *  net.minecraft.network.protocol.game.ServerboundChatAckPacket
 *  net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket
 *  net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket
 *  net.minecraft.server.RunningOnDifferentThreadException
 *  net.minecraft.util.LazyLoadedValue
 *  net.minecraft.util.Mth
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 *  org.slf4j.Marker
 *  org.slf4j.MarkerFactory
 *  us.whitedev.helpers.ExceptionPacket
 */
package net.minecraft.network;

import io.netty.channel.ChannelInitializer;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.logging.LogUtils;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.netty.event.CompressionReorderEvent;
import de.florianmichael.viamcp.MCPVLBPipeline;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import net.minecraft.Util;
import net.minecraft.network.CipherDecoder;
import net.minecraft.network.CipherEncoder;
import net.minecraft.network.CompressionDecoder;
import net.minecraft.network.CompressionEncoder;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketBundlePacker;
import net.minecraft.network.PacketBundleUnpacker;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.SkipPacketException;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.Varint21FrameDecoder;
import net.minecraft.network.Varint21LengthFieldPrepender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import us.whitedev.helpers.ExceptionPacket;

public class Connection
extends SimpleChannelInboundHandler<Packet<?>> {
    private static final float AVERAGE_PACKETS_SMOOTHING = 0.75f;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Marker ROOT_MARKER = MarkerFactory.getMarker((String)"NETWORK");
    public static final Marker PACKET_MARKER = (Marker)Util.make((Object)MarkerFactory.getMarker((String)"NETWORK_PACKETS"), p_202569_ -> p_202569_.add(ROOT_MARKER));
    public static final Marker PACKET_RECEIVED_MARKER = (Marker)Util.make((Object)MarkerFactory.getMarker((String)"PACKET_RECEIVED"), p_202562_ -> p_202562_.add(PACKET_MARKER));
    public static final Marker PACKET_SENT_MARKER = (Marker)Util.make((Object)MarkerFactory.getMarker((String)"PACKET_SENT"), p_202557_ -> p_202557_.add(PACKET_MARKER));
    public static final AttributeKey<ConnectionProtocol> ATTRIBUTE_PROTOCOL = AttributeKey.valueOf((String)"protocol");
    public static final LazyLoadedValue<NioEventLoopGroup> NETWORK_WORKER_GROUP = new LazyLoadedValue(() -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build()));
    public static final LazyLoadedValue<EpollEventLoopGroup> NETWORK_EPOLL_WORKER_GROUP = new LazyLoadedValue(() -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build()));
    public static final LazyLoadedValue<DefaultEventLoopGroup> LOCAL_WORKER_GROUP = new LazyLoadedValue(() -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build()));
    private final PacketFlow receiving;
    private final Queue<PacketHolder> queue = Queues.newConcurrentLinkedQueue();
    public Channel channel;
    private SocketAddress address;
    private PacketListener packetListener;
    private Component disconnectedReason;
    private boolean encrypted;
    private boolean disconnectionHandled;
    private int receivedPackets;
    private int sentPackets;
    private float averageReceivedPackets;
    private float averageSentPackets;
    private int tickCount;
    private boolean handlingFault;
    @Nullable
    private volatile Component delayedDisconnect;

    public Connection(PacketFlow p_129482_) {
        this.receiving = p_129482_;
    }

    public void channelActive(ChannelHandlerContext p_129525_) throws Exception {
        super.channelActive(p_129525_);
        this.channel = p_129525_.channel();
        this.address = this.channel.remoteAddress();
        try {
            this.setProtocol(ConnectionProtocol.HANDSHAKING);
        }
        catch (Throwable throwable) {
            LOGGER.error(LogUtils.FATAL_MARKER, "Failed to change protocol to handshake", throwable);
        }
        if (this.delayedDisconnect != null) {
            this.disconnect(this.delayedDisconnect);
        }
    }

    public void setProtocol(ConnectionProtocol p_129499_) {
        this.channel.attr(ATTRIBUTE_PROTOCOL).set((Object)p_129499_);
        this.channel.attr(BundlerInfo.BUNDLER_PROVIDER).set((Object)p_129499_);
        this.channel.config().setAutoRead(true);
        LOGGER.debug("Enabled auto read");
    }

    public void channelInactive(ChannelHandlerContext p_129527_) {
        this.disconnect((Component)Component.translatable((String)"disconnect.endOfStream"));
    }

    public void exceptionCaught(ChannelHandlerContext p_129533_, Throwable p_129534_) {
        if (p_129534_ instanceof SkipPacketException) {
            LOGGER.debug("Skipping packet due to errors", p_129534_.getCause());
        } else {
            boolean flag = !this.handlingFault;
            this.handlingFault = true;
            if (this.channel.isOpen()) {
                if (p_129534_ instanceof TimeoutException) {
                    LOGGER.debug("Timeout", p_129534_);
                    this.disconnect((Component)Component.translatable((String)"disconnect.timeout"));
                } else if (!(p_129534_ instanceof Exception)) {
                    MutableComponent component = Component.translatable((String)"disconnect.genericReason", (Object[])new Object[]{"Internal Exception: " + p_129534_});
                    if (flag) {
                        LOGGER.debug("Failed to sent packet", p_129534_);
                        ConnectionProtocol connectionprotocol = this.getCurrentProtocol();
                        ClientboundLoginDisconnectPacket packet = connectionprotocol == ConnectionProtocol.LOGIN ? new ClientboundLoginDisconnectPacket((Component)component) : new ClientboundDisconnectPacket((Component)component);
                        this.send((Packet<?>)packet, PacketSendListener.thenRun(() -> this.lambda$exceptionCaught$6((Component)component)));
                        this.setReadOnly();
                    } else {
                        LOGGER.debug("Double fault", p_129534_);
                        this.disconnect((Component)component);
                    }
                }
            }
        }
    }

    protected void channelRead0(ChannelHandlerContext p_129487_, Packet<?> packet) {
        if (this.channel.isOpen()) {
            try {
                Connection.genericsFtw(packet, this.packetListener);
            }
            catch (RunningOnDifferentThreadException runningOnDifferentThreadException) {
            }
            catch (RejectedExecutionException rejectedexecutionexception) {
                this.disconnect((Component)Component.translatable((String)"multiplayer.disconnect.server_shutdown"));
            }
            catch (ClassCastException classcastexception) {
                LOGGER.error("Received {} that couldn't be processed", packet.getClass(), (Object)classcastexception);
                this.disconnect((Component)Component.translatable((String)"multiplayer.disconnect.invalid_packet"));
            }
            ++this.receivedPackets;
        }
    }

    private static <T extends PacketListener> void genericsFtw(Packet<T> p_129518_, PacketListener p_129519_) {
        p_129518_.handle(p_129519_);
    }

    public void setListener(PacketListener p_129506_) {
        Validate.notNull((Object)p_129506_, (String)"packetListener", (Object[])new Object[0]);
        this.packetListener = p_129506_;
    }

    public void send(Packet<?> p_129513_) {
        this.send(p_129513_, null);
    }

    public void send(Packet<?> p_243248_, @Nullable PacketSendListener p_243316_) {
        if (p_243248_ instanceof ServerboundCustomPayloadPacket) {
            ServerboundCustomPayloadPacket serverboundCustomPayloadPacket = (ServerboundCustomPayloadPacket)p_243248_;
        }
        if (this.isConnected()) {
            this.flushQueue();
            this.sendPacket(p_243248_, p_243316_);
        } else {
            this.queue.add(new PacketHolder(p_243248_, p_243316_));
        }
    }

    private void sendPacket(Packet<?> p_129521_, @Nullable PacketSendListener p_243246_) {
        ConnectionProtocol connectionprotocol = !(p_129521_ instanceof ExceptionPacket) ? ConnectionProtocol.getProtocolForPacket(p_129521_) : ConnectionProtocol.getProtocolForPacket((Packet)new ServerboundChatAckPacket(0));
        ConnectionProtocol connectionprotocol1 = this.getCurrentProtocol();
        ++this.sentPackets;
        if (connectionprotocol1 != connectionprotocol) {
            if (connectionprotocol == null) {
                throw new IllegalStateException("Encountered packet without set protocol: " + p_129521_);
            }
            LOGGER.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            this.doSendPacket(p_129521_, p_243246_, connectionprotocol, connectionprotocol1);
        } else {
            this.channel.eventLoop().execute(() -> this.doSendPacket(p_129521_, p_243246_, connectionprotocol, connectionprotocol1));
        }
    }

    private void doSendPacket(Packet<?> p_243260_, @Nullable PacketSendListener p_243290_, ConnectionProtocol p_243203_, ConnectionProtocol p_243307_) {
        if (p_243203_ != p_243307_) {
            this.setProtocol(p_243203_);
        }
        ChannelFuture channelfuture = this.channel.writeAndFlush(p_243260_);
        if (p_243290_ != null) {
            channelfuture.addListener(p_243167_ -> {
                if (p_243167_.isSuccess()) {
                    p_243290_.onSuccess();
                } else {
                    Packet packet = p_243290_.onFailure();
                    if (packet != null) {
                        ChannelFuture channelfuture1 = this.channel.writeAndFlush((Object)packet);
                        channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }
                }
            });
        }
        channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    private ConnectionProtocol getCurrentProtocol() {
        return (ConnectionProtocol)this.channel.attr(ATTRIBUTE_PROTOCOL).get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void flushQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            Queue<PacketHolder> queue = this.queue;
            synchronized (queue) {
                PacketHolder connection$packetholder;
                while ((connection$packetholder = this.queue.poll()) != null) {
                    this.sendPacket(connection$packetholder.packet, connection$packetholder.listener);
                }
            }
        }
    }

    public void tick() {
        this.flushQueue();
        PacketListener packetlistener = this.packetListener;
        if (packetlistener instanceof TickablePacketListener) {
            TickablePacketListener tickablepacketlistener = (TickablePacketListener)packetlistener;
            tickablepacketlistener.tick();
        }
        if (!this.isConnected() && !this.disconnectionHandled) {
            this.handleDisconnection();
        }
        if (this.channel != null) {
            this.channel.flush();
        }
        if (this.tickCount++ % 20 == 0) {
            this.tickSecond();
        }
    }

    protected void tickSecond() {
        this.averageSentPackets = Mth.lerp((float)0.75f, (float)this.sentPackets, (float)this.averageSentPackets);
        this.averageReceivedPackets = Mth.lerp((float)0.75f, (float)this.receivedPackets, (float)this.averageReceivedPackets);
        this.sentPackets = 0;
        this.receivedPackets = 0;
    }

    public SocketAddress getRemoteAddress() {
        return this.address;
    }

    public void disconnect(Component p_129508_) {
        if (this.channel == null) {
            this.delayedDisconnect = p_129508_;
        }
        if (this.isConnected()) {
            this.channel.close().awaitUninterruptibly();
            this.disconnectedReason = p_129508_;
        }
    }

    public boolean isMemoryConnection() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }

    public PacketFlow getReceiving() {
        return this.receiving;
    }

    public PacketFlow getSending() {
        return this.receiving.getOpposite();
    }

    public static Connection connectToServer(InetSocketAddress p_178301_, boolean p_178302_) {
        Connection connection = new Connection(PacketFlow.CLIENTBOUND);
        ChannelFuture channelfuture = Connection.connect(p_178301_, p_178302_, connection);
        channelfuture.syncUninterruptibly();
        return connection;
    }

    public static ChannelFuture connect(InetSocketAddress p_290034_, boolean p_290035_, Connection p_290031_) {
        LazyLoadedValue<NioEventLoopGroup> lazyloadedvalue;
        Class<NioSocketChannel> oclass;
        if (Epoll.isAvailable() && p_290035_) {
            oclass = EpollSocketChannel.class;
            lazyloadedvalue = NETWORK_EPOLL_WORKER_GROUP;
        } else {
            oclass = NioSocketChannel.class;
            lazyloadedvalue = NETWORK_WORKER_GROUP;
        }
        return ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)lazyloadedvalue.get())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_129552_) {
                try {
                    p_129552_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                }
                catch (ChannelException channelException) {
                }
                ChannelPipeline channelpipeline = p_129552_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30));
                Connection.configureSerialization(channelpipeline, PacketFlow.CLIENTBOUND);
                channelpipeline.addLast("packet_handler", (ChannelHandler)p_290031_);
            }
        })).channel(oclass)).connect(p_290034_.getAddress(), p_290034_.getPort());
    }

    public static void configureSerialization(ChannelPipeline p_265436_, PacketFlow p_265104_) {
        if (p_265104_ == PacketFlow.CLIENTBOUND && us.whitedev.helpers.Socks5Helper.getInstance().isEnabled()) {
            us.whitedev.helpers.Socks5Helper s5 = us.whitedev.helpers.Socks5Helper.getInstance();
            java.net.InetSocketAddress proxyAddr = new java.net.InetSocketAddress(s5.getHost(), s5.getPort());
            if (s5.hasAuth()) {
                p_265436_.addFirst("socks5", (io.netty.channel.ChannelHandler)new io.netty.handler.proxy.Socks5ProxyHandler(proxyAddr, s5.getUsername(), s5.getPassword()));
            } else {
                p_265436_.addFirst("socks5", (io.netty.channel.ChannelHandler)new io.netty.handler.proxy.Socks5ProxyHandler(proxyAddr));
            }
        }
        PacketFlow packetflow = p_265104_.getOpposite();
        p_265436_.addLast("splitter", (ChannelHandler)new Varint21FrameDecoder()).addLast("decoder", (ChannelHandler)new PacketDecoder(p_265104_)).addLast("prepender", (ChannelHandler)new Varint21LengthFieldPrepender()).addLast("encoder", (ChannelHandler)new PacketEncoder(packetflow)).addLast("unbundler", (ChannelHandler)new PacketBundleUnpacker(packetflow)).addLast("bundler", (ChannelHandler)new PacketBundlePacker(p_265104_));
        if (p_265436_.channel() instanceof SocketChannel && ViaLoadingBase.getInstance().getTargetVersion().getVersion() != 763) {
            UserConnectionImpl user = new UserConnectionImpl(p_265436_.channel(), p_265104_ == PacketFlow.CLIENTBOUND);
            new ProtocolPipelineImpl((UserConnection)user);
            p_265436_.addLast(new ChannelHandler[]{new MCPVLBPipeline((UserConnection)user)});
        }
    }

    public static Connection connectToLocalServer(SocketAddress p_129494_) {
        Connection connection = new Connection(PacketFlow.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)LOCAL_WORKER_GROUP.get())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_129557_) {
                ChannelPipeline channelpipeline = p_129557_.pipeline();
                channelpipeline.addLast("packet_handler", (ChannelHandler)connection);
            }
        })).channel(LocalChannel.class)).connect(p_129494_).syncUninterruptibly();
        return connection;
    }

    public void setEncryptionKey(Cipher p_129496_, Cipher p_129497_) {
        this.encrypted = true;
        this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new CipherDecoder(p_129496_));
        this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new CipherEncoder(p_129497_));
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public boolean isConnected() {
        return this.channel != null && this.channel.isOpen();
    }

    public boolean isConnecting() {
        return this.channel == null;
    }

    public PacketListener getPacketListener() {
        return this.packetListener;
    }

    @Nullable
    public Component getDisconnectedReason() {
        return this.disconnectedReason;
    }

    public void setReadOnly() {
        if (this.channel != null) {
            this.channel.config().setAutoRead(false);
        }
    }

    public void setupCompression(int p_129485_, boolean p_182682_) {
        if (p_129485_ >= 0) {
            if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
                ((CompressionDecoder)this.channel.pipeline().get("decompress")).setThreshold(p_129485_, p_182682_);
            } else {
                this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new CompressionDecoder(p_129485_, p_182682_));
            }
            if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
                ((CompressionEncoder)this.channel.pipeline().get("compress")).setThreshold(p_129485_);
            } else {
                this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new CompressionEncoder(p_129485_));
            }
        } else {
            if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
                this.channel.pipeline().remove("decompress");
            }
            if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
                this.channel.pipeline().remove("compress");
            }
        }
        this.channel.pipeline().fireUserEventTriggered((Object)new CompressionReorderEvent());
    }

    public void handleDisconnection() {
        if (this.channel != null && !this.channel.isOpen()) {
            if (this.disconnectionHandled) {
                LOGGER.warn("handleDisconnection() called twice");
            } else {
                this.disconnectionHandled = true;
                if (this.getDisconnectedReason() != null) {
                    this.getPacketListener().onDisconnect(this.getDisconnectedReason());
                } else if (this.getPacketListener() != null) {
                    this.getPacketListener().onDisconnect((Component)Component.translatable((String)"multiplayer.disconnect.generic"));
                }
            }
        }
    }

    public float getAverageReceivedPackets() {
        return this.averageReceivedPackets;
    }

    public float getAverageSentPackets() {
        return this.averageSentPackets;
    }

    private /* synthetic */ void lambda$exceptionCaught$6(Component component) {
        this.disconnect(component);
    }
}

