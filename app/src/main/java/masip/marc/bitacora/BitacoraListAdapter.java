package masip.marc.bitacora;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;


public class BitacoraListAdapter extends ArrayAdapter<BitacoraItem> {
    public BitacoraListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BitacoraItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Crear un nou View si és necessari (no cal si convertView no és null)
        View root = convertView; // arrel d'un item de la llista
        if (root == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            root = inflater.inflate(R.layout.bitacora_item, parent, false);
        }

        TextView text = (TextView) root.findViewById(R.id.text);
        BitacoraItem item = getItem(position);
        text.setText(item.getText());
        TextView date = (TextView) root.findViewById(R.id.Data);
        //date.setText(getDate());----------------------------
        return root;
    }
}
