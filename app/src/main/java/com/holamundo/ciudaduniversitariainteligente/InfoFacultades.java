package com.holamundo.ciudaduniversitariainteligente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 7/1/2018.
 */

public class InfoFacultades extends Fragment {

    private Spinner spinnerBusqueda = null;
    private Button botonBusqueda = null;
    private MainActivity oMainActivity = null;
    private int contador = 0;
    public List<InfoDto> getInformaciones() {
        return informaciones;
    }

    public void setInformaciones(List<InfoDto> informaciones) {
        this.informaciones = informaciones;
    }

    private List<InfoDto> informaciones = new ArrayList<InfoDto>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.info_facultades, container, false);

        spinnerBusqueda = (Spinner) rootView.findViewById(R.id.busquedaSpinnerInfo);
        botonBusqueda = (Button) rootView.findViewById(R.id.boton_busquedaInfo);
        oMainActivity = (MainActivity) getActivity();

        String[] itemsSB1 = {" --- ", "FICH", "FBCB", "FCM", "FADU", "FHUC", "ISM", "FCE"};
        ArrayAdapter<String> arraySB1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, itemsSB1);
        spinnerBusqueda.setAdapter(arraySB1);

        final ImageView imagenFacultad = (ImageView) rootView.findViewById(R.id.imagenFacultad);
        imagenFacultad.setImageResource(R.drawable.info);
        final TextView informacionFacultad = (TextView) rootView.findViewById(R.id.informacionFacultad);

        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!spinnerBusqueda.getSelectedItem().toString().equals(" --- ")) {
                    switch (spinnerBusqueda.getSelectedItem().toString()){
                        case "FICH":
                            imagenFacultad.setImageResource(R.drawable.fich_info);
                            informacionFacultad.setText(informaciones.get(1).getNombre()+"\n"+informaciones.get(1).getLocalidad()+"\n"+informaciones.get(1).getDireccion()+"\n"+informaciones.get(1).getTelefono()+"\n"+informaciones.get(1).getFax()+"\n"+informaciones.get(1).getPagina());
                            contador = 1;
                            break;
                        case "FBCB":
                            imagenFacultad.setImageResource(R.drawable.fbcb_info);
                            informacionFacultad.setText(informaciones.get(2).getNombre()+"\n"+informaciones.get(2).getLocalidad()+"\n"+informaciones.get(2).getDireccion()+"\n"+informaciones.get(2).getTelefono()+"\n"+informaciones.get(2).getFax()+"\n"+informaciones.get(2).getPagina());
                            contador = 2;
                            break;
                        case "FCM":
                            imagenFacultad.setImageResource(R.drawable.fcm_info);
                            informacionFacultad.setText(informaciones.get(3).getNombre()+"\n"+informaciones.get(3).getLocalidad()+"\n"+informaciones.get(3).getDireccion()+"\n"+informaciones.get(3).getTelefono()+"\n"+informaciones.get(3).getFax()+"\n"+informaciones.get(3).getPagina());
                            contador = 3;
                            break;
                        case "FADU":
                            imagenFacultad.setImageResource(R.drawable.fadu_info);
                            informacionFacultad.setText(informaciones.get(4).getNombre()+"\n"+informaciones.get(4).getLocalidad()+"\n"+informaciones.get(4).getDireccion()+"\n"+informaciones.get(4).getTelefono()+"\n"+informaciones.get(4).getFax()+"\n"+informaciones.get(4).getPagina());
                            contador = 4;
                            break;
                        case "FHUC":
                            imagenFacultad.setImageResource(R.drawable.fhuc_info);
                            informacionFacultad.setText(informaciones.get(5).getNombre()+"\n"+informaciones.get(5).getLocalidad()+"\n"+informaciones.get(5).getDireccion()+"\n"+informaciones.get(5).getTelefono()+"\n"+informaciones.get(5).getFax()+"\n"+informaciones.get(5).getPagina());
                            contador = 5;
                            break;
                        case "ISM":
                            imagenFacultad.setImageResource(R.drawable.ism_info);
                            informacionFacultad.setText(informaciones.get(6).getNombre()+"\n"+informaciones.get(6).getLocalidad()+"\n"+informaciones.get(6).getDireccion()+"\n"+informaciones.get(6).getTelefono()+"\n"+informaciones.get(6).getFax()+"\n"+informaciones.get(6).getPagina());
                            contador = 6;
                            break;
                        case "FCE":
                            imagenFacultad.setImageResource(R.drawable.fce_info);
                            informacionFacultad.setText(informaciones.get(7).getNombre()+"\n"+informaciones.get(7).getLocalidad()+"\n"+informaciones.get(7).getDireccion()+"\n"+informaciones.get(7).getTelefono()+"\n"+informaciones.get(7).getFax()+"\n"+informaciones.get(7).getPagina());
                            contador = 7;
                            break;
                    }
                }
                else{
                    imagenFacultad.setImageResource(R.drawable.info);
                    informacionFacultad.setText(informaciones.get(0).getNombre()+"\n"+informaciones.get(0).getLocalidad()+"\n"+informaciones.get(0).getDireccion()+"\n"+informaciones.get(0).getTelefono()+"\n"+informaciones.get(0).getFax()+"\n"+informaciones.get(0).getPagina());
                    contador = 0;
                    Toast.makeText(getActivity(), "Primero debe ingresar la Facultad a buscar",Toast.LENGTH_LONG).show();
                }
            }
        });


           imagenFacultad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contador != 0){
                        Uri uri = Uri.parse(informaciones.get(contador).getPagina());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "Primero debe ingresar la Facultad a buscar",Toast.LENGTH_LONG).show();
                    }

                }
            });



    return rootView;
}
}
