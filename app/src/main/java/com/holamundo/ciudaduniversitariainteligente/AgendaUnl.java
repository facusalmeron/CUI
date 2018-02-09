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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Facundo on 7/2/2018.
 */

public class AgendaUnl extends Fragment{
    private MainActivity oMainActivity = null;

    List<AgendaDto> listaDto;

    public List<AgendaDto> getListaDto() {
        return listaDto;
    }

    public void setListaDto(List<AgendaDto> listaDto) {
        this.listaDto = listaDto;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflo mi listView
        final View rootView = inflater.inflate(R.layout.agenda_unl, container, false);

        if(listaDto.size() > 0) {

            final ListView listView = (ListView) rootView.findViewById(R.id.listAgendaView);
            List<Map<String, String>> crsList = new ArrayList<Map<String,String>>();

            for (AgendaDto dto : listaDto){
                Map<String, String> aug = new HashMap<String, String>();
                aug.put("nameEvento", dto.getNombreEvento());

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date fevento = new Date(Long.parseLong(dto.getFechaEvento())*1000);
                String fecha = df.format(fevento);
                aug.put("fechaEvento",fecha);

                crsList.add(aug);
            }

            String[] keys = {"nameEvento","fechaEvento"};
            int[] widgetIds = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter crsAdapter = new SimpleAdapter(getActivity(),crsList,android.R.layout.simple_list_item_2, keys,widgetIds);
/*
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String enlace = listaDto.get(i).getUrlEvento();
                    getActivity().setContentView(R.layout.web_view_agenda);
                    WebView browser =(WebView) listView.findViewById(R.id.webViewAgenda);
                    browser.getSettings().setJavaScriptEnabled(true);
                    browser.setWebViewClient(new WebViewClient());
                    browser.loadUrl(enlace);

                }
            });
*/

            listView.setAdapter(crsAdapter);

        }else{
            Toast.makeText(getActivity(), "Ud. no posee conexión a internet para ver ésta sección",Toast.LENGTH_LONG).show();
        }

        return rootView;
    }


    public void setMainActivity(MainActivity oMA){
        this.oMainActivity = oMA;
    }

    private class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }





}
