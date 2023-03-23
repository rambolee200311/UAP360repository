package u8c.server;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.nio.charset.Charset;
import nc.bs.logging.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSON;

public class HttpURLConnectionDemo {
	public static String httpPostWithJson(String url,String postData){
		String result="";
		HttpPost post = null;
		Logger.init("hanglianAPI");
		Logger.debug(postData);
	    try {
	    	// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
	        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	            
	        post = new HttpPost(url);
	        // ������Ϣͷ
	        post.setHeader("Content-type", "application/json; charset=utf-8");	         
	                    
	        // ������Ϣʵ��
	        StringEntity entity = new StringEntity(postData, Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        // ����Json��ʽ����������
	        entity.setContentType("application/json");
	        post.setEntity(entity);	            
	     // ��Ӧģ��
	        CloseableHttpResponse response = null;
	        response = httpClient.execute(post);
	            
	        // ��������
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode != HttpStatus.SC_OK){
	        	Logger.debug("�������: "+statusCode);	            
	        }else{
	        	HttpEntity responseEntity = response.getEntity();
	            result=EntityUtils.toString(responseEntity);  
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        Logger.error(e.getMessage());
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	                Logger.error(e.getMessage());
	            }
	        }
	    }
		return result;
	}
	public static String operator(String url, Map<String, Object> headparams,String json) {
		HttpClient httpClient = new HttpClient();
		PostMethod httpPost = new PostMethod(url);
		Logger.init("hanglianAPI");
		Logger.debug(json);
		httpPost.setRequestHeader("content-type",
				"application/json;charset=utf-8");
		for (Entry<String, Object> entry : headparams.entrySet()) {
			httpPost.setRequestHeader(entry.getKey(), entry.getValue()
					.toString());
		}
		try {
			RequestEntity entity = new StringRequestEntity(json,
					"application/json", "UTF-8");
			httpPost.setRequestEntity(entity);
			httpClient.executeMethod(httpPost);
			json=httpPost.getResponseBodyAsString();
			Logger.debug(json);
			return json;

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String goldenTax(String url,String contenttype,Map<String, Object> headparams,String data) throws URISyntaxException {
		HttpClient httpClient = new HttpClient();
		URIBuilder uriBuilder = new URIBuilder(url);
		
		for (Entry<String, Object> entry : headparams.entrySet()) {
			uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
		}

		
		PostMethod httpPost = new PostMethod(uriBuilder.toString());
		String strResult="";
		Logger.init("hanglianAPI");
		Logger.debug("golden tax request:"+data);
		httpPost.setRequestHeader("content-type",contenttype);
		
		try {
			RequestEntity entity = new StringRequestEntity(data,"application/x-www-form-urlencoded", "UTF-8");
			httpPost.setRequestEntity(entity);
			httpClient.executeMethod(httpPost);
			strResult=httpPost.getResponseBodyAsString();
			Logger.debug("golden tax response:"+strResult);			

		} catch (HttpException e) {
			e.printStackTrace();
			Logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error(e.getMessage());
		}
		return strResult;
	}
}
