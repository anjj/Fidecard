package com.einitia.fidecardpager;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class InitialActivity extends SherlockListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Se crea el adapter para llenar la lista de forma automática, 
		//si se quiere meter que pille cambios al vuelo creo que habría que heredar
		//la propiedad notificable (no sé como se dice en Java)
		setListAdapter(new SimpleAdapter(this, getData(),
			 android.R.layout.simple_list_item_1, new String[] { "title" },
			 new int[] { android.R.id.text1 }));
		getListView().setTextFilterEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.initial_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
			case R.id.scanQR:
				Intent intentScan = new Intent(this, ScanActivity.class);
				intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    	startActivity(intentScan);
		    	return true;
			default:
				return super.onOptionsItemSelected(item);
	    }
	}   
	
	//Devuelve los datos
    protected List<Map<String, String>> getData() {
        List<Map<String, String>> myData = new ArrayList<Map<String, String>>();

        for (int i = 1; i < 20; i++){
        	addItem(myData, "Categoria " + i,  String.valueOf(i));
        }
        
        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    //Funcion para añadir un registro
    protected void addItem(List<Map<String, String>> data, String name, String id) {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("title", name);
        temp.put("ItemId", id);
        data.add(temp);
    }
    
    //Esto sirve para ordenar por nombre cuando llamas a sort
    private final static Comparator<Map<String, String>> sDisplayNameComparator =
        new Comparator<Map<String, String>>() {
        private final Collator   collator = Collator.getInstance();

        public int compare(Map<String, String> map1, Map<String, String> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    //Cuando se hace click, saca el item seleccionado
    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, String> map = (Map<String, String>)l.getItemAtPosition(position);

        //Alert para probar
        String itemId = (String) map.get("ItemId");
        
        // nueva actividad
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        in.putExtra("idCategoria", itemId);
        startActivity(in);
        
        /*
        new AlertDialog.Builder(this)
        .setTitle("Selccionado")
        .setMessage("Elemento seleccionado: " + itemId)
        .setCancelable(true)
        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) { 
        		dialog.dismiss();
        	}
        })
        .show();
        */
    }
}
