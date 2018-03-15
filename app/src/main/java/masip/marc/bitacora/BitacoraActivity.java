package masip.marc.bitacora;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BitacoraActivity extends AppCompatActivity {

    public static final String FILENAME = "items.txt";
    public static final byte[] MAX_BYTES = new byte[10000];
    private ListView list;
    private ArrayList<BitacoraItem> items; // Model de dades (array de BitacoraItem)
    private BitacoraListAdapter adapter;
    private EditText new_item;

    private void writeItemList(){//agafar els items i guardar-los en un fitxer
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);//en privat només te permís d'accedir als fitxers la pròpia app
            for (BitacoraItem item:items) {//per cada shoppingitem en l'array items
                String line = String.format("%llu;%s\n", item.getDate().getTime(), item.getText());
                fos.write(line.getBytes());
            }
            fos.close();//tanquem el fitxer
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            Toast.makeText(this, "No es pot escriure el fitxer", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean readItemList(){
        items= new ArrayList<>();//resetejem arraylist
        try {
            FileInputStream fis = openFileInput(FILENAME);
            byte[] buffer = MAX_BYTES;//10kb - el que pot llegir de cop
            int nread = fis.read(buffer);
            if (nread>0){//si ha llegit més de 0 bytes
                String content = new String(buffer,0,nread);//string a partir del buffer començant des de 0 i acabant a nread bytes
                String[] lines = content.split("\n");//separa per salts de linia
                for(String line:lines){
                    if(!line.isEmpty()){
                        String[] parts = line.split(";");
                        long time = Long.parseLong(parts[0]);
                        Date date = new Date(time);
                        items.add(new BitacoraItem(parts[1],date));
                    }
                }
                fis.close();
            }
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            Toast.makeText(this, "No es pot escriure el fitxer", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    @Override
    protected void onStop() {//metode que es crida quan marxes de l'aplicació (no la tens en primer pla)
        super.onStop();
        writeItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);


        if(!readItemList()){
            Toast.makeText(this, "Benvingut al ShoppingList(TM)", Toast.LENGTH_SHORT).show();
        }


        list = (ListView) findViewById(R.id.list);
        new_item = (EditText) findViewById(R.id.new_item);

        adapter = new BitacoraListAdapter(this, R.layout.bitacora_item, items);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                onRemoveItem(pos);
                return true;
            }
        });

        //exemple d'ús de date
        Date date = new Date();//data en ms des de 1970 (en base a l'instant actual del dispositiu)
        Calendar calendar = new GregorianCalendar();//creem un calendari. el calendari només el creem a l'inici i l'anem utilitzant on sigui
        calendar.setTime(date);//posem el calendari a aquesta data
        int year = calendar.get(Calendar.YEAR);//obtenim l'any
        int month = calendar.get(Calendar.MONTH);//obtenim el mes
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        Toast.makeText(this, String.format("%02d/%02d/%04d %02d:%02d", day, month+1, year, hour, min), Toast.LENGTH_LONG).show();
    }

    private void onRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(
                String.format(Locale.getDefault(),
                        "Estàs segur que vols esborrar '%s'",
                        items.get(pos).getText())
        );
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                items.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void onAddItem(View view) {
        String item_text = new_item.getText().toString();
        if (!item_text.isEmpty()) {
            items.add(new BitacoraItem(item_text));
            adapter.notifyDataSetChanged();
            new_item.setText("");
            list.smoothScrollToPosition(items.size() - 1);
        }
    }
}//shift+f6 = refractor (canviar nom a tot arreu)
//ctrl+k = commit
