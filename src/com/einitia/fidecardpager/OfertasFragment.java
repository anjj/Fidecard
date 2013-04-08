package com.einitia.fidecardpager;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public final class OfertasFragment extends SherlockListFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("ojo!!!", "fragment Creado");
        
        String url = "http://fidecard.es/appmobile/ws/getEstablishment";
        
        ListViewLoaderTask llt = new ListViewLoaderTask();
        
        llt.execute(url);
        
    }
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	    Bundle savedInstanceState) {
    	
    	Log.d("ojo!!!", "Vista Creada");
	    View view = inflater.inflate(R.layout.descuentos_list_fragment, null);
	    return view;
    }
    */
    @Override
    @SuppressWarnings("unchecked")
	public void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, String> map = (Map<String, String>)l.getItemAtPosition(position);
        
        String itemId = (String) map.get("ItemId");
    	Intent in = new Intent(getSherlockActivity(), DetailActivity.class);
    	in.putExtra("idEstablecimiento", itemId);
    	startActivity(in);
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {
    	
        @Override
        protected SimpleAdapter doInBackground(String... url) {
        	JSONArray jArray = null;
        	
        	try {
	        	HttpClient httpclient = new DefaultHttpClient();
	            HttpGet httpget = new HttpGet(url[0]);
	            HttpResponse response = httpclient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            String data = EntityUtils.toString(entity);
	            jArray = new JSONArray(data);
        	}
        	catch (Exception ex)  {
        		Log.d("error mio", ex.getMessage());
        	}
            
			DescuentosJSONParser descuentosJP = new DescuentosJSONParser();
            List<Map<String, String>> descuentos = null;
 
            try{
            	descuentos = descuentosJP.getDescuentos(jArray);
            }catch(Exception e){
            	Log.d("error mio", e.getMessage());
            }
 
            String[] from = { "nombreComercial","categoria","ofertaCorta","distancia"};
            int[] to = { R.id.list_title,R.id.list_category, R.id.list_disscount, R.id.list_distance};
 
            SimpleAdapter adapter = new SimpleAdapter(getSherlockActivity(), descuentos , R.layout.descuentos_layout, from, to);
 
            return adapter;
        }
 
        @Override
        protected void onPostExecute(SimpleAdapter adapter) {
 
            /** Getting a reference to listview of main.xml layout file */
            ListView listView = getListView();
 
            /** Setting the adapter containing the country list to listview */
            listView.setAdapter(adapter);
        }
    }
}
