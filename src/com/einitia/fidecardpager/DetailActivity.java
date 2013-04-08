package com.einitia.fidecardpager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.einitia.fidecardpager.R.id;

public class DetailActivity extends SherlockActivity {
	private ShareActionProvider mShareActionProvider;
	private String idText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.detail_layout);

		 String text = getIntent().getStringExtra("idEstablecimiento");
		 idText = text;
		 TextView tv = (TextView)findViewById(id.txtDetail);
		 tv.setText(text);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.share_items, menu);
 
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.share).getActionProvider();
 
        Intent intent = getDefaultShareIntent();
 
        if(intent!=null)
            mShareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
 
    }
 
    private Intent getDefaultShareIntent(){
 
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Descuento con Fidecard");
        intent.putExtra(Intent.EXTRA_TEXT,"Te paso el descuento que hay en el establecimiento " + idText);
        return intent;
    }
}
