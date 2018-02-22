package com.github.gibbrich.banking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gibbrich.banking.model.AccountType;
import com.github.gibbrich.banking.model.FourDigitCardFormatWatcher;
import com.github.gibbrich.banking.model.PaymentType;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity
{
	private static final char space = ' ';
	
	@BindView (R.id.accountType)
	Spinner accountTypeSpinner;
	ArrayAdapter<AccountType> accountTypeAdapter;
	
	@BindView (R.id.cardLayout)
	LinearLayout cardLayout;
	
	@BindView (R.id.cardNumber)
	EditText cardNumberEditText;
	
	@BindView (R.id.accountLayout)
	LinearLayout accountLayout;
	
	@BindView (R.id.mfo)
	EditText mfoEditText;
	
	@BindView (R.id.accountNumber)
	EditText accountNumberEditText;
	
	@BindView (R.id.paymentType)
	Spinner paymentTypeSpinner;
	ArrayAdapter<PaymentType> paymentTypeAdapter;
	
	@BindView (R.id.lastName)
	EditText lastNameEditText;
	
	@BindView (R.id.firstName)
	EditText firstNameEditText;
	
	@BindView (R.id.middleName)
	EditText secondNameEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		
		accountTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AccountType.values());
		accountTypeSpinner.setAdapter(accountTypeAdapter);
		
		RxAdapterView.itemSelections(accountTypeSpinner)
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Integer>()
				{
					@Override
					public void accept(Integer position) throws Exception
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
				});
		
		getTextChangeObservable(cardNumberEditText).subscribe(new Consumer<CharSequence>()
		{
			@Override
			public void accept(CharSequence input) throws Exception
			{
				validate(input, "^\\d{4} \\d{4} \\d{4} \\d{4,7}$", "Номер карты введён корректно", "Неверный номер карты");
			}
		});
		
		cardNumberEditText.addTextChangedListener(new FourDigitCardFormatWatcher());
		
		getTextChangeObservable(mfoEditText).subscribe(new Consumer<CharSequence>()
		{
			@Override
			public void accept(CharSequence charSequence) throws Exception
			{
				validate(charSequence, "^\\d{9}$", "Корректный БИК", "Неверный БИК");
			}
		});
		
		getTextChangeObservable(accountNumberEditText).subscribe(new Consumer<CharSequence>()
		{
			@Override
			public void accept(CharSequence charSequence) throws Exception
			{
				validate(charSequence, "^\\d{20}$", "Неверное значение", "Корректное значение счета");
			}
		});
		
		paymentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PaymentType.values());
		paymentTypeSpinner.setAdapter(paymentTypeAdapter);
		
		RxAdapterView.itemSelections(accountTypeSpinner)
				.skip(1)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Integer>()
				{
					@Override
					public void accept(Integer integer) throws Exception
					{
						switch (paymentTypeAdapter.getItem(integer))
						{
							// TODO: 22.02.2018 разобраться, что должно происходить
							case COMMON_PAYMENT:
								break;
							case URGENT_PAYMENT:
								break;
						}
					}
				});
		
		getTextChangeObservable(lastNameEditText)
				.subscribe(new Consumer<CharSequence>()
				{
					@Override
					public void accept(CharSequence charSequence) throws Exception
					{
						validate(charSequence, "^[а-яА-Я\\-\\s]{2,40}$", "Верное значение", "Неверное значение");
					}
				});
		
		getTextChangeObservable(firstNameEditText)
				.subscribe(new Consumer<CharSequence>()
				{
					@Override
					public void accept(CharSequence charSequence) throws Exception
					{
						validate(charSequence, "^[а-яА-Я\\-\\s]{2,40}$", "Верное значение", "Неверное значение");
					}
				});
		
		getTextChangeObservable(secondNameEditText)
				.subscribe(new Consumer<CharSequence>()
				{
					@Override
					public void accept(CharSequence charSequence) throws Exception
					{
						validate(charSequence, "^[а-яА-Я\\-\\s]{2,40}$", "Верное значение", "Неверное значение");
					}
				});
	}
	
	private void validate(CharSequence charSequence, String regex, String good, String bad)
	{
		String message = Pattern.compile(regex).matcher(charSequence).matches()
				? good
				: bad;
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private Observable<CharSequence> getTextChangeObservable(@NonNull TextView view)
	{
		return RxTextView.textChanges(view)
					   .skip(1)
				       .debounce(3, TimeUnit.SECONDS)
					   .observeOn(AndroidSchedulers.mainThread());
	}
}
