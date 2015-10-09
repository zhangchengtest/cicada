package com.cicada.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

/**
* @author www.baizeju.com
*/
public class HTMLTest {
    private static String ENCODE = "GBK";
    private static void message( String szMsg ) {
    	System.out.println(szMsg);
    }
    public static String openFile( String szFileName ) {
        try {
            BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), ENCODE) );
            String szContent="";
            String szTemp;
            
            while ( (szTemp = bis.readLine()) != null) {
                szContent+=szTemp+"\n";
            }
            bis.close();
            return szContent;
        }
        catch( Exception e ) {
            return "";
        }
    }
    
   public static void main(String[] args) {
        
        
        try{
            //Parser parser = Parser.createParser(szContent, ENCODE);
            //Parser parser = new Parser( szContent );
//           Parser parser = new Parser( (HttpURLConnection) (new URL("http://r.ele.me/cxxcqxl/menu")).openConnection() );
        	 // 打开和URL之间的连接
        	String url = "http://r.ele.me/cxxcqxl";
        	 URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           Parser parser = new Parser(conn);
           for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {
               Node node = i.nextNode();
               message("getText:"+node.getText());
//               message("getPlainText:"+node.toPlainTextString());
//               message("toHtml:"+node.toHtml());
//               message("toHtml(true):"+node.toHtml(true));
//               message("toHtml(false):"+node.toHtml(false));
//               message("toString:"+node.toString());
//               message("=================================================");
           }            
        }
        catch( Exception e ) {  
        	e.printStackTrace();
        }
    }
}