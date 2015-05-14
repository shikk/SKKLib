package com.lonever.skklib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.lonever.skklib.util.StringUtils;

public class StringGetbacker {
	private static int TIME_OUT = 8000;
	
	public static String doGet(Context context,String urlString){
		HttpURLConnection http =null;
		InputStream inStream = null;
		String httpResponseStr = null;
		try {
			URL downUrl = new URL(urlString);
			if (NetUtils.getAPNType(context) == NetUtils.NETWORK_TYPE_CMWAP) {// 如果是wap方式，要加网关
				String domain = "";
				String path = "";
				String urlStr = ""; 
				int index = urlStr.indexOf("http://");
				if (index >= 0) {
					urlStr = urlStr.substring(index + 7);
					index = urlStr.indexOf("/");
					if (index > 0) {
						domain = urlStr.substring(0, index);
						path = urlStr.substring(index);
					} else {
						domain = urlStr;
						path = "/";
					}
				}
				downUrl = new URL("http://10.0.0.172"+path); 
				http = (HttpURLConnection) downUrl.openConnection();
				http.setRequestProperty("X-Online-Host", domain);   
			} else {
				http = (HttpURLConnection) downUrl.openConnection();
			}
			http.setConnectTimeout(TIME_OUT);
			http.setReadTimeout(TIME_OUT);
			http.setRequestMethod("GET");
			http.setRequestProperty(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			http.setRequestProperty("Accept-Language", "zh-CN");
			http.setRequestProperty("Referer", downUrl.toString());
			http.setRequestProperty("Charset", "UTF-8");
			http.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			http.setRequestProperty("Connection", "Keep-Alive");

			inStream = http.getInputStream();
			httpResponseStr = convertStreamToString(inStream);
			return httpResponseStr;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (inStream!=null) {
					inStream.close();
				}
				if (http!=null) {
					http.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return httpResponseStr;
		
	}
	
	
	public static String doGet(String urlStr, HashMap<String, String> headerData, Context context) throws SocketTimeoutException, IOException {
		if (StringUtils.isBlank(urlStr) || context == null) {
			throw new IllegalArgumentException();
		}

		if (NetUtils.getAPNType(context) == NetUtils.NETWORK_TYPE_CMWAP || NetUtils.getAPNType(context) == NetUtils.NETWORK_TYPE_3GWAP) {
			return doGetByCMWAP(urlStr, headerData, context);
		}

		Proxy proxy = NetManager.detectProxy(context);

		String httpResponseStr = null;
		HttpURLConnection httpConnect = null;
		InputStream is=null;
		try {
			URL url = new URL(urlStr);
			if (proxy != null) {
				httpConnect = (HttpURLConnection) url.openConnection(proxy);
			} else {
				httpConnect = (HttpURLConnection) url.openConnection();
			}
			httpConnect.setConnectTimeout(TIME_OUT);
			httpConnect.setReadTimeout(TIME_OUT);
			if (headerData != null && headerData.size() > 0) {
				for (String key : headerData.keySet()) {
					httpConnect.setRequestProperty(key, headerData.get(key));
				}
			}
			httpConnect.setRequestMethod("POST");
			httpConnect.connect();
			
			is = httpConnect.getInputStream();
			httpResponseStr = convertStreamToString(is);
		}catch (SocketTimeoutException e) {
			// TODO: handle exception
			throw new SocketTimeoutException();
		}catch (UnknownHostException e) {
			// TODO: handle exception
			throw new SocketTimeoutException();
		}catch (IOException e) {
			//e.printStackTrace();
			throw new IOException();
		} finally {
			try {
				if (is !=null) {
					is.close();
				}
				if (httpConnect != null) {
					httpConnect.disconnect();
				}
			} catch (Exception e) {
			}
		}

		return httpResponseStr;
	}

	private static String doGetByCMWAP(String urlStr, HashMap<String, String> headerData, Context context) throws SocketTimeoutException, IOException {
		String httpResponseStr = null;
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParams, TIME_OUT);
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpGet httpGet = null;
			HttpResponse httpResponse = null;

			String domain = "";
			String path = "";
			int index = urlStr.indexOf("http://");
			if (index >= 0) {
				urlStr = urlStr.substring(index + 7);
				index = urlStr.indexOf("/");
				if (index > 0) {
					domain = urlStr.substring(0, index);
					path = urlStr.substring(index);
				} else {
					domain = urlStr;
					path = "/";
				}
			}

			HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
			HttpHost target = new HttpHost(domain, 80, "http");
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

			httpGet = new HttpGet(path);
			if (headerData != null && headerData.size() > 0) {
				for (String key : headerData.keySet()) {
					httpGet.setHeader(key, headerData.get(key));
				}
			}
			httpResponse = client.execute(target, httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();

			httpResponseStr = EntityUtils.toString(httpEntity);
		}catch (SocketTimeoutException e) {
			// TODO: handle exception
			throw new SocketTimeoutException();
		}catch (IOException e) {
			throw new IOException();
		}

		return httpResponseStr;
	}

	/**
	 * 发送http post请求，并获得字符串返回，需要权限 android.permission.INTERNET
	 *
	 * @param urlStr
	 *            请求的链接地址
	 * @param reqData
	 *            请求数据
	 * @param headerData
	 *            要添加的头信息，没有的传 null *
	 * @param appContext
	 *            {@link cn.wap3.base.AppContext appContext}
	 * @return 返回请求返回的结果字符串或者<tt>null</tt>
	 * @throws IllegalArgumentException
	 *             传入的链接地址或 appContext 为空时抛出
	 */
	public static String doPost(String urlStr, HashMap<String, String> reqData, HashMap<String, String> headerData,
	        Context context) {
		if (StringUtils.isBlank(urlStr) || context == null) {
			throw new IllegalArgumentException();
		}

		Proxy proxy = NetManager.detectProxy(context);

		String httpResponseStr = null;
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (reqData != null && reqData.size() > 0) {
			for (String key : reqData.keySet()) {
				pairs.add(new BasicNameValuePair(key, reqData.get(key)));
			}
		}

		HttpURLConnection httpConnect = null;
		UrlEncodedFormEntity p_entity;
		OutputStream os = null;
		try {
			p_entity = new UrlEncodedFormEntity(pairs, "utf-8");
			URL url = new URL(urlStr);

			if (proxy != null) {
				httpConnect = (HttpURLConnection) url.openConnection(proxy);
			} else {
				httpConnect = (HttpURLConnection) url.openConnection();
			}
			httpConnect.setConnectTimeout(TIME_OUT);
			httpConnect.setReadTimeout(TIME_OUT*2);
			httpConnect.setDoOutput(true);
			httpConnect.setRequestMethod("POST");
			httpConnect.addRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

			if (headerData != null && headerData.size() > 0) {
				for (String key : headerData.keySet()) {
					httpConnect.setRequestProperty(key, headerData.get(key));
				}
			}

			httpConnect.connect();

			os = httpConnect.getOutputStream();
			p_entity.writeTo(os);
			os.flush();

			InputStream content = httpConnect.getInputStream();
			httpResponseStr = convertStreamToString(content);
		} catch (IOException e) {
			 e.printStackTrace();
		} finally {
			try {
				httpConnect.disconnect();
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
			}
		}

		return httpResponseStr;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			 e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * post中文到服务器
	 */
	public static String doGet(Context context, String urlPrefix,String data){
		StringBuffer bankXmlBuffer = null;
		HttpURLConnection connection = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL url = new URL(urlPrefix);  
			if (NetUtils.getAPNType(context) == NetUtils.NETWORK_TYPE_CMWAP) {// 如果是wap方式，要加网关
				String domain = "";
				String path = "";
				String urlStr = ""; 
				int index = urlStr.indexOf("http://");
				if (index >= 0) {
					urlStr = urlStr.substring(index + 7);
					index = urlStr.indexOf("/");
					if (index > 0) {
						domain = urlStr.substring(0, index);
						path = urlStr.substring(index);
					} else {
						domain = urlStr;
						path = "/";
					}
				}
				url = new URL("http://10.0.0.172"+path); 
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("X-Online-Host", domain);   
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}
			//创建URL连接，提交到银行卡鉴权，获取返回结果  
			connection = (HttpURLConnection) url.openConnection();  
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);  
			connection.setRequestProperty("User-Agent", "directclient");  
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));  
			out.print(data);
			out.flush();
			in = new BufferedReader(new InputStreamReader(connection  
			        .getInputStream()));  
			bankXmlBuffer = new StringBuffer();  
			
			String inputLine;  
			while ((inputLine = in.readLine()) != null) {  
			    bankXmlBuffer.append(inputLine);  
			}  
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (bankXmlBuffer != null) {
			return bankXmlBuffer.toString();
		}else{
			return null;
		}
	}
}
