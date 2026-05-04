package com.utn.libreriaapi_rincon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiClient {
    private static final String BASE_URL ="https://api-libreria-rest.vercel.app/";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final MediaType JSON =
            MediaType.parse("application/json");

    //Get autores
    public static String getAutores() throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL + "autor")
                .build();
        ResponseBody body = client.newCall(request).execute().body();
        String json = body != null ? body.string() : "[]";
        return gson.fromJson(json, new
                TypeToken<List<autor>>() {
                }.getType());
    }
    //Get autor
    public static autor getAutor(int id) throws Exception {
        final autor[] result= {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                    .url(BASE_URL + "autor/" + id)
                    .build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                String json = body != null ? body.string() : "[]";
                result[0] = gson.fromJson(json, autor.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join();
        return result[0];
    }
    //Post
    public static autor CreateAutor(autor autor) throws Exception {
        final autor[] result = {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + "autor")
                        .post(RequestBody.create(JSON, gson.toJson(autor)))
                        .build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                String json = body != null ? body.string() : "[]";
                result[0] = gson.fromJson(json, autor.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try{
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result[0];
    }

    //Put
    public static void UpdateAutor(autor autor) throws Exception {
        final autor[] result = {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + "autor/" + autor.aut_id)
                        .put(RequestBody.create(JSON, gson.toJson(autor)))
                        .build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                String json = body != null ? body.string() : "[]";
                result[0] = gson.fromJson(json, autor.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        try{
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            }
    }
    //Delete
    public static void DeleteAutor(int id) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + "autor/" + id)
                        .delete()
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        );
        t.start();
        try{
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
