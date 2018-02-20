package com.github.gibbrich.banking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.gibbrich.banking.databinding.ActivityMainBinding;
import com.github.gibbrich.banking.model.AccountType;

public class MainActivity extends AppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.setViewModel(new ViewModel());
		binding.setSpinnerAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, AccountType.values()));
	}
}
