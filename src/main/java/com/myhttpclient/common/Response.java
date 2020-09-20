package com.myhttpclient.common;


import com.myhttpclient.config.CilentConfig;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private static final long serialVersionUID = -6368281080581808792L;

    /**
     * 协议版本
     */
    private ProtocolVersion protocolVersion;

    /**
     * 状态行-StatusLine
     */
    private StatusLine statusLine;

    /**
     * 状态码-statusCode
     */
    private int statusCode;

    /**
     * 响应头信息
     */
    private Header[] respHeaders;

    /**
     * 执行结果-body
     */
    private String responseBody;

    /**
     * 请求上下文
     */
    private HttpClientContext context;

    public static Response custom(CloseableHttpResponse response, HttpClientContext context){
        return new Response(response,context);
    }

    private Response(CloseableHttpResponse response, HttpClientContext context) {
        this.protocolVersion = response.getProtocolVersion();
        this.statusLine = response.getStatusLine();
        this.statusCode = response.getStatusLine().getStatusCode();
        this.respHeaders = response.getAllHeaders();
        try {
            this.responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.context = context;
    }

    public HttpClientContext getContext() {
        return context;
    }

    public void setContext(HttpClientContext context) {
        this.context = context;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Header[] getRespHeaders() {
        return respHeaders;
    }

    public void setRespHeaders(Header[] respHeaders) {
        this.respHeaders = respHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "Response{" +
                "protocolVersion=" + protocolVersion +
                ", statusLine=" + statusLine +
                ", statusCode=" + statusCode +
                ", respHeaders=" + Arrays.toString(respHeaders) +
                ", responseBody='" + responseBody + '\'' +
                ", context=" + context +
                '}';
    }
}
