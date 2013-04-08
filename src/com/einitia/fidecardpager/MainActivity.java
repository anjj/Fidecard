package com.einitia.fidecardpager;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends SherlockFragmentActivity {
	private static final String[] CONTENT = new String[] { "Descuentos", "Ofertas"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);
		
		ActionBar menu = getSupportActionBar();
		menu.setDisplayHomeAsUpEnabled(true);
		menu.setHomeButtonEnabled(true);
		
		FragmentPagerAdapter adapter = new PaginationAdapter(getSupportFragmentManager());
		ViewPager pager = (ViewPager)findViewById(R.id.pager);
	    pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	       MenuInflater inflater = getSupportMenuInflater();
	       inflater.inflate(R.menu.list_menu, menu);
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
	       case R.id.viewMap:
	    	    Intent intentMap = new Intent(this, MapActivity.class);
	    	    //TODO Revisar, tiene que volver a la lista de categorías
	    	    intentMap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	    startActivity(intentMap);
	    	    overridePendingTransition (R.anim.list_leave, R.anim.map_enter);
	    	    return true;
	       case R.id.scanQR:
	    	   Intent intentScan = new Intent(this, ScanActivity.class);
	    	   startActivityForResult(intentScan, 1);
	    	   return true;
	   	   default:
	        	return super.onOptionsItemSelected(item);
	    }
	}   

	
	class PaginationAdapter extends FragmentPagerAdapter {
        public PaginationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	Fragment tab = null;
        	
        	switch(position) {
        		case 0: tab = new DescuentosFragment();
        			break;
        		case 1: tab = new DescuentosFragment();
    				break;
        	}
        	return tab;
        	//return DescuentosFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase(Locale.getDefault());
        }

        @Override
        public int getCount() {
          return CONTENT.length;
        }
    }

	
   
}
