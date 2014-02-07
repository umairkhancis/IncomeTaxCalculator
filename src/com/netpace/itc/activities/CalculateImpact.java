package com.netpace.itc.activities;

import java.util.ArrayList;
import java.util.List;
import com.example.incometaxcalculator.R;
import com.netpace.itc.util.IncomeTax;
import com.netpace.itc.util.IncomeTaxSlab;
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

public class CalculateImpact extends Activity {

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
	IncomeTax incomeTax;
	List<IncomeTaxSlab> incomeTaxSlabs;

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
				Long payableTax = 0L;
				Long income = Long.parseLong(incomeEditText.getText()
						.toString());
				Long takeHomeSalary = 0L;

				// if monthly income then convert to yearly
				if (typeDropDown.getSelectedItemId() == 0) {

					income = income * 12; // always send yearly income to
											// calculate income tax
					payableTax = getIncomeTax(income) / 12; // convert to
															// monthly income
															// tax
					income = income / 12; // back to monthly income b/c we have
											// to display current take home
											// salary monthly
					takeHomeSalary = income - payableTax;

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

				currentTaxResultSentenceTextView.setTextSize(
						TypedValue.COMPLEX_UNIT_PX, 60);
				currentTaxResultTextView.setTextSize(
						TypedValue.COMPLEX_UNIT_PX, 60);
				currentTaxResultTextView.setText(payableTax.toString() + " PKR");

				currentTakeHomeSalarySentenceTextView.setTextSize(
						TypedValue.COMPLEX_UNIT_PX, 60);
				currentTakeHomeSalaryTextView.setTextSize(
						TypedValue.COMPLEX_UNIT_PX, 60);
				currentTakeHomeSalaryTextView.setText(takeHomeSalary.toString()
						+ " PKR");
			}
		});

	}

	private Long getIncomeTax(Long income) {
		incomeTaxSlabs = getSlabsFromDatabase();
		incomeTax = new IncomeTax(income, incomeTaxSlabs);
		return incomeTax.getPayableTax();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate_impact, menu);
		return true;
	}

	private List<IncomeTaxSlab> getSlabsFromDatabase() {
		incomeTaxSlabs = new ArrayList<IncomeTaxSlab>();

		// To be fetched from Database
		incomeTaxSlabs.add(new IncomeTaxSlab(0L, 400000L, 0L, 0F));
		incomeTaxSlabs.add(new IncomeTaxSlab(400000L, 750000L, 0L, 5F));
		incomeTaxSlabs.add(new IncomeTaxSlab(750000L, 1500000L, 17500L, 10F));
		incomeTaxSlabs.add(new IncomeTaxSlab(1500000L, 2000000L, 95000L, 15F));
		incomeTaxSlabs
				.add(new IncomeTaxSlab(2000000L, 2500000L, 175000L, 17.5F));
		incomeTaxSlabs.add(new IncomeTaxSlab(2500000L, -1L, 420000L, 20F));

		return incomeTaxSlabs;
	}

	private void initUIComponents() {
		incomeEditText = (EditText) findViewById(R.id.incomeEditText);
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
