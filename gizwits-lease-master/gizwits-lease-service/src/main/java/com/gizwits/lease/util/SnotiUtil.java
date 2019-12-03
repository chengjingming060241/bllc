package com.gizwits.lease.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.channels.spi.SelectorProvider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SnotiUtil {

    private static final Logger logger = LoggerFactory.getLogger(SnotiUtil.class);

    private ChannelFuture channelFuture;
    private EventLoopGroup workerGroup;

    private boolean loginSuccess = false;
    private Message message;

    public SnotiUtil(String productKey, String authId, String authSecret, String subkey) {
        LoginData data = new LoginData();
        data.setProductKey(productKey);
        data.setAuthId(authId);
        data.setAuthSecret(authSecret);
        data.setSubkey(subkey);
        data.setEvents(Arrays.asList(Events.STATUS_KV));
        Message message = new Message();
        message.setPrefetchCount(50);
        message.setCmd(Command.LOGIN);
        message.setData(Arrays.asList(data));
        this.message = message;
    }

    public boolean login() throws InterruptedException {
        createClient(this);

        double time = 0d;
        while (true){
            if (time > 2d){
                break;
            }

            if(this.loginSuccess){
                break;
            }

            Thread.sleep(200);
            time += 0.2d;
        }

        destroy();

        return this.loginSuccess;
    }


    private void createClient(SnotiUtil snotiUtil) {
        workerGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("--client--"), SelectorProvider.provider());

        GenericFutureListener eventLister = getEventLister();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channelFactory(NioSocketChannel::new);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.TCP_NODELAY, true)//TCP立即发包
                    .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 128 * 1024)) //设置高低水位的方式进行限流
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(getChannelHandler(snotiUtil));

            channelFuture = bootstrap.connect("snoti.gizwits.com", 2017).addListener(eventLister).sync();

            channelFuture.channel().closeFuture().addListener(eventLister);


        } catch (Exception e) {

            logger.error("Client start exception", e);
            throw new RuntimeException("Client start exception, ", e);

        }
    }

    private void destroy(){
        if (this.workerGroup != null && channelFuture != null) {
            channelFuture.channel().close();
            if (this.workerGroup != null) {
                this.workerGroup.shutdownGracefully();
            }
            logger.info("noti client  destory");
            this.workerGroup = null;
            this.channelFuture = null;
        }
    }

    class SnotiChannelHandler extends SimpleChannelInboundHandler<String> {

        private SnotiUtil snotiUtil;

        private Gson gson = new Gson();

        private static final String lineSeparator = "\n";

        public SnotiChannelHandler(SnotiUtil snotiUtil) {
            this.snotiUtil = snotiUtil;
        }

        /**
         * 登入认证
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(gson.toJson(snotiUtil.message) + lineSeparator);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            if ((msg != "") && (msg != null) && (!msg.equalsIgnoreCase("null"))) {

                JSONObject messageObject = JSONObject.parseObject(msg);
                String cmd = messageObject.getString("cmd");

                switch (Command.getCmd(cmd)) {

                    case LOGIN_RES:
                        boolean isSuceess = messageObject.getJSONObject("data").getBoolean("result");
                        if (isSuceess) {
                            logger.info("client login success");
                            snotiUtil.loginSuccess = true;
                        } else {
                            logger.error("client  login  error:{}", msg);
                        }
                        ctx.close();
                        break;
                    default:
                        logger.info("{}", msg);
                        break;
                }

            }
        }
    }



    private ChannelHandler getChannelHandler(SnotiUtil snotiLoginUtil) {

        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustManager()}, new java.security.SecureRandom());
                SSLEngine sslEngine = sslContext.createSSLEngine();
                sslEngine.setUseClientMode(true);
                ch.pipeline().addLast(new SslHandler(sslEngine));
                ch.pipeline().addLast(new IdleStateHandler(1, 1, 0, TimeUnit.MINUTES));// 心跳检查
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new SnotiChannelHandler(snotiLoginUtil));

            }
        };
    }

    private X509TrustManager trustManager() {
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return tm;
    }

    private GenericFutureListener getEventLister() {
        return (ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                logger.info("===EventLister===");
            }
        };
    }

    class Message {

        @SerializedName("cmd")
        private Command cmd;
        @SerializedName("msg_id")
        private String msgId;
        @SerializedName("prefetch_count")
        private int prefetchCount = 50;
        @SerializedName("data")
        private List<LoginData> data;

        public Message() {
        }

        public Command getCmd() {
            return cmd;
        }

        public void setCmd(Command cmd) {
            this.cmd = cmd;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public int getPrefetchCount() {
            return prefetchCount;
        }

        public void setPrefetchCount(int prefetchCount) {
            this.prefetchCount = prefetchCount;
        }

        public List<LoginData> getData() {
            return data;
        }

        public void setData(List<LoginData> data) {
            this.data = data;
        }

    }

    class LoginData {
        @SerializedName("cmd")
        private DataCommand cmd;
        @SerializedName("source")
        private String source = "noti";
        @SerializedName("product_key")
        private String productKey;
        @SerializedName("auth_id")
        private String authId;
        @SerializedName("auth_secret")
        private String authSecret;
        @SerializedName("subkey")
        private String subkey;
        @SerializedName("events")
        private List<Events> events;

        public DataCommand getCmd() {
            return cmd;
        }

        public void setCmd(DataCommand cmd) {
            this.cmd = cmd;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getAuthId() {
            return authId;
        }

        public void setAuthId(String authId) {
            this.authId = authId;
        }

        public String getAuthSecret() {
            return authSecret;
        }

        public void setAuthSecret(String authSecret) {
            this.authSecret = authSecret;
        }

        public String getSubkey() {
            return subkey;
        }

        public void setSubkey(String subkey) {
            this.subkey = subkey;
        }

        public List<Events> getEvents() {
            return events;
        }

        public void setEvents(List<Events> events) {
            this.events = events;
        }
    }

    enum DataCommand{

        @SerializedName("write_attrs")
        WRITE_ATTRS("write_attrs"),
        @SerializedName("write")
        WRITE("write"),
        @SerializedName("write_v1")
        WRITE_V1("write_v1");

        private String name;

        DataCommand(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    enum Events {

        @SerializedName("device.status.kv")
        STATUS_KV("device.status.kv");//设备上报数据点业务指令

        private String name;

        Events(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    enum Command {

        @SerializedName("login_req")
        LOGIN("login_req"),//连接与登陆
        @SerializedName("login_res")
        LOGIN_RES("login_res");//  登入回复

        private String name;

        Command(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static Command getCmd(String name) {
            for (Command cmd : values()) {
                if (cmd.getName().equals(name)) {
                    return cmd;
                }
            }
            return null;
        }

    }


}
