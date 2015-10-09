package com.cicada.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;

public class HTMLTest1 {

    public String getPageContent(String strUrl, String strPostRequest,
            int maxLength) {
        // 读取结果网页
        StringBuffer buffer = new StringBuffer();
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "5000");
        try {
            URL newUrl = new URL(strUrl);
            HttpURLConnection hConnect = (HttpURLConnection) newUrl
                    .openConnection();
            // POST方式的额外数据
            if (strPostRequest.length() > 0) {
                hConnect.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(hConnect
                        .getOutputStream());
                out.write(strPostRequest);
                out.flush();
                out.close();
            }
            // 读取内容
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    hConnect.getInputStream()));
            int ch;
            for (int length = 0; (ch = rd.read()) > -1
                    && (maxLength <= 0 || length < maxLength); length++)
                buffer.append((char) ch);
            String s = buffer.toString();
            s.replaceAll("//&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
            System.out.println(s);
            
            rd.close();
            hConnect.disconnect();
            return buffer.toString().trim();
        } catch (Exception e) {
            // return "错误:读取网页失败！";
            //
            return null;

             
        }
    }

//    public static String createhttpClient(String url, String param) {
//        HttpClient client = new HttpClient();
//        String response = null;
//        String keyword = null;
//        PostMethod postMethod = new PostMethod(url);

   
//        try {
//            if (param != null)
//                keyword = new String(param.getBytes("gb2312"), "ISO-8859-1");
//        } catch (UnsupportedEncodingException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }

        // NameValuePair[] data = { new NameValuePair("keyword", keyword) };
//        // // 将表单的值放入postMethod中
//        // postMethod.setRequestBody(data);
//        //    以上部分是带参数抓取,我自己把它注销了．大家可以把注销消掉研究下           
//
//        try {
//            int statusCode = client.executeMethod(postMethod);
//            response = new String(postMethod.getResponseBodyAsString()
//                    .getBytes("ISO-8859-1"), "gb2312");//这里要注意下 gb2312要和你抓取网页的编码要一样
//            
//            String p = response.replaceAll("//&[a-zA-Z]{1,10};", "")
//                    .replaceAll("<[^>]*>", "");//去掉网页中带有html语言的标签
//            System.out.println(p);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//        return response;

//    }


public static void main(String[] args) {

        String url = "http://r.ele.me/cxxcqxl";
        String keyword = "疯狂Java网";
        HTMLTest1 p = new HTMLTest1();
//        String response = p.createhttpClient(url, keyword); // 第一种方法
         p.getPageContent(url, "post", 100500);//第二种方法
    }
}
