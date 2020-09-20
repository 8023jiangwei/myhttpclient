package com.myhttpclient;

import com.myhttpclient.common.*;
import com.myhttpclient.config.CilentConfig;
import com.myhttpclient.exception.HttpRequestInvokeException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpClientUtil {

    public static Response get(RequestHMPConfig requetHMPConfig) throws HttpRequestInvokeException {
        //获取请求行、头 、请求参数
        String url = requetHMPConfig.getUrl();
        RequestMethods requestMethod = requetHMPConfig.getRequestMethod();
        RequestHeader requestHeader = requetHMPConfig.getRequestHeader();
        DefaultProxyRoutePlanner proxy = requetHMPConfig.getProxy();
        Header[] headers = null;
        if (requestHeader == null) {
            //设置默认请求头
            headers = RequestHeader.custom().defaultHeaders().build();
        } else {
            headers = requestHeader.build();
        }
        HttpGet httpGet = null;
        if (requetHMPConfig.getRequestPram() != null) {
            if (requetHMPConfig.getRequestPram().getRequestBody() != null) {
                throw new HttpRequestInvokeException("Get请求不支持请求体形式");
            }
            List<NameValuePair> params = requetHMPConfig.getRequestPram().getParams();
            //获取请求协议，请求路径
            String[] split = url.split("://");
            String[] split1 = split[1].split("/");
            String host = split1[0];
            String path = "/";
            for (int i = 1; i < split1.length; i++) {
                if (path.equals("/")) {
                    path = path.concat(split1[i]);
                } else {
                    path = path.concat("/");
                    path = path.concat(split1[i]);
                }
            }
            URI uri = null;
            if (split[0].equals("http")) {
                uri = buildUri("http", host, 80, path, params);
            } else {
                uri = buildUri("https", host, 443, path, params);
            }
            //获取请求方式对象
            httpGet = (HttpGet) getRequest(uri, requestMethod);
        } else {
            httpGet = (HttpGet) getRequest(url, requestMethod);
        }

        //设置请求头
        httpGet.setHeaders(headers);
        //执行请求
        Response response = executeRequest(httpGet,proxy);

        return response;
    }

    public static Response post(RequestHMPConfig requetHMPConfig) {
        String url = requetHMPConfig.getUrl();
        RequestMethods requestMethod = requetHMPConfig.getRequestMethod();
        RequestHeader requestHeader = requetHMPConfig.getRequestHeader();
        DefaultProxyRoutePlanner proxy = requetHMPConfig.getProxy();
        Header[] headers = null;
        if (requestHeader == null) {
            //设置默认请求头
            headers = RequestHeader.custom().defaultHeaders().build();
        } else {
            headers = requestHeader.build();
        }
        HttpPost httpPost = null;
        StringEntity requestBody = null;
        HttpEntity httpEntity = null;

        if (requetHMPConfig.getRequestPram() != null) {
            requestBody = requetHMPConfig.getRequestPram().getRequestBody();
            httpEntity = requetHMPConfig.getRequestPram().getHttpEntity();
            //判断是否含有get参数，如果有buildURI,如果没有正常传参
            List<NameValuePair> params = requetHMPConfig.getRequestPram().getParams();
            if (params.size() == 0) {
                httpPost = (HttpPost) getRequest(url, requestMethod);
                if (requestBody != null) {
                    httpPost.setEntity(requestBody);
                    httpPost.setHeaders(headers);
                } else {
                    httpPost.setEntity(httpEntity);
                }
                //    httpPost.setEntity(requestBody);
            } else {
                //获取请求协议，请求路径
                String[] split = url.split("://");
                String[] split1 = split[1].split("/");
                String host = split1[0];
                String path = "/";
                for (int i = 1; i < split1.length; i++) {
                    if (path.equals("/")) {
                        path = path.concat(split1[i]);
                    } else {
                        path = path.concat("/");
                        path = path.concat(split1[i]);
                    }
                }
                URI uri = null;
                if (split[0].equals("http")) {
                    uri = buildUri("http", host, 80, path, params);
                } else {
                    uri = buildUri("https", host, 443, path, params);
                }
                httpPost = (HttpPost) getRequest(uri, requestMethod);
                // 1 null null 2 null y  3 y null
                if (requestBody == null && httpEntity == null) {

                } else if (requestBody != null && httpEntity == null) {
                    httpPost.setEntity(requestBody);
                    httpPost.setHeaders(headers);
                } else if (requestBody == null && httpEntity != null) {
                    httpPost.setEntity(httpEntity);
                }
                if (requestBody != null) {
                    httpPost.setEntity(requestBody);
                    httpPost.setHeaders(headers);
                }
            }
        } else {
            httpPost = (HttpPost) getRequest(url, requestMethod);
            httpPost.setHeaders(headers);
        }
        //发送请求
        Response response = executeRequest(httpPost,proxy);
        return response;
    }

    /**
     * 执行请求
     *
     * @param httpRequestBase
     * @return
     */
    private static Response executeRequest(HttpRequestBase httpRequestBase, DefaultProxyRoutePlanner proxy) {
        Response responseData = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = CilentConfig.custom().getHttpclient(proxy);
        HttpClientContext context = Cookies.custom().getContext();
        try {
            response = httpclient.execute(httpRequestBase, context);
            if (response != null) {
                responseData = Response.custom(response, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseData;
    }

    /**
     * @param protocol 协议号
     * @param Host     域名
     * @param port     端口号
     * @param path     路径
     * @return
     */
    private static URI buildUri(String protocol, String Host, Integer port, String path, List<NameValuePair> params) {
        // 设置uri信息,并将参数集合放入uri;
        URI uri = null;
        try {
            uri = new URIBuilder().setScheme(protocol).setHost(Host)
                    .setPort(port).setPath(path)
                    .setParameters(params).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * 默认请求路径
     *
     * @param path 路径
     * @return
     */
    private static URI buildDefaultUri(String path, List<NameValuePair> params) {
        // 设置uri信息,并将参数集合放入uri;
        URI uri = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost("test.qiyekexie.com")
                    .setPort(80).setPath(path)
                    .setParameters(params).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * 根据请求方法名，获取request对象
     *
     * @param urlobj 资源地址
     * @param method 请求方式
     * @return 返回Http处理request基类
     */
    private static HttpRequestBase getRequest(Object urlobj, RequestMethods method) {
        HttpRequestBase request = null;
        if (urlobj instanceof String) {
            String url = (String) urlobj;
            switch (method.getCode()) {
                case 0:// HttpGet
                    request = new HttpGet(url);
                    break;
                case 1:// HttpPost
                    request = new HttpPost(url);
                    break;
                case 2:// HttpHead
                    request = new HttpHead(url);
                    break;
                case 3:// HttpPut
                    request = new HttpPut(url);
                    break;
                case 4:// HttpDelete
                    request = new HttpDelete(url);
                    break;
                case 5:// HttpTrace
                    request = new HttpTrace(url);
                    break;
                case 6:// HttpPatch
                    request = new HttpPatch(url);
                    break;
                case 7:// HttpOptions
                    request = new HttpOptions(url);
                    break;
                default:
                    request = new HttpPost(url);
                    break;
            }
        } else if (urlobj instanceof URI) {
            URI url = (URI) urlobj;
            switch (method.getCode()) {
                case 0:// HttpGet
                    request = new HttpGet(url);
                    break;
                case 1:// HttpPost
                    request = new HttpPost(url);
                    break;
                case 2:// HttpHead
                    request = new HttpHead(url);
                    break;
                case 3:// HttpPut
                    request = new HttpPut(url);
                    break;
                case 4:// HttpDelete
                    request = new HttpDelete(url);
                    break;
                case 5:// HttpTrace
                    request = new HttpTrace(url);
                    break;
                case 6:// HttpPatch
                    request = new HttpPatch(url);
                    break;
                case 7:// HttpOptions
                    request = new HttpOptions(url);
                    break;
                default:
                    request = new HttpPost(url);
                    break;
            }
        }
        return request;
    }
}
