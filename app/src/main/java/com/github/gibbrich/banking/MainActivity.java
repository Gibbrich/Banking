package com.github.gibbrich.banking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.gibbrich.banking.model.AccountType;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
{
	@BindView (R.id.accountType)
	Spinner                   accountTypeSpinner;
	ArrayAdapter<AccountType> accountTypeAdapter;
	
	@BindView(R.id.cardLayout)
	LinearLayout cardLayout;
	
	@BindView(R.id.accountLayout)
	LinearLayout accountLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		
		accountTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AccountType.values());
		accountTypeSpinner.setAdapter(accountTypeAdapter);
		accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				switch (accountTypeAdapter.getItem(position))
				{
					case ACCOUNT:
						accountLayout.setVisibility(View.VISIBLE);
						cardLayout.setVisibility(View.GONE);
						break;
					case CARD:
						accountLayout.setVisibility(View.GONE);
						cardLayout.setVisibility(View.VISIBLE);
						break;
					default:
						throw new UnsupportedOperationException("Not implemented");
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		
		
	}
}
