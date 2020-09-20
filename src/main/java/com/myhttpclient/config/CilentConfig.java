package com.myhttpclient.config;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import com.myhttpclient.common.Cookies;
import com.myhttpclient.common.SSL;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.*;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

/**
 * 全局配置和获取CloseableHttpClient
 */
public class CilentConfig {

    private CilentConfig() {
    }

    public static CilentConfig custom() {
        return new CilentConfig();
    }

    //使用自定义消息解析器/编写器自定义从数据流解析和写出HTTP消息的方式。
    private HttpMessageParserFactory<HttpResponse> responseParserFactory() {
        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
            @Override
            public HttpMessageParser<HttpResponse> create(
                    SessionInputBuffer buffer, MessageConstraints constraints) {
                LineParser lineParser = new BasicLineParser() {
                    @Override
                    public Header parseHeader(final CharArrayBuffer buffer) {
                        try {
                            return super.parseHeader(buffer);
                        } catch (ParseException ex) {
                            return new BasicHeader(buffer.toString(), null);
                        }
                    }
                };
                return new DefaultHttpResponseParser(
                        buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
                    @Override
                    protected boolean reject(final CharArrayBuffer line, int count) {
                        return false;
                    }
                };
            }
        };
        return responseParserFactory;
    }

    private HttpMessageWriterFactory<HttpRequest> requestWriterFactory() {
        return new DefaultHttpRequestWriterFactory();
    }

    //使用自定义连接工厂自定义传出HTTP连接的初始化过程。
    //除了标准的连接配置参数之外，
    //HTTP连接工厂还可以定义消息解析器/编写器例程，以供各个连接使用。
    private HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory() {
        return new ManagedHttpClientConnectionFactory(requestWriterFactory(), responseParserFactory());
    }

    //为支持的协议方案创建自定义连接套接字工厂的注册表。
    private Registry<ConnectionSocketFactory> socketFactoryRegistry() {
        //客户端HTTP连接对象在完全初始化时可以绑定到任意网络套接字。
        //网络套接字初始化、连接到远程地址和绑定到本地地址的过程由连接套接字工厂控制。
        //安全连接的SSL上下文可以基于系统或应用程序特定的属性创建。
        //SSLContext sslcontext = SSLContexts.createSystemDefault();
        SSLContext sslcontext = null;
        try {
            sslcontext = SSL.custom().createIgnoreVerifySSL();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        return socketFactoryRegistry;
    }

    //使用自定义DNS解析程序覆盖系统DNS解析。
    private DnsResolver dnsResolver() {
        return new SystemDefaultDnsResolver() {
            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[]{InetAddress.getByAddress(new byte[]{127, 0, 0, 1})};
                } else {
                    return super.resolve(host);
                }
            }
        };
    }

    //自定义连接池
    private PoolingHttpClientConnectionManager connManager() {
        return new PoolingHttpClientConnectionManager(
                socketFactoryRegistry());//, connFactory(), dnsResolver()
    }

    //默认连接池连接池
    private PoolingHttpClientConnectionManager myConnManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        return connManagerConfig(poolingHttpClientConnectionManager);
    }

    //连接池配置
    private PoolingHttpClientConnectionManager connManagerConfig(PoolingHttpClientConnectionManager connManager) {
        //创建套接字配置
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
        //将连接管理器配置为在默认情况下或针对特定主机使用套接字配置。
        connManager.setDefaultSocketConfig(socketConfig);
        //connManager.setSocketConfig(new HttpHost("test.qiyekexie.com", 80), socketConfig);
        //检测有效连接的间隔
        connManager.setValidateAfterInactivity(1000);

        //创建消息约束
        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200)
                .setMaxLineLength(2000)
                .build();
        //创建连接配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints)
                .build();
        //将连接管理器配置为在默认情况下
        connManager.setDefaultConnectionConfig(connectionConfig);
        //针对特定主机使用连接配置。
        //connManager.setConnectionConfig(new HttpHost("test.qiyekexie.com", 80), ConnectionConfig.DEFAULT);

        //设置池最大连接数
        connManager.setMaxTotal(100);
        //设置每个路由的默认最大连接数
        connManager.setDefaultMaxPerRoute(20);
        //设置目标主机对应的路由的最大连接数，会覆盖setDefaultMaxPerRoute设置的默认值
        //connManager.setMaxPerRoute(new HttpRoute(new HttpHost("test.qiyekexie.com", 80)), 20);
        return connManager;
    }

    //使用自定义cookie存储
    private CookieStore cookieStore() {
        return Cookies.custom().getCookieStore();
    }

    //一个简单的示例，该示例使用HttpClient,对需要用户身份验证的目标站点执行HTTP请求。
    private CredentialsProvider credentialsProvider() {
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
       /* credsProvider.setCredentials(
                new AuthScope("httpbin.org", 80),
                new UsernamePasswordCredentials("user", "passwd"));*/
        return credsProvider;
    }

    //创建全局请求配置
    private RequestConfig defaultRequestConfig() {
        return RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                // 配置请求的超时设置
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                // .setProxy(new HttpHost("myotherproxy", 8080)) //设置代理请求
                .build();
    }

    // 请求重试处理
    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };

    //使用给定的自定义依赖项和配置创建HttpClient。
    public CloseableHttpClient getHttpclient(DefaultProxyRoutePlanner proxy) {
        CloseableHttpClient closeableHttpClient = null;
        if (proxy == null) {
            closeableHttpClient = HttpClients.custom()   // HttpClientBuilder.create().build()
                    .setConnectionManager(connManager())
                    .setDefaultCookieStore(cookieStore())
                    // .setDefaultCredentialsProvider(credentialsProvider())
                    // .setProxy(new HttpHost("myproxy", 8080))  //设置代理请求
                    .setDefaultRequestConfig(defaultRequestConfig())
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
        } else {
            closeableHttpClient = HttpClients.custom()   // HttpClientBuilder.create().build()
                    .setConnectionManager(connManager())
                    .setDefaultCookieStore(cookieStore())
                    .setRoutePlanner(proxy)
                    .setDefaultRequestConfig(defaultRequestConfig())
                    .setRetryHandler(httpRequestRetryHandler)
                    .build();
        }
        IdleConnectionMonitorThread idleConnectionMonitor = new IdleConnectionMonitorThread(connManager());
        idleConnectionMonitor.start();
        return closeableHttpClient;
    }

    //执行上下文可以在本地自定义,
    // 一旦执行了请求，就可以使用本地上下文来检查更新的状态和受请求执行影响的各种对象。
    public HttpClientContext context() {
        HttpClientContext context = Cookies.custom().getContext();
        //在本地上下文级别设置的上下文属性将优先于在客户端级别设置的属性。
        //context.setCredentialsProvider(credentialsProvider());
        return context;
    }

    //用于监控空闲的连接池连接
    private final class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;
        private static final int MONITOR_INTERVAL_MS = 2000;
        private static final int IDLE_ALIVE_MS = 5000;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
            this.shutdown = false;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(MONITOR_INTERVAL_MS);
                        // 关闭无效的连接
                        connMgr.closeExpiredConnections();
                        // 关闭空闲时间超过IDLE_ALIVE_MS的连接
                        connMgr.closeIdleConnections(IDLE_ALIVE_MS, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 关闭后台连接
        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
