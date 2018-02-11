package com.holamundo.ciudaduniversitariainteligente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import java.util.Calendar;
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

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date fevento = new Date(Long.parseLong(dto.getFechaEvento())*1000);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.parseLong(dto.getFechaEvento())*1000);
                dto.setFevento(cal);
                String fecha = df.format(fevento);
                aug.put("fechaEvento",fecha);

                crsList.add(aug);
            }

            String[] keys = {"nameEvento","fechaEvento"};
            int[] widgetIds = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter crsAdapter = new SimpleAdapter(getActivity(),crsList,android.R.layout.simple_list_item_2, keys,widgetIds);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String enlace = "https:" + listaDto.get(i).getUrlEvento();
                    Calendar fevento = listaDto.get(i).getFevento();
                    String nEvento = listaDto.get(i).getNombreEvento();
                    showAlertDialog(fevento, enlace, nEvento);


                }
            });


            listView.setAdapter(crsAdapter);

        }else{
            Toast.makeText(getActivity(), "Ud. no posee conexión a internet para ver ésta sección",Toast.LENGTH_LONG).show();
        }

        return rootView;
    }


    public void setMainActivity(MainActivity oMA){
        this.oMainActivity = oMA;
    }

    public void showAlertDialog(final Calendar fechaEvento, final String urlEvento, final String nombreEvento){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Confirme opción a realizar");
        alertDialog.setMessage("Ud. puede Agendar el evento o visitar la página del mismo");
        alertDialog.setPositiveButton("Agendar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agregar(fechaEvento, nombreEvento);
            }
        });

        alertDialog.setNegativeButton("Ver URL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(urlEvento);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    public void agregar(Calendar cal, String nombreEvento){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 *60*1000);
        intent.putExtra(CalendarContract.Events.TITLE, nombreEvento);
        startActivity(intent);
    }


}
