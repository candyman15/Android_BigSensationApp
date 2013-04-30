package com.bigsensation.preference.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpUtil {

	public static final String HANDLER_RETURN_MESSAGE_RESPONSE 	= "RESPONSE";
	public static final String HANDLER_RETURN_MESSAGE_OK 		= "OK";
	public static final String HANDLER_RETURN_MESSAGE_ERROR 	= "ERROR";
	
	private static final int DOWN_LOAD_TIMEOUT_XML = 10000;
	private static final int DOWN_LOAD_TIMEOUT_IMAGE_CONNECT = 10000;
	private static final int DOWN_LOAD_TIMEOUT_IMAGE_SO = 10000;
		
	private static HttpDataThread httpDataThread = null;

	
	public void test(Context _ctx, Handler _handler) throws Exception{
		//if(httpXmlData3Thread != null && httpXmlData3Thread.isAlive()){
			
			//MessageUtil.showToast(_ctx, "서버 통신이 진행중입니다.\n잠시 기다려 주십시오.", Toast.LENGTH_SHORT);
			
		//}else{
			
			String strAddr = "http://eungoo.com/app/testJson.json";
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();			
			//prefs = PreferenceManager.getDefaultSharedPreferences(_ctx);
			
			try
			{					
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_MODE,mode));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_CO_ID,STR_SERVER_REQUEST_MODE_APPR_VALUE_COID));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_IMEI_ID, DeviceInfoUtil.getMyIMEI(_ctx)));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_DOCDTL, docdtl));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_EMP_ID,createId));				
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_TYPE,type));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_DOC_ID,docId));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_OPIN,opin));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_AID,prefs.getString(XmlParserUtil.STR_LOGIN_CHECK_KEY_EMP_ID, "")));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_LAST,last));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_REQCL,reqCl));
												
				
				httpDataThread = new HttpDataThread(_ctx, _handler, strAddr, postParameters);
				
				httpDataThread.start();
				
			}catch (Exception e){
				e.printStackTrace();
				throw e;
			}
		//}

	}
	
	public void sendSelectFileName(Context _ctx, String selectFileName, Handler _handler) throws Exception{
		//if(httpXmlData3Thread != null && httpXmlData3Thread.isAlive()){
		
		//MessageUtil.showToast(_ctx, "서버 통신이 진행중입니다.\n잠시 기다려 주십시오.", Toast.LENGTH_SHORT);
		
		//}else{
		
		String strAddr = "http://eungoo.com/app/testJson.json";
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();			
		//prefs = PreferenceManager.getDefaultSharedPreferences(_ctx);
		
		try
		{					
				postParameters.add(new BasicNameValuePair("selectFileName",selectFileName));
			
			
			httpDataThread = new HttpDataThread(_ctx, _handler, strAddr, postParameters);
			
			httpDataThread.start();
			
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		//}
		
	}
	
	public void matchAnswerOfFriend(Context _ctx, Handler _handler) throws Exception{
		//if(httpXmlData3Thread != null && httpXmlData3Thread.isAlive()){
		
		//MessageUtil.showToast(_ctx, "서버 통신이 진행중입니다.\n잠시 기다려 주십시오.", Toast.LENGTH_SHORT);
		
		//}else{
		
		String strAddr = "http://eungoo.com/app/testJson.json";
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();			
		//prefs = PreferenceManager.getDefaultSharedPreferences(_ctx);
		
		try
		{					
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_MODE,mode));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_CO_ID,STR_SERVER_REQUEST_MODE_APPR_VALUE_COID));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_IMEI_ID, DeviceInfoUtil.getMyIMEI(_ctx)));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_DOCDTL, docdtl));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_EMP_ID,createId));				
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_TYPE,type));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_DOC_ID,docId));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_OPIN,opin));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_AID,prefs.getString(XmlParserUtil.STR_LOGIN_CHECK_KEY_EMP_ID, "")));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_LAST,last));
//				postParameters.add(new BasicNameValuePair(STR_SERVER_REQUEST_KEY_APPROVE_REQCL,reqCl));
			
			
			httpDataThread = new HttpDataThread(_ctx, _handler, strAddr, postParameters);
			
			httpDataThread.start();
			
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		//}
		
	}
	
	class HttpDataThread extends Thread
	{
		private Context ctx;
		private String strAddr;
		private String strResult;
		private Handler handler;
		private Message msg;
		private Bundle bundle;
		private List<NameValuePair> postParameters;
		
		public HttpDataThread(Context _ctx, Handler _handler, String _strAddr)
		{
			ctx = _ctx;
			handler = _handler;
			strAddr = _strAddr;
			strResult = "";
		}
		
		public HttpDataThread(Context _ctx, Handler _handler, String _strAddr, List<NameValuePair> _postParameters)
		{
			ctx = _ctx;
			handler = _handler;
			strAddr = _strAddr;
			strResult = "";
			postParameters = _postParameters;
		}
		
		public void setPostParameters(List<NameValuePair> _postParameters)
		{
			this.postParameters = _postParameters;
		}
		
		public void run() 
		{
			ArrayList<NameValuePair> serverPostParameter = null;
			UrlEncodedFormEntity formEntity = null;
			HttpPost post = null;
			HttpClient client = null;
			
			String strResult = "";
			String strErrorMessage = "";

			StringBuffer sbParameter = null;
			String strParameterKey = "";
			String strParameterValue = "";
			
			try
			{
				
				sbParameter = new StringBuffer();

//				for(int i=0;i<postParameters.size();i++)
//				{
//					if(i != 0)
//					{
//						sbParameter.append("&");
//					}
//					
//					strParameterKey = postParameters.get(i).getName();
//					strParameterValue = postParameters.get(i).getValue();
//					
//					sbParameter.append(strParameterKey);
//					sbParameter.append("=");
//					sbParameter.append(strParameterValue);
//				}
//
//				LogUtil.debugLog(ctx, TAG, strAddr + "?Q=" + sbParameter.toString());
//				LogUtil.debugLog(ctx, TAG, strAddr + "?Q=" + CryptionUtil.HttpEncrypt(sbParameter.toString()));
				
				//serverPostParameter = new ArrayList<NameValuePair>();
				//serverPostParameter.add(new BasicNameValuePair(STR_SERVER_REQUEST_PARAMNAME, CryptionUtil.HttpEncrypt(sbParameter.toString())));
				
				formEntity = new UrlEncodedFormEntity(postParameters,HTTP.UTF_8);				

				post = new HttpPost();
				post.setURI(new URI(strAddr));
				post.setEntity(formEntity);

				client = getHttpClient(ctx, strAddr); 

				strResult = client.execute(post, mDataResHandler);
				
			}
			catch(SocketTimeoutException e)
			{
				strResult = HANDLER_RETURN_MESSAGE_ERROR;
				
				strErrorMessage = "서버 연결 시간 초과";
				e.printStackTrace();
			}
			catch(ConnectTimeoutException e)
			{
				strResult = HANDLER_RETURN_MESSAGE_ERROR;
				
				strErrorMessage = "서버 연결 시간 초과";
				e.printStackTrace();
			}
			catch(Exception e)
			{
				strResult = HANDLER_RETURN_MESSAGE_ERROR;
				
				strErrorMessage = e.getMessage();
				e.printStackTrace();
			}
			finally
			{
				serverPostParameter = null;
				formEntity = null;
				post = null;
				client = null;
				
				//strResult = CommonUtil.nvl(strResult, "");
				//LogUtil.debugLog(ctx, TAG, " HttpXmlDataThread strResult:" + strResult);
				//strErrorMessage = CommonUtil.nvl(strErrorMessage, "");
				
				bundle = new Bundle();
				bundle.putString(HANDLER_RETURN_MESSAGE_RESPONSE, strResult);
				bundle.putString(HANDLER_RETURN_MESSAGE_ERROR, strErrorMessage);
				
				msg = Message.obtain();
				msg.setData(bundle);
				
				handler.sendMessage(msg);
			}
		}
		
		private HttpClient getHttpClient(Context ctx, String strAddr)
		{
			HttpClient returnHttpClient = null;
			
			ClientConnectionManager clientConnectionManager;
			HttpParams params = null;
			HttpContext httpContext;
			
//			if(strAddr.toLowerCase().startsWith("https"))
//			{
//				SchemeRegistry schemeRegistry = new SchemeRegistry();
//				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80)); // http scheme
//				schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));// https scheme
//				
//				params = new BasicHttpParams();
//				params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 1);
//				params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(1));
//				params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
//				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//				HttpProtocolParams.setContentCharset(params, "utf8");
//				HttpConnectionParams.setConnectionTimeout(params, DOWN_LOAD_TIMEOUT_XML);
//	            HttpConnectionParams.setSoTimeout(params, DOWN_LOAD_TIMEOUT_XML);
//				  
//	            // ignore that the ssl cert is self signed
//				CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//				credentialsProvider.setCredentials(new AuthScope(ctx.getString(R.string.gsitm_moffice_server_host), AuthScope.ANY_PORT),
//												   null);
//													//new UsernamePasswordCredentials("YourUserNameHere", "UserPasswordHere"));
//				clientConnectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
//				httpContext = new BasicHttpContext();
//				httpContext.setAttribute("http.auth.credentials-provider", credentialsProvider);
//				
//				returnHttpClient = new DefaultHttpClient(clientConnectionManager, params);
//			}
//			else
//			{
				returnHttpClient = new DefaultHttpClient();
				params = returnHttpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(params, DOWN_LOAD_TIMEOUT_XML);
                HttpConnectionParams.setSoTimeout(params, DOWN_LOAD_TIMEOUT_XML);
//			}
			
			return returnHttpClient;
		}

		ResponseHandler<String> mDataResHandler = new ResponseHandler<String>()
		{
			String SUBTAG = ".mDataResHandler";
			
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
			{
				//response.getStatusLine().getStatusCode()
				
				StringBuilder sbResult = new StringBuilder();
				String strLine = "";
				String strReturnValue = "";
				
				try
				{
					if(HttpURLConnection.HTTP_OK == response.getStatusLine().getStatusCode())
					{
						BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),HTTP.UTF_8),8);
	
						while((strLine = br.readLine()) != null)
						{
							sbResult.append(strLine);
						}
						
						br.close();
						
						//strResult = URLDecoder.decode(sbResult.toString(), "UTF-8");
					
						strReturnValue = sbResult.toString();
						
						
					}
					else
					{
						throw new Exception("Server 통신 오류\n\n" + response.getStatusLine());
					}
				}
				catch(ClientProtocolException e)
				{
					e.printStackTrace();
					throw e;
				}
				catch(IOException e)
				{
					e.printStackTrace();
					throw e;
				}
				catch(Exception e)
				{
					e.printStackTrace();					
					throw new IOException(e.getMessage());
				}
				
				return strReturnValue;
			}
		};
	}

}
