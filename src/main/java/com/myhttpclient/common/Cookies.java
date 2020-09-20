package com.myhttpclient.common;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

public class Cookies {

    /**
     * 使用httpcontext，用于设置和携带Cookie
     */
    private HttpClientContext context ;

    /**
     * 储存Cookie
     */
    private CookieStore cookieStore;

    public static Cookies custom(){
        return new Cookies();
    }

    private Cookies(){
        this.context = new HttpClientContext();
        this.cookieStore = new BasicCookieStore();
        this.context.setCookieStore(cookieStore);
    }

    public HttpClientContext getContext() {
        return context;
    }

    public Cookies setContext(HttpClientContext context) {
        this.context = context;
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public Cookies setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }
}
