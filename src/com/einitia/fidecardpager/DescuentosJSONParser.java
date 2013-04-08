package com.einitia.fidecardpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DescuentosJSONParser {
	
	/** Receives a JSONObject and returns a list */
    public List<Map<String,String>> parse(JSONObject jObject){
 
        JSONArray jDescuentos = null;
        try {
            /** Retrieves all the elements in the 'countries' array */
        	jDescuentos = jObject.getJSONArray("countries");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getCountries with the array of json object
        * where each json object represent a country
        */
        return getDescuentos(jDescuentos);
    }
 
    public List<Map<String, String>> getDescuentos(JSONArray jDescuentos){
        int countryCount = jDescuentos.length();
        List<Map<String, String>> descuentoList = new ArrayList<Map<String,String>>();
        Map<String, String> descuento = null;
 
        /** Taking each country, parses and adds to list object */
        for(int i=0; i<countryCount;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
            	descuento = getDescuento((JSONObject)jDescuentos.get(i));
            	descuentoList.add(descuento);
            } catch (JSONException e) {
                //e.printStackTrace();
                Log.d("error mio", e.getMessage());
            }
        }
 
        return descuentoList;
    }

    private Map<String, String> getDescuento(JSONObject jDescuento){
 
        Map<String, String> descuento = new HashMap<String, String>();
        String idEstablecimiento = "";
        String idCategoria = "";
        //String idSubcategoria = "";
        String nombreComercial = "";
        String ofertaCorta = "";
        String categoria = "";
        String latitud = "";
        String longitud = "";
        String distancia = "";
 
        try {
            idEstablecimiento = jDescuento.getString("idEstablecimiento");
            idCategoria = jDescuento.getString("idCategoria");
            //idSubcategoria = jDescuento.getString("idSubcategoria");
            nombreComercial = jDescuento.getString("NombreComercial");
            ofertaCorta = jDescuento.getString("OfertaCorta");
            categoria = jDescuento.getString("Categoria");
            latitud = jDescuento.getString("Latitud");
            longitud = jDescuento.getString("Longitud");
            distancia = jDescuento.getString("distancia");
         
            //Poner la función de cálculo de metros/km
            
            descuento.put("idEstablecimiento", idEstablecimiento);
            descuento.put("idCategoria", idCategoria);
            //descuento.put("idSubcategoria", idSubcategoria);
            descuento.put("nombreComercial", nombreComercial);
            descuento.put("ofertaCorta", ofertaCorta);
            descuento.put("categoria", categoria);
            descuento.put("latitud", latitud);
            descuento.put("longitud", longitud);
            descuento.put("distancia", formatDistance(distancia));
            
 
        } catch (JSONException e) {
            Log.d("error mio", e.getMessage());
        }
        return descuento;
    }
    
    private String formatDistance(String data){
    	String result = "";
    	
    	Double dist = Double.valueOf(data);
    	
    	if (dist == 0){
    		
    	}
    	else if (dist < 1) {
    		result = data.substring(2, 5) + "m";
    	}
    	else {
    		result = data.substring(0, 4) + "km";
    	}
    	
    	return result;
    }
}