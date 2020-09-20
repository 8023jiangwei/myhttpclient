package com.myhttpclient.common;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SSL {


    private SSL() {
    }

    public static SSL custom() {
        return new SSL();
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public  SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
}




    // 相关的 jks 文件及其密码定义
/*    private final static String CERT_STORE = "D:/test_server_cert.jks";
    private final static String CERT_STORE_PASSWORD = "Testpassw0rd";
    private final static String TRUST_STORE="D:/test_client_trust.jks";
    private final static String TRUST_STORE_PASSWORD="Testpassw0rd";*/
/*
    //SSLContext 指定证书库
    public KeyManager[] getKeyManager() {

        // 载入 jks 文件
        FileInputStream f_certStore = new FileInputStream(CERT_STORE);
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(f_certStore, CERT_STORE_PASSWORD.toCharArray());
        f_certStore.close();

        // 创建并初始化证书库工厂
        String alg = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);
        kmFact.init(ks, CERT_STORE_PASSWORD.toCharArray());

        KeyManager[] kms = kmFact.getKeyManagers();

        return null;
    }

    //SSLContext 指定信任库
    public TrustManager[] getTrustManager() {
    // 相关的 jks 文件及其密码定义


// 载入 jks 文件
        FileInputStream f_trustStore=new FileInputStream(TRUST_STORE);
        KeyStore ks=KeyStore.getInstance("jks");
        ks.load(f_trustStore, TRUST_STORE_PASSWORD.toCharArray());
        f_trustStore.close();

        // 创建并初始化信任库工厂
        String alg=TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmFact=TrustManagerFactory.getInstance(alg);
        tmFact.init(ks);

        TrustManager[] tms=tmFact.getTrustManagers();
        return null;
    }
*/




