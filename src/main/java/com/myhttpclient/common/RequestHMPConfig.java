package com.myhttpclient.common;

import org.apache.http.HttpHost;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

/**
 * 请求配置
 */
public class RequestHMPConfig {

    private String Url;
    private RequestMethods requestMethods = RequestMethods.GET;
    private RequestHeader requestHeader;
    private RequestPram requestPram;
    private DefaultProxyRoutePlanner routePlanner = null;

    private RequestHMPConfig() {
    }

    public static RequestHMPConfig custom() {
        return new RequestHMPConfig();
    }

    /**
     * 设置url
     *
     * @param Url
     * @return
     */
    public RequestHMPConfig setUrl(String Url) {
        this.Url = Url;
        return this;
    }

    /**
     * 获取url
     *
     * @return
     */
    public String getUrl() {
        return this.Url;
    }

    /**
     * 设置请求方式
     *
     * @param requestMethod
     * @return
     */
    public RequestHMPConfig setRequestMethod(RequestMethods requestMethod) {
        this.requestMethods = requestMethod;
        return this;
    }

    /**
     * 获取请求方式
     *
     * @return
     */
    public RequestMethods getRequestMethod() {
        return this.requestMethods;
    }

    /**
     * 设置请求头
     *
     * @param requestHeader
     * @return
     */
    public RequestHMPConfig setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    /**
     * 获取RequestHeader
     *
     * @return
     */
    public RequestHeader getRequestHeader() {
        return this.requestHeader;
    }

    /**
     * 设置请求参数
     *
     * @param requestPram
     * @return
     */
    public RequestHMPConfig setRequestPram(RequestPram requestPram) {
        this.requestPram = requestPram;
        return this;
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public RequestPram getRequestPram() {
        return this.requestPram;
    }

    /**
     * 设置代理端口号
     * @param ipAndPort
     */
    public RequestHMPConfig setProxy(String ipAndPort) {
        String hostName = ipAndPort.split(":")[0];
        Integer port =Integer.parseInt(ipAndPort.split(":")[1]);
        HttpHost proxy = new HttpHost(hostName, port);
        this.routePlanner = new DefaultProxyRoutePlanner(proxy);
        return this;
    }

    /**
     * 获取代理
     * @return
     */
    public DefaultProxyRoutePlanner getProxy() {
        return this.routePlanner;
    }


}
