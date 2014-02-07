package com.netpace.itc.activities;

import com.example.incometaxcalculator.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PlanToSave extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_to_save);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan_to_save, menu);
		return true;
	}

}
