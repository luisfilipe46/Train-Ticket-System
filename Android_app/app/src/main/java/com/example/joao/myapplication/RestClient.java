package com.example.joao.myapplication;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by joao on 25-10-2015.
 */
public class RestClient {
    private URL url;
    private InputStream in;
    private String method;
    private boolean paramsBool;
    private List<Pair<String, String>> params;
    private String ret;


    public RestClient(String url) throws MalformedURLException {
        this.url = new URL(url);
        method = "GET";
        paramsBool = false;
        params = new ArrayList<>();
        ret = null;
    }

    public void setParams(List<Pair<String,String>> params)
    {
        paramsBool = true;
        this.params = params;

    }

    public void setParams()
    {
        paramsBool = true;

    }

    public String getReturn()
    {
        return ret;
    }

    public void addParam(String name,String value)
    {
        paramsBool= true;
        params.add(new Pair<String, String>(name,value));
    }



    public void setUrl(String url)
    {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e("RestClient", "url malformed");
            e.printStackTrace();

        }
    }

    public void setMethod(String method) {
       this.method = method;
    }

    public String execute() throws IOException, JSONException, InterruptedException {



        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        if(params.size()>0)
        {
            addParams(urlConnection);
        }
        int responseCode = urlConnection.getResponseCode();
        String ret;




        if(responseCode == 200) {
            in = new BufferedInputStream(urlConnection.getInputStream());
            ret = readStream(in);
            // Log.i("RESULT", ret);
            //JSONObject json = new JSONObject(ret); // Convert text to object
            //Log.i("JSON",json.toString());
        }
        else
            ret =  "Code: " + responseCode;


        urlConnection.disconnect();

        if(params != null)
        params.clear();

        this.ret = ret;

        return ret;



    }

    private void addParams(HttpsURLConnection conn) throws IOException {
        conn.setDoInput(true);
        conn.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder();
        for(int i = 0; i < params.size();i++)
        {
            builder.appendQueryParameter(params.get(i).first,params.get(i).second);
        }

        String query = builder.build().getEncodedQuery();

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();
        conn.connect();
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        catch (IOException e) {
            return e.getMessage();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    return e.getMessage();
                }
            }
        }
        return response.toString();
    }


}
