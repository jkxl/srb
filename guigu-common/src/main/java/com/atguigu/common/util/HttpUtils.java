package com.atguigu.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jiankai
 * @description:
 * @date 2021-11-16 9:11
 */
@Slf4j
public final class HttpUtils {

    static final String POST = "POST";
    static final String GET = "GET";
    static final int CONN_TIMEOUT = 30000; // ms
    static final int READ_TIMEOUT =30000; // ms

    /**
     * post 方式发送http请求.
     * @author jiankai
     * @date 2021/11/16 9:16
     * @param strUrl
     * @param reqData
     * @return byte[]
     */
    public static byte[] doPost(String strUrl, byte[] reqData) {
        return send(strUrl, POST, reqData);
    }

    /**
     * get方式发送http请求.
     * @author jiankai
     * @date 2021/11/16 9:16
     * @param strUrl
     * @return byte[]
     */
    public static byte[] doGet(String strUrl) {
        return send(strUrl, GET, null);
    }

    public static byte[] send(String strUrl, String reqmethod, byte[] reqData) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setConnectTimeout(CONN_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod(reqmethod);
            httpURLConnection.connect();
            if (reqmethod.equalsIgnoreCase(POST)) {
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(reqData);
                os.flush();
                os.close();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String inputLine;
            StringBuilder bankXmlBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                bankXmlBuffer.append(inputLine);
            }
            in.close();
            httpURLConnection.disconnect();
            return bankXmlBuffer.toString().getBytes();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            return null;
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();// 网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
