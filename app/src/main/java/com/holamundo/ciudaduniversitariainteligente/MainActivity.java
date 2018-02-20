package com.holamundo.ciudaduniversitariainteligente;


import android.Manifest;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String[] INITIAL_PERMS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST = 1337;


    /* Atributos de la clase*/
    private ArmaCamino oArmaCamino = null;
    private MapsFragment mapsFragment = null;
    private FragmentManager fm = getSupportFragmentManager();
    private FloatingActionButton qrBoton = null;
    private TextView textClima = null;
    private IntentIntegrator scanIntegrator = new IntentIntegrator(this);
    private ultimasBusquedas ultimasBusquedas = null;
    private Menu menu = null;
    private BaseDatos CUdb = null;
    private List<AgendaDto> listaDto = new ArrayList<AgendaDto>();
    private Integer total = 100;
    /*Funciones*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ciudad Inteligente");
        setSupportActionBar(toolbar);

        //Instancio la base de datos
        CUdb = new BaseDatos(getApplicationContext(), "DBCUI", null, 206);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }

        //Instancio los objetos para ArmaCamino y el MapFragment
        oArmaCamino = new ArmaCamino(this);
        mapsFragment = new MapsFragment();
        ultimasBusquedas = new ultimasBusquedas();
        ultimasBusquedas.setMainActivity(this);

        //Agrego Nodos a mi vector de nodos en oArmaCamino
        cargaNodos();

        //Boton Flotante que está abajo a la derecha, para leer QR
        qrBoton = (FloatingActionButton) findViewById(R.id.fab);
        qrBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se procede con el proceso de scaneo
                scanIntegrator.initiateScan();
            }
        });

        //Agrego TextView abajo trayendo datos del webService del clima
        textClima = (TextView) findViewById(R.id.textWSClima);
        new HttpAsyncTask().execute("http://api.wunderground.com/api/e8cd515cb766a5bf/lang:SP/forecast/geolookup/q/-31.6358630444,-60.705443881,15.json");
        new HttpAsyncTaskAgenda().execute("https://www.unl.edu.ar/agenda/webapp.php?act=getJerarquicos&cantidad=" + String.valueOf(total));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Cambio el fragment por defecto por mi mapFragment
        fm.beginTransaction().replace(R.id.fragment_container, mapsFragment).addToBackStack(null).commit();
    }

    public static String GETAGENDA(String url) {
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
                result = convertInputStreamToStringAgenda(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToStringAgenda(InputStream inputStream) throws IOException {
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

            return GETAGENDA(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result); //Convierte String a JSONObject
                int contador = 1;
                while (contador < total){
                    AgendaDto dto = new AgendaDto();
                    dto.setNombreEvento(json.getJSONArray(String.valueOf(contador)).getString(1));
                    dto.setFechaEvento(json.getJSONArray(String.valueOf(contador)).getString(2));
                    dto.setUrlEvento(json.getJSONArray(String.valueOf(contador)).getString(3));
                    listaDto.add(dto);
                    contador++;
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

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
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result); //Convierte String a JSONObject

                String diaSemana = json.getJSONObject("forecast").
                        getJSONObject("simpleforecast").
                        getJSONArray("forecastday").
                        getJSONObject(0).
                        getJSONObject("date").getString("weekday_short");

                String dia = json.getJSONObject("forecast").
                        getJSONObject("simpleforecast").
                        getJSONArray("forecastday").
                        getJSONObject(0).
                        getJSONObject("date").getString("day");

                String mes = json.getJSONObject("forecast").
                        getJSONObject("simpleforecast").
                        getJSONArray("forecastday").
                        getJSONObject(0).
                        getJSONObject("date").getString("monthname_short");

                String temperatura = json.getJSONObject("forecast").
                        getJSONObject("simpleforecast").
                        getJSONArray("forecastday").getJSONObject(0).getJSONObject("high").getString("celsius");

                String ubicacion = json.getJSONObject("location").
                        getJSONObject("nearby_weather_stations").
                        getJSONObject("pws").
                        getJSONArray("station").getJSONObject(0).getString("city");

                textClima.setText(diaSemana + " " + dia + " " + mes + " - " + temperatura + "ºC - " + ubicacion);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fm.findFragmentById(R.id.fragment_container) instanceof MapsFragment) {
                finish();
            } else {
                fm.popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getTitle().charAt(0) == '*') {
                menu.getItem(i).setTitle(menu.getItem(i).getTitle().toString().substring(1));
                item.setTitle("*" + item.getTitle());
                break;
            }
        }
        /*Si estoy mostrando una polilinea, la cambio segun la opcion de piso seleccionada*/
        if (mapsFragment.modoPolilinea()) {
            if (item.toString().contains("Baja")) {
                mapsFragment.cambiarPolilinea(0);
                return true;
            } else {
                mapsFragment.cambiarPolilinea(Integer.parseInt(item.toString().substring(item.toString().indexOf(' ') + 1)));
            }
        }
        /*Esto es si estoy mostrando nodos*/
        else {
            if (item.toString().contains("Baja")) {
                mapsFragment.cambiarNodos(0);
                return true;
            } else {
                mapsFragment.cambiarNodos(Integer.parseInt(item.toString().substring(item.toString().indexOf(' ') + 1)));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Switch segun en que opcion del menu desplegable se selecciona
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.buscar) {
            if (!(fm.findFragmentById(R.id.fragment_container) instanceof Busqueda)) {
                mapsFragment.limpiarMapa();
                qrBoton.hide();
                menu.clear();
                Busqueda busqueda = new Busqueda();
                //fm.popBackStack();
                fm.beginTransaction().replace(R.id.fragment_container, busqueda).addToBackStack(null).commit();
            }

        } else if (id == R.id.mapa_completo) {
            mapsFragment.limpiarMapa();
            menu.clear();
            if (!(fm.findFragmentById(R.id.fragment_container) instanceof MapsFragment)) {
                qrBoton.show();
                fm.beginTransaction().replace(R.id.fragment_container, mapsFragment).addToBackStack(null).commit();
            }

        } else if (id == R.id.ultimas) {
            if (!(fm.findFragmentById(R.id.fragment_container) instanceof ultimasBusquedas)) {
                qrBoton.hide();
                menu.clear();
                //fm.popBackStack();
                fm.beginTransaction().replace(R.id.fragment_container, ultimasBusquedas).addToBackStack(null).commit();
            }

        } else if (id == R.id.infoFacultades){
            if (!(fm.findFragmentById(R.id.fragment_container) instanceof InfoFacultades)) {
                qrBoton.hide();
                menu.clear();
                InfoFacultades infoFacultades = new InfoFacultades();
                infoFacultades.setInformaciones(cargaInformacion());
                fm.beginTransaction().replace(R.id.fragment_container, infoFacultades).addToBackStack(null).commit();

            }
        }
        else if (id == R.id.agendaUnl){
            if (!(fm.findFragmentById(R.id.fragment_container) instanceof AgendaUnl)) {
                qrBoton.hide();
                menu.clear();
                AgendaUnl agenda = new AgendaUnl();
                agenda.setListaDto(listaDto);
                fm.beginTransaction().replace(R.id.fragment_container, agenda).addToBackStack(null).commit();

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    Mostrar busqueda llama a las funciones del mapFragment que:
    -muestran un conjunto de puntos en el mapa
    -muestran una polilinea desde el punto mas cercano hasta el objetivo
    *setPuntoMasCercano setea en oArmaCamino el nodo mas cercano a la posición donde estoy parado
    Luego reemplazo el fragment de Busqueda por el de mapa
    */
    public void mostrarBusqueda(String Edificio, String Nombre) {
        mapsFragment.setPisoActual(0);
        if (Edificio.equals("*")) {
            mapsFragment.mostrarNodos(oArmaCamino.nodosMapa(Nombre));
            menu.clear();
            menu.add("Planta Baja");
            for (int i = 1; i < mapsFragment.getCantPisos(); i++) {
                menu.add("Piso " + i);
            }
            menu.getItem(mapsFragment.getPisoActual()).setTitle("*" + menu.getItem(mapsFragment.getPisoActual()).getTitle());
            getMenuInflater().inflate(R.menu.main, menu);
        } else {
            oArmaCamino.setPuntoMasCercano(mapsFragment.getPosicion(), mapsFragment.getPisoActual());
            mapsFragment.dibujaCamino(oArmaCamino.camino(Edificio, Nombre));
            menu.clear();
            menu.add("Planta Baja");
            for (int i = 1; i < mapsFragment.getCantPisos(); i++) {
                menu.add("Piso " + i);
            }
            menu.getItem(mapsFragment.getPisoActual()).setTitle("*" + menu.getItem(mapsFragment.getPisoActual()).getTitle());
            getMenuInflater().inflate(R.menu.main, menu);
            String texto = "Su objetivo está en " + oArmaCamino.getPisoObjetivo();
            Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG).show();
        }
        fm.beginTransaction().replace(R.id.fragment_container, mapsFragment).addToBackStack(null).commit();
        qrBoton.show();
    }

    //Funcion que le pasa a oArmaCamino un edificio y devuelve un Vector con todas las aulas de ese edificio
    public Vector<Punto> verAulasPorEdificio(String Edificio) {
        return oArmaCamino.verAulasPorEdificio(Edificio);
    }

    //Funcion que le pasa a oArmaCamino un edificio y devuelve un Vector con todas las oficinas de ese edificio
    public Vector<Punto> verOficinasPorEdificio(String Edificio) {
        return oArmaCamino.verOficinasPorEdificio(Edificio);
    }

    //Funcion que le pasa a oArmaCamino un edificio y devuelve un Vector con todas los laboratorios de ese edificio
    public Vector<Punto> verLaboratoriosPorEdificio(String Edificio) {
        return oArmaCamino.verLaboratoriosPorEdificio(Edificio);
    }


    private List<InfoDto> cargaInformacion() {
        List<InfoDto> infos = new ArrayList<InfoDto>();
        SQLiteDatabase db1 = CUdb.getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT *  FROM Informacion", null);
        c.moveToFirst();

        if (c.getCount() > 0) {
            do {
                InfoDto info = new InfoDto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
                infos.add(info);
            } while (c.moveToNext());
        }
        return infos;
    }


    //Funcion para crear nodos del mapa y sus conexiones
    private void cargaNodos() {
        Vector<Punto> puntos = new Vector<>();
        SQLiteDatabase db1 = CUdb.getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT *  FROM Punto", null);
        c.moveToFirst();

        //Creo y agrego los nodos
        if (c.getCount() > 0) {
            do {
                Punto oPunto = new Punto(c.getInt(0), Double.parseDouble(c.getString(1)), Double.parseDouble(c.getString(2)), c.getString(3), c.getInt(4), c.getString(5), c.getInt(6));
                puntos.add(oPunto);
            } while (c.moveToNext());
        }

        //Genero las conexiones
        for (int i = 0; i < puntos.size(); i++) {
            Cursor d = db1.rawQuery("SELECT idHasta FROM Conexiones WHERE idDesde = " + puntos.elementAt(i).getId(), null);
            d.moveToFirst();
            if (d.getCount() > 0) {
                do {
                    puntos.elementAt(i).addVecino(puntos.elementAt(d.getInt(0)));
                } while (d.moveToNext());
            }
            oArmaCamino.addNodo(puntos.elementAt(i));
        }
        //Cierro DB
        puntos.clear();
        db1.close();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = scanningResult.getContents();
            mapsFragment.setLat(Double.parseDouble(scanContent.toString().substring(0, (scanContent.toString().indexOf(',')))));
            mapsFragment.setLon(Double.parseDouble(scanContent.toString().substring((scanContent.toString().indexOf(',')) + 1, scanContent.length() - 2)));
            mapsFragment.setPisoActual(Integer.parseInt(scanContent.toString().substring(scanContent.toString().length() - 1)));
            mapsFragment.actualizaPosicion();

            //Actualizo el * del menu de pisos cuando cambio el piso por QR
            if (mapsFragment.getPisoActual() + 1 <= mapsFragment.getCantPisos()) {
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i).getTitle().charAt(0) == '*') {
                        menu.getItem(i).setTitle(menu.getItem(i).getTitle().toString().substring(1));
                        menu.getItem(mapsFragment.getPisoActual()).setTitle("*" + menu.getItem(mapsFragment.getPisoActual()).getTitle());
                        break;
                    }
                }
            }
        } else {
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
