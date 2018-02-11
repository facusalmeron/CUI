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

/**
 * Created by Facundo on 7/1/2018.
 */

public class InfoFacultades extends Fragment {

    private Spinner spinnerBusqueda = null;
    private Button botonBusqueda = null;
    private MainActivity oMainActivity = null;

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
        final TextView nombreFacultad = (TextView) rootView.findViewById(R.id.nombreFacultad);
        final TextView localidadFacultad = (TextView) rootView.findViewById(R.id.localidadFacultad);
        final TextView direccionFacultad = (TextView) rootView.findViewById(R.id.direccionFacultad);
        final TextView telefonoFacultad = (TextView) rootView.findViewById(R.id.telefonoFacultad);
        final TextView faxFacultad = (TextView) rootView.findViewById(R.id.faxFacultad);
        final TextView paginaFacultad = (TextView) rootView.findViewById(R.id.paginaFacultad);

        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!spinnerBusqueda.getSelectedItem().toString().equals(" --- ")) {
                    switch (spinnerBusqueda.getSelectedItem().toString()){
                        case "FICH":
                            imagenFacultad.setImageResource(R.drawable.fich_info);
                            nombreFacultad.setText("Facultad de Ingeniería y Ciencias Hídricas");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel.:(+54) (0342) 4575233 / 34 / 39 / 44 / 45");
                            faxFacultad.setText("Fax:(+54) (0342) 457-5224");
                            paginaFacultad.setText("http://fich.unl.edu.ar/");
                            break;
                        case "FBCB":
                            imagenFacultad.setImageResource(R.drawable.fbcb_info);
                            nombreFacultad.setText("Facultad de Bioquímica y Ciencias Biológicas");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel.: +54 (342) 4575215 /4575216");
                            faxFacultad.setText("Fax: +54 (342) 4575216");
                            paginaFacultad.setText("http://www.fbcb.unl.edu.ar/");
                            break;
                        case "FCM":
                            imagenFacultad.setImageResource(R.drawable.fcm_info);
                            nombreFacultad.setText("Facultad de Ciencias Médicas");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel: (54) 0342 - 4575116/7");
                            faxFacultad.setText("Fax: -----");
                            paginaFacultad.setText("http://www.fcm.unl.edu.ar/");
                            break;
                        case "FADU":
                            imagenFacultad.setImageResource(R.drawable.fadu_info);
                            nombreFacultad.setText("Facultad de Arquitectura, Diseño y Urbanismo");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel: +54 (342) 4575100 ");
                            faxFacultad.setText("Fax: +54 (342) 4575112");
                            paginaFacultad.setText("http://www.fadu.unl.edu.ar/");
                            break;
                        case "FHUC":
                            imagenFacultad.setImageResource(R.drawable.fhuc_info);
                            nombreFacultad.setText("Facultad de Humanidades y Ciencias");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel: +54 (0342) 4575105");
                            faxFacultad.setText("Fax: +54 (0342) 4575105");
                            paginaFacultad.setText("http://www.fhuc.unl.edu.ar/");
                            break;
                        case "ISM":
                            imagenFacultad.setImageResource(R.drawable.ism_info);
                            nombreFacultad.setText("Instituto Superior de Música");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Ciudad Universitaria. Ruta Nacional N° 168 - Km 472,4");
                            telefonoFacultad.setText("Tel: +54 (0342) 4511622/3");
                            faxFacultad.setText("Fax: ----------");
                            paginaFacultad.setText("http://www.ism.unl.edu.ar/");
                            break;
                        case "FCE":
                            imagenFacultad.setImageResource(R.drawable.fce_info);
                            nombreFacultad.setText("Facultad de Ciencias Económicas");
                            localidadFacultad.setText("(3000) Santa Fe - Argentina");
                            direccionFacultad.setText("Moreno 2557");
                            telefonoFacultad.setText("Tel: (54) 0342 - 4571180 / 4571181");
                            faxFacultad.setText("Fax: +54 (342) 4551222");
                            paginaFacultad.setText("http://www.fce.unl.edu.ar/");
                            break;
                    }
                }
                else{
                    imagenFacultad.setImageResource(R.drawable.info);
                    nombreFacultad.setText("");
                    localidadFacultad.setText("");
                    direccionFacultad.setText("");
                    telefonoFacultad.setText("");
                    faxFacultad.setText("");
                    paginaFacultad.setText("");
                    Toast.makeText(getActivity(), "Primero debe ingresar la Facultad a buscar",Toast.LENGTH_LONG).show();
                }
            }
        });


            imagenFacultad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (paginaFacultad.getText().toString().length() > 0){
                        Uri uri = Uri.parse(paginaFacultad.getText().toString());
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
