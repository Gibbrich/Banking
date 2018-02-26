package com.github.gibbrich.banking.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.gibbrich.banking.R;
import com.github.gibbrich.banking.databinding.ActivityMainBinding;
import com.github.gibbrich.banking.viewModel.ViewModel;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements MainActivityView
{
	private ActivityMainBinding binding;
	private ViewModel viewModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		
		viewModel = new ViewModel(this);
		binding.setViewModel(viewModel);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		viewModel.onResume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		viewModel.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		viewModel.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		viewModel.onRestoreInstanceState(savedInstanceState);
		
		binding.executePendingBindings();
	}
	
	@NotNull
	@Override
	public Spinner getAccountType()
	{
		return binding.accountType;
	}
	
	@NotNull
	@Override
	public Spinner getPaymentType()
	{
		return binding.paymentType;
	}
	
	@NotNull
	@Override
	public TextInputLayout getCardNumberTextInputLayout()
	{
		return binding.cardNumberLayout;
	}
	
	@NotNull
	@Override
	public TextView getCardNumberTextView()
	{
		return binding.cardNumber;
	}
	
	@NotNull
	@Override
	public TextInputLayout getMfoTextInputLayout()
	{
		return binding.mfoTextInputLayout;
	}
	
	@NotNull
	@Override
	public TextView getMfoTextView()
	{
		return binding.mfoEditText;
	}
	
	@NotNull
	@Override
	public TextInputLayout getAccountNumberTextInputLayout()
	{
		return binding.accountNumberLayout;
	}
	
	@NotNull
	@Override
	public TextView getAccountNumberTextView()
	{
		return binding.accountNumberEditText;
	}
	
	@NotNull
	@Override
	public TextInputLayout getLastNameTextInputLayout()
	{
		return binding.lastNameLayout;
	}
	
	@NotNull
	@Override
	public TextView getLastNameTextView()
	{
		return binding.lastName;
	}
	
	@NotNull
	@Override
	public TextInputLayout getFirstNameTextInputLayout()
	{
		return binding.firstNameLayout;
	}
	
	@NotNull
	@Override
	public TextView getFirstNameTextView()
	{
		return binding.firstName;
	}
	
	@NotNull
	@Override
	public TextInputLayout getSecondNameTextInputLayout()
	{
		return binding.secondNameLayout;
	}
	
	@NotNull
	@Override
	public TextView getSecondNameTextView()
	{
		return binding.secondName;
	}
	
	@NotNull
	@Override
	public Context getContext()
	{
		return this;
	}
}
