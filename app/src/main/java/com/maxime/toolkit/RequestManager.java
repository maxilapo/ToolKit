package com.maxime.toolkit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager
{
    String ClassName = "RequestManager";

    private OkHttpClient _httpClient;

    String GET = "GET";
    String POST = "POST";
    String port = "6969";
    String URL = "http://10.0.2.2:3000/api/";

    public RequestManager() { }

    String getRequest(String url) throws IOException
    {
        Log.d("max_HTTPREQUEST", "GET method on " + url);
        _httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = _httpClient.newCall(request).execute();
        String resp = response.body().string();

        return resp;
    }

    String postRequest(String url) throws IOException
    {
        Log.d("max_HTTPREQUEST", "POST method on " + url);
        _httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse(""), "")).build();
        Response response = _httpClient.newCall(request).execute();
        String resp = response.body().string();

        return resp;
    }

    public class HttpRequest extends AsyncTask<String, Integer, String[]> {
        @Override
        protected String[] doInBackground(String... params){

            String request = URL + params[2].toString();
            String[] result = new String[1];

            try {
                if (params[1].toString() == GET)
                    result[0] = getRequest(request);
                else
                    result[0] = postRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    public String[] httpRequest(String method, String action) throws ExecutionException, InterruptedException {
        return new HttpRequest().execute("", method, action).get();
    }
}
