package com.holamundo.ciudaduniversitariainteligente;

import android.app.Activity;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Facundo on 7/2/2018.
 */

public class AgendaUnl extends Fragment{
    private MainActivity oMainActivity = null;

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    private String prueba;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //new HttpAsyncTaskAgenda().execute("https://www.unl.edu.ar/agenda/webapp.php?act=getJerarquicos&cantidad=1");
        super.onCreate(savedInstanceState);

    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflo mi listView
        final View rootView = inflater.inflate(R.layout.agenda_unl, container, false);


        int a = 1;
        if(a == 1) {

            final ListView listView = (ListView) rootView.findViewById(R.id.listAgendaView);
            List<Map<String, String>> crsList = new ArrayList<Map<String,String>>();

            Map<String, String> aug = new HashMap<String, String>();
            aug.put("nameEvento", "Temporada de verano: actividades deportivas y recreativas en el Predio UNL-ATE");
            aug.put("fechaEvento", "14/01/2018");
            crsList.add(aug);
            Map<String, String> cong = new HashMap<String, String>();
            cong.put("nameEvento", "Evento 2");
            cong.put("fechaEvento", "22/02/2018");
            crsList.add(cong);

            String[] keys = {"nameEvento","fechaEvento"};
            int[] widgetIds = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter crsAdapter = new SimpleAdapter(getActivity(),crsList,android.R.layout.simple_list_item_2, keys,widgetIds);
            listView.setAdapter(crsAdapter);

        }



        return rootView;
    }


    public void setMainActivity(MainActivity oMA){
        this.oMainActivity = oMA;
    }
/*
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        int tamanio = result.length();
        result = result.substring(1, tamanio - 1);
        return result;

    }

    private class HttpAsyncTaskAgenda extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result); //Convierte String a JSONObject
                String probando = json.getJSONObject("1").getString("");
                json = json.getJSONObject("1");

                prueba = json.getJSONArray("").getString(1);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }*/



}
