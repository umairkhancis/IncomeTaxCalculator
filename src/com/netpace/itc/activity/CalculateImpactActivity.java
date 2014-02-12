package com.netpace.itc.activity;

import java.util.ArrayList;
import java.util.List;
import com.example.incometaxcalculator.R;
import com.netpace.itc.model.CalculateIncomeTax;
import com.netpace.itc.model.Slab;

import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculateImpactActivity extends Activity {

	Spinner typeDropDown;
	EditText incomeEditText;
	EditText incrementEditText;

	TextView currentTaxResultSentenceTextView;
	TextView currentTaxResultTextView;
	TextView currentTakeHomeSalarySentenceTextView;
	TextView currentTakeHomeSalaryTextView;

	TextView newTaxResultSentenceTextView;
	TextView newTaxResultTextView;
	TextView newTakeHomeSalarySentenceTextView;
	TextView newTakeHomeSalaryTextView;

	Button calculateImpactButton;
	CalculateIncomeTax incomeTax;
	List<Slab> incomeTaxSlabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate_impact);

		initUIComponents(); // get UI elements refrences using findViewById
		populateTypeDropDown();

		typeDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				String INCOME_HINT_MONTHLY = getResources().getString(
						R.string.income_hint_monthly);
				String INCOME_HINT_YEARLY = getResources().getString(
						R.string.income_hint_yearly);
				String INCREMENT_HINT_MONTHLY = getResources().getString(
						R.string.increment_hint_monthly);
				String INCREMENT_HINT_YEARLY = getResources().getString(
						R.string.increment_hint_yearly);

				switch (position) {
				case 0:
					incomeEditText.setHint(INCOME_HINT_MONTHLY);
					incrementEditText.setHint(INCREMENT_HINT_MONTHLY);
					break;
				case 1:
					incomeEditText.setHint(INCOME_HINT_YEARLY);
					incrementEditText.setHint(INCREMENT_HINT_YEARLY);
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}

		});

		calculateImpactButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double payableTax = 0d;
				Double newPayableTax = 0d;
				Double income = Double.parseDouble(incomeEditText.getText()
						.toString());
				Double increment = Double.parseDouble(incrementEditText.getText()
						.toString());
				Double takeHomeSalary = 0d;
				Double newTakeHomeSalary = 0d;

				// if monthly income then convert to yearly
				if (typeDropDown.getSelectedItemId() == 0) {

					income = income * 12; // always send yearly income to calculate income tax
					payableTax = getIncomeTax(income) / 12; // convert to monthly income tax
					income = income / 12; // back to monthly income b/c we have to display current take home salary monthly
					takeHomeSalary = income - payableTax;
					
					Double newincome = income+increment;
					newincome = newincome*12; // convert to yearly
					newPayableTax = getIncomeTax(newincome)/12; // getting monthly income tax
					newincome = newincome / 12; // back to monthly income b/c we have to display new take home salary monthly
					newTakeHomeSalary = newincome - newPayableTax;

					currentTaxResultSentenceTextView.setText(getResources()
							.getString(R.string.current_tax_result_monthly)
							+ " ");
					currentTakeHomeSalarySentenceTextView
							.setText(getResources().getString(
									R.string.current_salary_result_monthly)
									+ " ");

					newTaxResultSentenceTextView.setText(getResources()
							.getString(R.string.new_tax_result_monthly) + " ");
					newTakeHomeSalarySentenceTextView.setText(getResources()
							.getString(R.string.new_salary_result_monthly)
							+ " ");

				} else {

					payableTax = getIncomeTax(income);
					takeHomeSalary = income - payableTax;

					currentTaxResultSentenceTextView.setText(getResources()
							.getString(R.string.current_tax_result_yearly)
							+ " ");
					currentTakeHomeSalarySentenceTextView
							.setText(getResources().getString(
									R.string.current_salary_result_yearly)
									+ " ");

					newTaxResultSentenceTextView.setText(getResources()
							.getString(R.string.new_tax_result_yearly) + " ");
					newTakeHomeSalarySentenceTextView
							.setText(getResources().getString(
									R.string.new_salary_result_yearly)
									+ " ");
				}

				currentTaxResultSentenceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				currentTaxResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				currentTaxResultTextView.setText(payableTax.toString() + " PKR");

				currentTakeHomeSalarySentenceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				currentTakeHomeSalaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				currentTakeHomeSalaryTextView.setText(takeHomeSalary.toString()+ " PKR");
				
				newTaxResultSentenceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				newTaxResultTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				newTaxResultTextView.setText(newPayableTax.toString() + " PKR");

				newTakeHomeSalarySentenceTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				newTakeHomeSalaryTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
				newTakeHomeSalaryTextView.setText(newTakeHomeSalary.toString()+ " PKR");
			}
		});

	}

	private Double getIncomeTax(Double income) {
		incomeTaxSlabs = getSlabsFromDatabase();
		incomeTax = new CalculateIncomeTax(income, incomeTaxSlabs);
		return incomeTax.getPayableTax();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate_impact, menu);
		return true;
	}

	private List<Slab> getSlabsFromDatabase() {
		incomeTaxSlabs = new ArrayList<Slab>();

		// To be fetched from Database
		incomeTaxSlabs.add(new Slab(0d, 400000d, 0d, 0f));
		incomeTaxSlabs.add(new Slab(400000d, 750000d, 0d, 5f));
		incomeTaxSlabs.add(new Slab(750000d, 1500000d, 17500d, 10f));
		incomeTaxSlabs.add(new Slab(1500000d, 2000000d, 95000d, 15f));
		incomeTaxSlabs.add(new Slab(2000000d, 2500000d, 175000d, 17.5f));
		incomeTaxSlabs.add(new Slab(2500000d, -1d, 420000d, 20f));

		return incomeTaxSlabs;
	}

	private void initUIComponents() {
		incomeEditText = (EditText) findViewById(R.id.incomeEditText);
		incrementEditText = (EditText) findViewById(R.id.incrementEditText);
		typeDropDown = (Spinner) findViewById(R.id.typeDropdown);
		calculateImpactButton = (Button) findViewById(R.id.calculateImpactButton);

		currentTaxResultSentenceTextView = (TextView) findViewById(R.id.currentTaxResultSentenceTextView);
		currentTaxResultTextView = (TextView) findViewById(R.id.currentTaxResultTextView);
		currentTakeHomeSalarySentenceTextView = (TextView) findViewById(R.id.currentTakeHomeSalarySentenceTextView);
		currentTakeHomeSalaryTextView = (TextView) findViewById(R.id.currentTakeHomeSalaryTextView);

		newTaxResultSentenceTextView = (TextView) findViewById(R.id.newTaxResultSentenceTextView);
		newTaxResultTextView = (TextView) findViewById(R.id.newTaxResultTextView);
		newTakeHomeSalarySentenceTextView = (TextView) findViewById(R.id.newTakeHomeSalarySentenceTextView);
		newTakeHomeSalaryTextView = (TextView) findViewById(R.id.newTakeHomeSalaryTextView);
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
