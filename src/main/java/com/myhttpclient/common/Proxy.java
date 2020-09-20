package com.myhttpclient.common;

import com.alibaba.fastjson.JSONObject;
import com.myhttpclient.HttpClientUtil;
import com.myhttpclient.exception.HttpRequestInvokeException;

/**
 * 获取代理
 */
public class Proxy {

    private  String ip;
    private  Integer port;

    private Proxy() {
        try {
            initProxy();
        } catch (HttpRequestInvokeException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getProxy(){
        String proxy = this.ip +":"+this.port;
        return proxy;
    }

    public static Proxy custom() {
        return new Proxy();
    }

    private  void initProxy() throws HttpRequestInvokeException, InterruptedException {
        RequestHMPConfig requestHMPConfig = RequestHMPConfig.custom().setUrl("")
                .setRequestMethod(RequestMethods.GET);
        Response response = HttpClientUtil.get(requestHMPConfig);
        String responseBody = response.getResponseBody();
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        JSONObject data ;
        if (jsonObject.getJSONArray("data").getJSONObject(0) == null) {
            Thread.sleep(15000);
            getProxy();
        } else {
            data = jsonObject.getJSONArray("data").getJSONObject(0);
            this.ip = (String) data.get("ip");
            this.port = (Integer) data.get("port");
        }
    }
}
