package com.myhttpclient.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * 请求参数，封装post请求和get请求参数
 */
public class RequestPram {

    private List<NameValuePair> params = new ArrayList();// 将参数放入键值对类NameValuePair中,再放入集合中
    private StringEntity entity; //post请求体;
    private HttpEntity httpEntity;

    private RequestPram() {
    }

    public static RequestPram custom() {
        return new RequestPram();
    }

    /**
     * 设置get请求参数
     *
     * @param key
     * @param value
     * @return
     */
    public RequestPram setGetPram(String key, String value) {
        params.add(new BasicNameValuePair(key, value));
        return this;
    }

    /**
     * 设置post请求参数
     *
     * @param key
     * @param value
     * @return
     */
    public RequestPram setPostPram(String key, String value) {
        setGetPram(key, value);
        return this;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public List<NameValuePair> getParams() {
        return params;
    }

    /**
     * 设置请求体
     *
     * @param obj
     */
    public RequestPram setRequestBody(Object obj) {
        // 将user对象转换为json字符串，并放入entity中
        entity = new StringEntity(JSON.toJSONString(obj), "UTF-8");
        return this;
    }

    public RequestPram setRequestBody(String str) {
        entity = new StringEntity(str, "UTF-8");
        return this;
    }

    /**
     * 获取请求体
     *
     * @return
     */
    public StringEntity getRequestBody() {
        return entity;
    }

    /**
     * 上传文件
     *
     * @param filePath
     * @throws FileNotFoundException
     */
    public RequestPram setHttpEntity(String filePath) throws FileNotFoundException {
        FileBody fileBody = new FileBody(new File(filePath));
        httpEntity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
        return this;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }
}
