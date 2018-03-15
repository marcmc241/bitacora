package masip.marc.bitacora;

import java.util.Date;

/**
 * Created by marcmc6 on 13/03/2018.
 */

public class BitacoraItem {
    private String text;
    private Date date;

    //creat automaticament amb el generador d'android studio (code>generate o alt+insert)

    public BitacoraItem(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public BitacoraItem(String text) {//si només rep el text
        this.text = text;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
