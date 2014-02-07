package com.netpace.itc.activities;

import java.util.ArrayList;
import java.util.List;
import com.example.incometaxcalculator.R;
import com.netpace.itc.util.IncomeTax;
import com.netpace.itc.util.IncomeTaxSlab;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;

public class CalculateTax extends Activity {

	Spinner typeDropDown;
	EditText incomeEditText;
	TextView taxResultTextView;
	TextView taxResultSentenceTextView;
	Button calculateTaxButton;
	IncomeTax incomeTax;
	List<IncomeTaxSlab> incomeTaxSlabs;
	
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
				Long payableTax = 0L;
				Long income = Long.parseLong(incomeEditText.getText().toString());
				String text = "";
				// if monthly income then convert to yearly
				if(typeDropDown.getSelectedItemId() == 0) {
					income = income*12; // always send yearly income to calculate income tax
					payableTax = getIncomeTax(income)/12; // convert to monthly income tax
					
					text = getResources().getString(R.string.tax_result_monthly) + " ";
					taxResultSentenceTextView.setText(text.toString());
				} else {
					payableTax = getIncomeTax(income);
					
					text = getResources().getString(R.string.tax_result_yearly) + " ";
					taxResultSentenceTextView.setText(text.toString());
				}
				
				taxResultSentenceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
				taxResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
				taxResultTextView.setText(payableTax.toString() + " PKR");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate_tax, menu);
		return true;
	}
	
	private Long getIncomeTax(Long income) {
		incomeTaxSlabs = getSlabsFromDatabase();
		incomeTax = new IncomeTax(income, incomeTaxSlabs);
		return incomeTax.getPayableTax();
	}
	
	private List<IncomeTaxSlab> getSlabsFromDatabase() {
		incomeTaxSlabs = new ArrayList<IncomeTaxSlab>();
		
		// To be fetched from Database 
		incomeTaxSlabs.add(new IncomeTaxSlab(0L, 400000L, 0L, 0F));
		incomeTaxSlabs.add(new IncomeTaxSlab(400000L, 750000L, 0L, 5F));
		incomeTaxSlabs.add(new IncomeTaxSlab(750000L, 1500000L, 17500L, 10F));
		incomeTaxSlabs.add(new IncomeTaxSlab(1500000L, 2000000L, 95000L, 15F));
		incomeTaxSlabs.add(new IncomeTaxSlab(2000000L, 2500000L, 175000L, 17.5F));
		incomeTaxSlabs.add(new IncomeTaxSlab(2500000L, -1L, 420000L, 20F));
		
		return incomeTaxSlabs;
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

}
