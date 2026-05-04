package com.utn.libreriaapi_rincon;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiClient {
    private static final String BASE_URL = "https://api-libreria-rest.vercel.app/";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Helper to handle the "value" wrapper in the API responses.
     */
    private static <T> List<T> parseList(String json, Type type) {
        try {
            JsonElement element = gson.fromJson(json, JsonElement.class);
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.has("value")) {
                    return gson.fromJson(obj.get("value"), type);
                }
            }
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // --- AUTOR METHODS ---

    public static List<autor> getAutores() throws Exception {
        final List<autor>[] result = new List[1];
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "autor").build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = parseList(body != null ? body.string() : "[]", new TypeToken<List<autor>>() {}.getType());
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static autor getAutor(int id) throws Exception {
        final autor[] result = {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "autor/" + id).build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = gson.fromJson(body != null ? body.string() : "null", autor.class);
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static void CreateAutor(autor autor) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "autor?" +
                        "aut_nombre=" + URLEncoder.encode(autor.aut_nombre, "UTF-8") +
                        "&aut_pais=" + URLEncoder.encode(autor.aut_pais, "UTF-8");
                if (autor.aut_id != 0) url += "&aut_id=" + autor.aut_id;

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void UpdateAutor(autor autor) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "autor/" + autor.aut_id + "?" +
                        "aut_nombre=" + URLEncoder.encode(autor.aut_nombre, "UTF-8") +
                        "&aut_pais=" + URLEncoder.encode(autor.aut_pais, "UTF-8");

                Request request = new Request.Builder()
                        .url(url)
                        .put(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void DeleteAutor(int id) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "autor/" + id).delete().build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    // --- LIBRO METHODS ---

    public static List<Libro> getLibros() throws Exception {
        final List<Libro>[] result = new List[1];
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libro").build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = parseList(body != null ? body.string() : "[]", new TypeToken<List<Libro>>() {}.getType());
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static Libro getLibro(int id) throws Exception {
        final Libro[] result = {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libro/" + id).build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = gson.fromJson(body != null ? body.string() : "null", Libro.class);
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static void createLibro(Libro libro) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "libro?" +
                        "lib_titulo=" + URLEncoder.encode(libro.lib_titulo, "UTF-8") +
                        "&lib_editorial=" + URLEncoder.encode(libro.lib_editorial, "UTF-8");
                if (libro.lib_id != 0) url += "&lib_id=" + libro.lib_id;

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void updateLibro(Libro libro) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "libro/" + libro.lib_id + "?" +
                        "lib_titulo=" + URLEncoder.encode(libro.lib_titulo, "UTF-8") +
                        "&lib_editorial=" + URLEncoder.encode(libro.lib_editorial, "UTF-8");

                Request request = new Request.Builder()
                        .url(url)
                        .put(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void deleteLibro(int id) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libro/" + id).delete().build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    // --- AUTOR_LIBRO METHODS (libros-autores endpoint) ---

    public static List<autor_libro> getAutoresLibros() throws Exception {
        final List<autor_libro>[] result = new List[1];
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libros-autores").build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = parseList(body != null ? body.string() : "[]", new TypeToken<List<autor_libro>>() {}.getType());
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static autor_libro getAutorLibro(int id) throws Exception {
        final autor_libro[] result = {null};
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libros-autores/" + id).build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                result[0] = gson.fromJson(body != null ? body.string() : "null", autor_libro.class);
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
        return result[0];
    }

    public static void createAutorLibro(autor_libro al) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "libros-autores?" +
                        "aut_id=" + al.aut_id +
                        "&lib_id=" + al.lib_id +
                        "&aut_lib_autor_principal=" + al.aut_lib_autor_principal;
                if (al.aut_lib_id != 0) url += "&aut_lib_id=" + al.aut_lib_id;

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void updateAutorLibro(autor_libro al) throws Exception {
        Thread t = new Thread(() -> {
            try {
                String url = BASE_URL + "libros-autores/" + al.aut_lib_id + "?" +
                        "aut_id=" + al.aut_id +
                        "&lib_id=" + al.lib_id +
                        "&aut_lib_autor_principal=" + al.aut_lib_autor_principal;

                Request request = new Request.Builder()
                        .url(url)
                        .put(RequestBody.create("", null))
                        .build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }

    public static void deleteAutorLibro(int id) throws Exception {
        Thread t = new Thread(() -> {
            try {
                Request request = new Request.Builder().url(BASE_URL + "libros-autores/" + id).delete().build();
                client.newCall(request).execute();
            } catch (Exception e) { e.printStackTrace(); }
        });
        t.start();
        t.join();
    }
}
