package masip.marc.bitacora;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public String getDateString() {
        Calendar calendar = new GregorianCalendar();//creem un calendari. el calendari només el creem a l'inici i l'anem utilitzant on sigui
        calendar.setTime(date);//posem el calendari a aquesta data
        int year = calendar.get(Calendar.YEAR);//obtenim l'any
        int month = calendar.get(Calendar.MONTH);//obtenim el mes
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        return String.format("%02d/%02d/%04d %02d:%02d", day, month+1, year, hour, min);
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
