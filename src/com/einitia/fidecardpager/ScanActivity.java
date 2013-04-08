package com.einitia.fidecardpager;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

public class ScanActivity extends SherlockActivity implements android.view.View.OnClickListener {
	private static final int ZBAR_QR_SCANNER_REQUEST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_layout);
		
		ActionBar menu = getSupportActionBar();
		menu.setDisplayHomeAsUpEnabled(true);
		menu.setHomeButtonEnabled(true);
		
		Button b = (Button) this.findViewById(R.id.qrscan_btn);
		b.setOnClickListener(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	       case android.R.id.home:
	            this.finish();
	            return true;
	   	   default:
	        	return super.onOptionsItemSelected(item);
	    }
	}
		
	@Override
	public void onClick(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
        } else {
        	new AlertDialog.Builder(this)
            .setTitle("Cámara")
            .setMessage("La cámara trasera no está disponible")
            .setCancelable(true)
            .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int which) { 
            		dialog.dismiss();
            	}
            })
            .show();
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = this.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                	/*Filtro URL QR scan if != http://fidecard.es --> ERROR Page*/
                	if(data.getStringExtra(ZBarConstants.SCAN_RESULT).startsWith("http://www.fidecard.es")){
                		
                		/*Cargar el ID del dispositivo por webservice*/
                		WebView webview = new WebView(this);
                   	 	setContentView(webview);
                   	 	webview.loadUrl(data.getStringExtra(ZBarConstants.SCAN_RESULT));
                	} else {
                		WebView webview = new WebView(this);
                   	 	setContentView(webview);
                   	 	webview.loadUrl("");
                	}
                	//Intent in = new Intent(this, DetailActivity.class);
                	//in.putExtra("idEstablecimiento", data.getStringExtra(ZBarConstants.SCAN_RESULT));
                	//startActivity(in);
                	
                } else if(resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Se ha cancelado o la cámara no está disponible", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
