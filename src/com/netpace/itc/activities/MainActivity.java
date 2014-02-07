package com.netpace.itc.activities;

import com.example.incometaxcalculator.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button calcTaxButton = (Button) findViewById(R.id.calc_tax);
		calcTaxButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, CalculateTax.class);
				startActivity(intent);
			}
		});
		
		Button calcImpactButton = (Button) findViewById(R.id.calc_impact);
		calcImpactButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, CalculateImpact.class);
				startActivity(intent);
			}
		});
		
		Button howtosaveButton = (Button) findViewById(R.id.howtosave);
		howtosaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, HowToSave.class);
				startActivity(intent);
			}
		});
		
		Button plantosaveButton = (Button) findViewById(R.id.plantosave);
		plantosaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, PlanToSave.class);
				startActivity(intent);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
