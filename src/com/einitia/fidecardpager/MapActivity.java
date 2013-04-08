package com.einitia.fidecardpager;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

public class MapActivity extends SherlockFragmentActivity {
	private GoogleMap map;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_layout);
	    
	    ActionBar menu = getSupportActionBar();
		menu.setDisplayHomeAsUpEnabled(true);
		menu.setHomeButtonEnabled(true);
	    
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
		    public void onInfoWindowClick(Marker marker){
		    	Intent intentDetail = new Intent(MapActivity.this,DetailActivity.class);
		        startActivityForResult(intentDetail, 0);
		    }
		});
		
		setCurrentLocation(map);
	  }
	  
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  super.onCreateOptionsMenu(menu);
		  	MenuInflater inflater = getSupportMenuInflater();
		    inflater.inflate(R.menu.map_menu, menu);
		    
		    SubMenu subMenu1 = menu.getItem(0).getSubMenu();
	        subMenu1.add("Sample");
	        subMenu1.add("Menu");
	        subMenu1.add("Items");

		   return super.onCreateOptionsMenu(menu);
	  }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
		  	case android.R.id.home:
	            Intent intentHome = new Intent(this, InitialActivity.class);
	            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intentHome);
	            return true;
		  	case R.id.menucategory:
	  			Intent intentList = new Intent(this, MainActivity.class);
	  			intentList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    	// onBackPressed(); /*Ir para atras*/
		    	overridePendingTransition (R.anim.map_leave, R.anim.list_enter);
		    	return true;
		  	case R.id.scanQR:
		        Intent intentScan = new Intent(this, ScanActivity.class);
		        startActivityForResult(intentScan, 1);
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		  }
	  }   

	  private void setCurrentLocation(GoogleMap map) {
		  GPSTracker gps = new GPSTracker(this);

		  if(gps.canGetLocation()){
			
			  double latitude = gps.getLatitude();
			  double longitude = gps.getLongitude();
			    
			  LatLng current = new LatLng(latitude, longitude);
			    
	  	      map.addMarker(new MarkerOptions()
	  	      	.position(current)
			    .title("Posicion")
			    .snippet("Estas aqui")
			    .icon(BitmapDescriptorFactory
			        .fromResource(R.drawable.marker)));
			    
			  map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));
			
			  map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
			
		  }else{
		      gps.showSettingsAlert();
		  }
	  }
}
