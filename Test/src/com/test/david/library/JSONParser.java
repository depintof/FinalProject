package com.test.david.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;


public class JSONParser {

	static InputStream is = null;
    JSONObject jObj = null;
    static String json = "";
    
    String globalResult = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    public String getJSONFromUrl(final String url) {
    	try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
// 	                httpPost.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
            httpPost.setHeader("Accept-Charset","utf-8");
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            
// 	                Log.e("JSON", "Functional");
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            
            Log.e("JSON", "Functional 1");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            
            Log.e("JSON", "Functional 2");
        } catch (IOException e) {
            e.printStackTrace();
            
            Log.e("JSON", "Functional 3");
            
            return null;
        }
    	
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8000);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        json = replaceSpecialCharacters(json);
        
		return json;
    }
    
    
    public String replaceSpecialCharacters(String json){
    	json = json.replaceAll("&ntilde", "ñ");
    	json = json.replaceAll("&aacute", "á");
    	json = json.replaceAll("&eacute", "é");
    	json = json.replaceAll("&iacute", "í");
    	json = json.replaceAll("&oacute", "ó");
    	json = json.replaceAll("&uacute", "ú");
    	return json;
    }
    
}
