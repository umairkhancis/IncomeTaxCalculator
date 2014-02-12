package com.netpace.itc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.incometaxcalculator.R;
import com.netpace.itc.model.CalculateIncomeTax;
import com.netpace.itc.model.Slab;
import com.netpace.itc.service.CallService;

public class CalculateTaxActivity extends Activity {

	Spinner typeDropDown;
	EditText incomeEditText;
	TextView taxResultTextView;
	TextView taxResultSentenceTextView;
	Button calculateTaxButton;
	CalculateIncomeTax calculateIncomeTax;
	List<Slab> slabs;
	public static final String TAG = "CalculateTaxActivity"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_tax);

		initUIComponents(); // get UI elements refrences using findViewById
		populateTypeDropDown(); 

		typeDropDown.setOnItemSelectedListener(new OnItemSelectedListener(){
			String INCOME_HINT_MONTHLY = getResources().getString(R.string.income_hint_monthly);
			String INCOME_HINT_YEARLY = getResources().getString(R.string.income_hint_yearly);
			
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				switch (position) {
				case 0:
					incomeEditText.setHint(INCOME_HINT_MONTHLY);
					break;
				case 1:
					incomeEditText.setHint(INCOME_HINT_YEARLY);
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
			
		});
	
		calculateTaxButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Double payableTax = 0d;
				Double income = Double.parseDouble(incomeEditText.getText().toString());
				
				if( isMonthly() ) {
					payableTax = calculateMonthlyIncomeTax(income);
					displayTextView(taxResultSentenceTextView, getResources().getString(R.string.tax_result_monthly), TypedValue.COMPLEX_UNIT_PX, 60);
				} else {
					payableTax = calculateYearlyIncomeTax(income);
					displayTextView(taxResultSentenceTextView, getResources().getString(R.string.tax_result_yearly), TypedValue.COMPLEX_UNIT_PX, 60);
				}
				
				displayTextView(taxResultTextView, " " + payableTax.toString()+ " PKR", TypedValue.COMPLEX_UNIT_PX, 60);
			}
		});
	
	}
	
	private boolean isMonthly() {
		return (typeDropDown.getSelectedItemId() == 0) ? true: false;
	}
	
	private Double calculateMonthlyIncomeTax(Double income) {
		income = income*12; // always send yearly income to calculate income tax
		return (double) Math.round(getIncomeTax(income)/12); // convert to monthly income tax
	} 
	
	private Double calculateYearlyIncomeTax(Double income) {
		return (double) Math.round(getIncomeTax(income)); 
	} 
	
	private void displayTextView(TextView textView, String text, int unit_TypeValue, float size) {
		textView.setTextSize(unit_TypeValue, size);
		textView.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate_tax, menu);
		return true;
	}
	
	private Double getIncomeTax(Double income) {
		slabs = getSlabsFromDatabase();
		calculateIncomeTax = new CalculateIncomeTax(income, slabs);
		return calculateIncomeTax.getPayableTax();
	}
	
	private List<Slab> getSlabsFromDatabase() {
////	Hard Coded List
		slabs = getHardCodedSlabs();
		
		Intent serviceIntent = new Intent(this, CallService.class); 
		startService(serviceIntent);
		
		return slabs;
	}

	private void initUIComponents() {
		typeDropDown = (Spinner) findViewById(R.id.typeDropdown);
		incomeEditText = (EditText) findViewById(R.id.incomeEditText);
		taxResultSentenceTextView = (TextView) findViewById(R.id.taxResultSentenceTextView);
		taxResultTextView = (TextView) findViewById(R.id.taxResultTextView);
		calculateTaxButton = (Button) findViewById(R.id.calculateTaxButton);
	}
	
	private void populateTypeDropDown() {
		List<String> list = new ArrayList<String>();
		list.add("Monthly");
		list.add("Yearly");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeDropDown.setAdapter(dataAdapter);
	}

	private List<Slab> getHardCodedSlabs() {
		slabs = new ArrayList<Slab>();
		
		slabs.add(new Slab(0d, 400000d, 0d, 0f));
		slabs.add(new Slab(400000d, 750000d, 0d, 5f));
		slabs.add(new Slab(750000d, 1500000d, 17500d, 10f));
		slabs.add(new Slab(1500000d, 2000000d, 95000d, 15f));
		slabs.add(new Slab(2000000d, 2500000d, 175000d, 17.5f));
		slabs.add(new Slab(2500000d, -1d, 420000d, 20f));
		
		return slabs;
	}
}
