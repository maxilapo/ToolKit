package com.maxime.toolkit;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestManager
{
    String ClassName = "RequestManager";

    public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient _httpClient;

    String GET = "GET";
    String POST = "POST";
    String URL = "http://10.0.2.2:3000/api/";
    //String URL = "http://172.20.10.2:3000/api/";


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

    String postRequest(String url, String jsonString) throws IOException
    {
        Log.d("max_HTTPREQUEST", "POST method on " + url);
        _httpClient = new OkHttpClient();

        RequestBody body = RequestBody.create(JSONTYPE, jsonString);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = _httpClient.newCall(request).execute();

        String resp = response.body().string();
        return resp;
    }

    public class HttpRequest extends AsyncTask<String, Integer, String[]> {
        @Override
        protected String[] doInBackground(String... params){

            String request = URL + params[2].toString();
            String[] result = new String[1];
            String jsonString = params[3].toString();

            try {
                if (params[1].toString() == GET)
                    result[0] = getRequest(request);
                else
                    result[0] = postRequest(request, jsonString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    public String[] httpRequest(String method, String action, String jsonString) throws ExecutionException, InterruptedException {

        return new HttpRequest().execute("", method, action, jsonString).get();
    }
}
