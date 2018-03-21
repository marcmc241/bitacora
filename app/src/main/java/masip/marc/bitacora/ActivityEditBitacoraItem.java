package masip.marc.bitacora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityEditBitacoraItem extends AppCompatActivity {

    private EditText edit_text;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bitacora_item);
        edit_text = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        if (text!=null){
            edit_text.setText(text);
        }
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("text", edit_text.getText().toString());//omplim intent amb la id text i el valor el text que ha escrit l'ueuari
        setResult(RESULT_OK, data);//setejem el botó que retorni result ok. En cas que cliqués el botó "back" retornaria result_cancel
        finish();
    }
}
