package com.holamundo.ciudaduniversitariainteligente;

import android.app.Activity;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.InputStream;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflo mi listView
        final View rootView = inflater.inflate(R.layout.agenda_unl, container, false);

        int a = 1;
        if(a == 1) {

            final ListView listView = (ListView) rootView.findViewById(R.id.listAgendaView);
            List<Map<String, String>> crsList = new ArrayList<Map<String,String>>();

            Map<String, String> aug = new HashMap<String, String>();
            aug.put("nameEvento", "Temporada de verano");
            aug.put("fechaEvento", "14/01/2018");
            crsList.add(aug);
            Map<String, String> cong = new HashMap<String, String>();
            cong.put("nameEvento", "Torneo Truco");
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



}
