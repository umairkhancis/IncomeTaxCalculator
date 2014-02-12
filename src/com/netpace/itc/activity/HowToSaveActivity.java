package com.netpace.itc.activity;

import com.example.incometaxcalculator.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HowToSaveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_save);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.how_to_save, menu);
		return true;
	}

}
