package com.holamundo.ciudaduniversitariainteligente;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by usuario on 9/2/2018.
 */

public class AgendaDto {

    private String nombreEvento;
    private String fechaEvento;
    private String urlEvento;
    private Calendar fevento;

    public Calendar getFevento() {
        return fevento;
    }

    public void setFevento(Calendar fevento) {
        this.fevento = fevento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getUrlEvento() {
        return urlEvento;
    }

    public void setUrlEvento(String urlEvento) {
        this.urlEvento = urlEvento;
    }
}
