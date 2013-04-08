package com.einitia.fidecardpager;


import java.util.ArrayList;
import org.json.JSONArray;



public class Categories {
	
	public static ArrayList<ArrayList<String>> categories = new ArrayList<ArrayList<String>>();
	
	
	public JSONArray setCategories () {
		/*ArrayList de las categorias*/
		ArrayList<String> alimentacion = new ArrayList<String>();
		ArrayList<String> ocio = new ArrayList<String>();
		ArrayList<String> BaresRestaurantes = new ArrayList<String>();
		ArrayList<String> SaludBelleza = new ArrayList<String>();
		ArrayList<String> Deportes = new ArrayList<String>();
		ArrayList<String> Gremios = new ArrayList<String>();
		
		/*Set Subcategories*/
		alimentacion.add("Panaderia");
		ocio.add("Cinesa");
		BaresRestaurantes.add("Lizarran");
		SaludBelleza.add("Corporacion Dermoestetica");
		Deportes.add("Tenis");
		Gremios.add("Fontaneros");
		
		/*Añadimos las subcategorias a las categorias*/
		categories.add(alimentacion); 
		categories.add(ocio);
		categories.add(BaresRestaurantes);
		categories.add(SaludBelleza);
		categories.add(Deportes);
		categories.add(Gremios);
		
		
		/*Change to JSONArray*/
		JSONArray JSONCategories = new  JSONArray(categories);
		return JSONCategories;
	}
	
	

	
	

}
