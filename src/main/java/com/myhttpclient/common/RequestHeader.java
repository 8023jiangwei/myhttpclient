package com.myhttpclient.common;


import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_CHARSET = "Accept-Charset";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String ACCEPT_RANGES = "Accept-Ranges";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String CONNECTION = "Connection";
    private static final String COOKIE = "Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String DATE = "Date";
    private static final String EXPECT = "Expect";
    private static final String FROM = "From";
    private static final String HOST = "Host";
    private static final String IF_MATCH = "If-Match ";
    private static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    private static final String IF_NONE_MATCH = "If-None-Match";
    private static final String IF_RANGE = "If-Range";
    private static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    private static final String KEEP_ALIVE = "Keep-Alive";
    private static final String MAX_FORWARDS = "Max-Forwards";
    private static final String PRAGMA = "Pragma";
    private static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    private static final String RANGE = "Range";
    private static final String REFERER = "Referer";
    private static final String TE = "TE";
    private static final String UPGRADE = "Upgrade";
    private static final String USER_AGENT = "User-Agent";
    private static final String VIA = "Via";
    private static final String WARNING = "Warning";
    //记录head头信息
    private Map<String, org.apache.http.Header> headerMaps = new HashMap<String, org.apache.http.Header>();

    private RequestHeader() {
    }

    public static RequestHeader custom() {
        return new RequestHeader();
    }

    /**
     * 自定义header头信息
     *
     * @param key   header-key
     * @param value header-value
     * @return 返回当前对象
     */
    public RequestHeader setOtherHeader(String key, String value) {
    
        headerMaps.put(key, new BasicHeader(key, value));
        return this;
    }

    /**
     * 指定客户端能够接收的内容类型
     * 例如：Accept: text/plain, text/html
     *
     * @param accept accept
     * @return 返回当前对象
     */
    public RequestHeader accept(String accept) {
    
        headerMaps.put(this.ACCEPT,
                new BasicHeader(this.ACCEPT, accept));
        return this;
    }

    /**
     * 浏览器可以接受的字符编码集
     * 例如：Accept-Charset: iso-8859-5
     *
     * @param acceptCharset accept-charset
     * @return 返回当前对象
     */
    public RequestHeader acceptCharset(String acceptCharset) {
    
        headerMaps.put(this.ACCEPT_CHARSET,
                new BasicHeader(this.ACCEPT_CHARSET, acceptCharset));
        return this;
    }

    /**
     * 指定浏览器可以支持的web服务器返回内容压缩编码类型
     * 例如：Accept-Encoding: compress, gzip
     *
     * @param acceptEncoding accept-encoding
     * @return 返回当前对象
     */
    public RequestHeader acceptEncoding(String acceptEncoding) {
    
        headerMaps.put(this.ACCEPT_ENCODING,
                new BasicHeader(this.ACCEPT_ENCODING, acceptEncoding));
        return this;
    }

    /**
     * 浏览器可接受的语言
     * 例如：Accept-Language: en,zh
     *
     * @param acceptLanguage accept-language
     * @return 返回当前对象
     */
    public RequestHeader acceptLanguage(String acceptLanguage) {
    
        headerMaps.put(this.ACCEPT_LANGUAGE,
                new BasicHeader(this.ACCEPT_LANGUAGE, acceptLanguage));
        return this;
    }

    /**
     * 可以请求网页实体的一个或者多个子范围字段
     * 例如：Accept-Ranges: bytes
     *
     * @param acceptRanges accept-ranges
     * @return 返回当前对象
     */
    public RequestHeader acceptRanges(String acceptRanges) {
    
        headerMaps.put(this.ACCEPT_RANGES,
                new BasicHeader(this.ACCEPT_RANGES, acceptRanges));
        return this;
    }

    /**
     * HTTP授权的授权证书
     * 例如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param authorization authorization
     * @return 返回当前对象
     */
    public RequestHeader authorization(String authorization) {
    
        headerMaps.put(this.AUTHORIZATION,
                new BasicHeader(this.AUTHORIZATION, authorization));
        return this;
    }

    /**
     * 指定请求和响应遵循的缓存机制
     * 例如：Cache-Control: no-cache
     *
     * @param cacheControl cache-control
     * @return 返回当前对象
     */
    public RequestHeader cacheControl(String cacheControl) {
    
        headerMaps.put(this.CACHE_CONTROL,
                new BasicHeader(this.CACHE_CONTROL, cacheControl));
        return this;
    }

    /**
     * 表示是否需要持久连接（HTTP 1.1默认进行持久连接）
     * 例如：Connection: close 短链接； Connection: keep-alive 长连接
     *
     * @param connection connection
     * @return 返回当前对象
     */
    public RequestHeader connection(String connection) {
    
        headerMaps.put(this.CONNECTION,
                new BasicHeader(this.CONNECTION, connection));
        return this;
    }

    /**
     * HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器
     * 例如：Cookie: $Version=1; Skin=new;
     *
     * @param cookie cookie
     * @return 返回当前对象
     */
    public RequestHeader cookie(String cookie) {
    
        headerMaps.put(this.COOKIE,
                new BasicHeader(this.COOKIE, cookie));
        return this;
    }

    /**
     * 请求内容长度
     * 例如：Content-Length: 348
     *
     * @param contentLength content-length
     * @return 返回当前对象
     */
    public RequestHeader contentLength(String contentLength) {
    
        headerMaps.put(this.CONTENT_LENGTH,
                new BasicHeader(this.CONTENT_LENGTH, contentLength));
        return this;
    }

    /**
     * 请求的与实体对应的MIME信息
     * 例如：Content-Type: application/x-www-form-urlencoded
     *
     * @param contentType content-type
     * @return 返回当前对象
     */
    public RequestHeader contentType(String contentType) {
    
        headerMaps.put(this.CONTENT_TYPE,
                new BasicHeader(this.CONTENT_TYPE, contentType));
        return this;
    }

    /**
     * 请求发送的日期和时间
     * 例如：Date: Tue, 15 Nov 2010 08:12:31 GMT
     *
     * @param date date
     * @return 返回当前对象
     */
    public RequestHeader date(String date) {
    
        headerMaps.put(this.DATE,
                new BasicHeader(this.DATE, date));
        return this;
    }

    /**
     * 请求的特定的服务器行为
     * 例如：Expect: 100-continue
     *
     * @param expect expect
     * @return 返回当前对象
     */
    public RequestHeader expect(String expect) {
    
        headerMaps.put(this.EXPECT,
                new BasicHeader(this.EXPECT, expect));
        return this;
    }

    /**
     * 发出请求的用户的Email
     * 例如：From: user@email.com
     *
     * @param from from
     * @return 返回当前对象
     */
    public RequestHeader from(String from) {
    
        headerMaps.put(this.FROM,
                new BasicHeader(this.FROM, from));
        return this;
    }

    /**
     * 指定请求的服务器的域名和端口号
     * 例如：Host: blog.csdn.net
     *
     * @param host host
     * @return 返回当前对象
     */
    public RequestHeader host(String host) {
    
        headerMaps.put(this.HOST,
                new BasicHeader(this.HOST, host));
        return this;
    }

    /**
     * 只有请求内容与实体相匹配才有效
     * 例如：If-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifMatch if-match
     * @return 返回当前对象
     */
    public RequestHeader ifMatch(String ifMatch) {
    
        headerMaps.put(this.IF_MATCH,
                new BasicHeader(this.IF_MATCH, ifMatch));
        return this;
    }

    /**
     * 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
     * 例如：If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifModifiedSince if-modified-Since
     * @return 返回当前对象
     */
    public RequestHeader ifModifiedSince(String ifModifiedSince) {
    
        headerMaps.put(this.IF_MODIFIED_SINCE,
                new BasicHeader(this.IF_MODIFIED_SINCE, ifModifiedSince));
        return this;
    }

    /**
     * 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
     * 例如：If-None-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifNoneMatch if-none-match
     * @return 返回当前对象
     */
    public RequestHeader ifNoneMatch(String ifNoneMatch) {
    
        headerMaps.put(this.IF_NONE_MATCH,
                new BasicHeader(this.IF_NONE_MATCH, ifNoneMatch));
        return this;
    }

    /**
     * 如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag
     * 例如：If-Range: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifRange if-range
     * @return 返回当前对象
     */
    public RequestHeader ifRange(String ifRange) {
    
        headerMaps.put(this.IF_RANGE,
                new BasicHeader(this.IF_RANGE, ifRange));
        return this;
    }

    /**
     * 只在实体在指定时间之后未被修改才请求成功
     * 例如：If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifUnmodifiedSince if-unmodified-since
     * @return 返回当前对象
     */
    public RequestHeader ifUnmodifiedSince(String ifUnmodifiedSince) {
    
        headerMaps.put(this.IF_UNMODIFIED_SINCE,
                new BasicHeader(this.IF_UNMODIFIED_SINCE, ifUnmodifiedSince));
        return this;
    }

    /**
     * 限制信息通过代理和网关传送的时间
     * 例如：Max-Forwards: 10
     *
     * @param maxForwards max-forwards
     * @return 返回当前对象
     */
    public RequestHeader maxForwards(String maxForwards) {
    
        headerMaps.put(this.MAX_FORWARDS,
                new BasicHeader(this.MAX_FORWARDS, maxForwards));
        return this;
    }

    /**
     * 用来包含实现特定的指令
     * 例如：Pragma: no-cache
     *
     * @param pragma pragma
     * @return 返回当前对象
     */
    public RequestHeader pragma(String pragma) {
    
        headerMaps.put(this.PRAGMA,
                new BasicHeader(this.PRAGMA, pragma));
        return this;
    }

    /**
     * 连接到代理的授权证书
     * 例如：Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param proxyAuthorization proxy-authorization
     * @return 返回当前对象
     */
    public RequestHeader proxyAuthorization(String proxyAuthorization) {
    
        headerMaps.put(this.PROXY_AUTHORIZATION,
                new BasicHeader(this.PROXY_AUTHORIZATION, proxyAuthorization));
        return this;
    }

    /**
     * 只请求实体的一部分，指定范围
     * 例如：Range: bytes=500-999
     *
     * @param range range
     * @return 返回当前对象
     */
    public RequestHeader range(String range) {
    
        headerMaps.put(this.RANGE,
                new BasicHeader(this.RANGE, range));
        return this;
    }

    /**
     * 先前网页的地址，当前请求网页紧随其后,即来路
     * 例如：Referer: http://www.zcmhi.com/archives/71.html
     *
     * @param referer referer
     * @return 返回当前对象
     */
    public RequestHeader referer(String referer) {
    
        headerMaps.put(this.REFERER,
                new BasicHeader(this.REFERER, referer));
        return this;
    }

    /**
     * 客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息
     * 例如：TE: trailers,deflate;q=0.5
     *
     * @param te te
     * @return 返回当前对象
     */
    public RequestHeader te(String te) {
    
        headerMaps.put(this.TE,
                new BasicHeader(this.TE, te));
        return this;
    }

    /**
     * 向服务器指定某种传输协议以便服务器进行转换（如果支持）
     * 例如：Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
     *
     * @param upgrade upgrade
     * @return 返回当前对象
     */
    public RequestHeader upgrade(String upgrade) {
    
        headerMaps.put(this.UPGRADE,
                new BasicHeader(this.UPGRADE, upgrade));
        return this;
    }

    /**
     * User-Agent的内容包含发出请求的用户信息
     *
     * @param userAgent user-agent
     * @return 返回当前对象
     */
    public RequestHeader userAgent(String userAgent) {
    
        headerMaps.put(this.USER_AGENT,
                new BasicHeader(this.USER_AGENT, userAgent));
        return this;
    }

    /**
     * 关于消息实体的警告信息
     * 例如：Warn: 199 Miscellaneous warning
     *
     * @param warning warning
     * @return 返回当前对象
     */
    public RequestHeader warning(String warning) {
    
        headerMaps.put(this.WARNING,
                new BasicHeader(this.WARNING, warning));
        return this;
    }

    /**
     * 通知中间网关或代理服务器地址，通信协议
     * 例如：Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
     *
     * @param via via
     * @return 返回当前对象
     */
    public RequestHeader via(String via) {
    
        headerMaps.put(this.VIA,
                new BasicHeader(this.VIA, via));
        return this;
    }

    /**
     * 设置此HTTP连接的持续时间（超时时间）
     * 例如：Keep-Alive: 300
     *
     * @param keepAlive keep-alive
     * @return 返回当前对象
     */
    public RequestHeader keepAlive(String keepAlive) {
    
        headerMaps.put(this.KEEP_ALIVE,
                new BasicHeader(this.KEEP_ALIVE, keepAlive));
        return this;
    }

    /**
     * 默认header
     *
     * @return Accept-Encoding: gzip, deflate, br
     * Accept-Language: zh-CN,zh;q=0.9
     */
    public RequestHeader defaultHeaders() {
        headerMaps.put(this.CONNECTION, new BasicHeader(this.CONNECTION, "keep-alive"));
        headerMaps.put(this.USER_AGENT, new BasicHeader(this.USER_AGENT, "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"));
        headerMaps.put(this.CONTENT_TYPE, new BasicHeader((this.CONTENT_TYPE), "application/json;charset=UTF-8"));
        headerMaps.put(this.ACCEPT, new BasicHeader(this.ACCEPT, "application/json; charset=utf-8"));
        headerMaps.put(this.ACCEPT_ENCODING, new BasicHeader(this.ACCEPT_ENCODING, "gzip, deflate, br"));
        headerMaps.put(this.ACCEPT_LANGUAGE, new BasicHeader(this.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9"));
        return this;
    }

    /**
     * 获取head信息
     *
     * @return
     */
    public String get(String headName) {
        if (headerMaps.containsKey(headName)) {
            return headerMaps.get(headName).getValue();
        }
        return null;
    }

    /**
     * 返回header头信息
     *
     * @return 返回构建的header头信息数组
     */
    public org.apache.http.Header[] build() {
        org.apache.http.Header[] headers = new org.apache.http.Header[headerMaps.size()];
        int i = 0;
        for (org.apache.http.Header header : headerMaps.values()) {
            headers[i] = header;
            i++;
        }
        headerMaps.clear();
        headerMaps = null;
        return headers;
    }


}
