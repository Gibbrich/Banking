package com.github.gibbrich.banking;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.gibbrich.banking.databinding.ActivityMainBinding;
import com.github.gibbrich.banking.model.Account;
import com.github.gibbrich.banking.model.AccountType;
import com.github.gibbrich.banking.model.Callback;
import com.github.gibbrich.banking.model.Card;
import com.github.gibbrich.banking.model.FieldViewModel;
import com.github.gibbrich.banking.model.FourDigitCardFormatWatcher;
import com.github.gibbrich.banking.model.PaymentType;
import com.github.gibbrich.banking.model.RegexCondition;
import com.github.gibbrich.banking.model.RegexPredicate;
import com.github.gibbrich.banking.model.RegexValidator;
import com.github.gibbrich.banking.model.SpinnerFieldViewModel;
import com.github.gibbrich.banking.model.SpinnerViewProperties;
import com.github.gibbrich.banking.model.TextFieldViewModel;
import com.github.gibbrich.banking.model.TextViewProperties;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity
{
	private static final String CARD_NUMBER = "CARD_NUMBER";
	private static final String ACCOUNT_MFO = "ACCOUNT_MFO";
	private static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	private static final String ACCOUNT_PAYMENT_TYPE = "ACCOUNT_PAYMENT_TYPE";
	private static final String ACCOUNT_LAST_NAME = "ACCOUNT_LAST_NAME";
	private static final String ACCOUNT_FIRST_NAME = "ACCOUNT_FIRST_NAME";
	private static final String ACCOUNT_SECOND_NAME = "ACCOUNT_SECOND_NAME";

	private ActivityMainBinding binding;

	private SpinnerFieldViewModel<AccountType> accountTypeField;
	private TextFieldViewModel cardField;
	private TextFieldViewModel mfoField;
	private TextFieldViewModel accountField;
	private SpinnerFieldViewModel<PaymentType> paymentTypeField;
	private TextFieldViewModel lastNameField;
	private TextFieldViewModel firstNameField;
	private TextFieldViewModel secondNameField;

	private CompositeDisposable compositeDisposable;

	private Card card;
	private Account account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		setViewModels();

		card = new Card();
		account = new Account();

		if (savedInstanceState != null)
		{
			card.setNumber(savedInstanceState.getString(CARD_NUMBER));

			account.setMfo(savedInstanceState.getString(ACCOUNT_MFO));
			account.setNumber(savedInstanceState.getString(ACCOUNT_NUMBER));
			String paymentTypeString = savedInstanceState.getString(ACCOUNT_PAYMENT_TYPE);
			if (paymentTypeString != null)
			{
				account.setPaymentType(PaymentType.valueOf(paymentTypeString));
			}
			account.setLastName(savedInstanceState.getString(ACCOUNT_LAST_NAME));
			account.setFirstName(savedInstanceState.getString(ACCOUNT_FIRST_NAME));
			account.setSecondName(savedInstanceState.getString(ACCOUNT_SECOND_NAME));

			binding.executePendingBindings();
		}

		compositeDisposable = new CompositeDisposable();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		addObservables();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		compositeDisposable.clear();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(CARD_NUMBER, card.getNumber());

		outState.putString(ACCOUNT_MFO, account.getMfo());
		outState.putString(ACCOUNT_NUMBER, account.getNumber());
		if (account.getPaymentType() != null)
		{
			outState.putString(ACCOUNT_PAYMENT_TYPE, account.getPaymentType().toString());
		}
		outState.putString(ACCOUNT_LAST_NAME, account.getLastName());
		outState.putString(ACCOUNT_FIRST_NAME, account.getFirstName());
		outState.putString(ACCOUNT_SECOND_NAME, account.getSecondName());
	}

	private void setViewModels()
	{
		RegexPredicate accountTypePredicate = new RegexPredicate("^2$|^5$");
		RegexValidator accountTypeValidator = new RegexValidator(accountTypePredicate, "Необходимо выбрать тип идентификатора");
		SpinnerViewProperties<AccountType> accountTypeViewProperties = new SpinnerViewProperties<>("Тип идентификатора", "Выберите тип идентификатора", AccountType.values());
		accountTypeField = new SpinnerFieldViewModel<>(accountTypeValidator, accountTypeViewProperties, binding.accountType, this, null);
		binding.setAccountTypeViewModel(accountTypeField);

		RegexCondition cardCondition = new RegexCondition(new RegexPredicate("^5$"));

		RegexPredicate cardPredicate = new RegexPredicate("^\\d{4} \\d{4} \\d{4} \\d{4,7}$");
		RegexValidator cardValidator = new RegexValidator(cardPredicate, "Неверный номер карты");
		TextViewProperties cardViewProperties = new TextViewProperties("Номер карты", "Введите номер карты", null);
		cardField = new TextFieldViewModel(cardValidator, cardViewProperties, binding.cardNumber, cardCondition);
		binding.setCardViewModel(cardField);

		binding.cardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());

		RegexCondition accountCondition = new RegexCondition(new RegexPredicate("^2$"));

		RegexPredicate mfoPredicate = new RegexPredicate("^\\d{9}$");
		RegexValidator mfoValidator = new RegexValidator(mfoPredicate, "Неверный БИК");
		TextViewProperties mfoViewProperties = new TextViewProperties("БИК", "Введите БИК", "numeric");
		mfoField = new TextFieldViewModel(mfoValidator, mfoViewProperties, binding.mfo, accountCondition);
		binding.setMfoViewModel(mfoField);

		RegexPredicate accountPredicate = new RegexPredicate("^\\d{20}$");
		RegexValidator accountValidator = new RegexValidator(accountPredicate, "Неверное значение");
		TextViewProperties accountViewProperties = new TextViewProperties("Номер счета", "Номер счета", "numeric");
		accountField = new TextFieldViewModel(accountValidator, accountViewProperties, binding.accountNumberEditText, accountCondition);
		binding.setAccountViewModel(accountField);

		RegexPredicate paymentTypePredicate = new RegexPredicate("^0$|^1$");
		RegexValidator paymentTypeValidator = new RegexValidator(paymentTypePredicate, "Необходимо выбрать тип платежа");
		SpinnerViewProperties<PaymentType> paymentTypeViewProperties = new SpinnerViewProperties<>("Тип платежа", "Выберите тип платежа", PaymentType.values());
		paymentTypeField = new SpinnerFieldViewModel<>(paymentTypeValidator, paymentTypeViewProperties, binding.paymentType, this, accountCondition);
		binding.setPaymentTypeViewModel(paymentTypeField);

		RegexPredicate namePredicate = new RegexPredicate("^[а-яА-Я\\-\\s]{2,40}$");
		RegexValidator nameValidator = new RegexValidator(namePredicate, "Неверное значение");

		TextViewProperties lastNameViewProperties = new TextViewProperties("Фамилия владельца счета", "Фамилия владельца счета", null);
		lastNameField = new TextFieldViewModel(nameValidator, lastNameViewProperties, binding.lastName, accountCondition);
		binding.setLastNameViewModel(lastNameField);

		TextViewProperties firstNameViewProperties = new TextViewProperties("Имя владельца счета", "Имя владельца счета", null);
		firstNameField = new TextFieldViewModel(nameValidator, firstNameViewProperties, binding.firstName, accountCondition);
		binding.setFirstNameViewModel(firstNameField);

		TextViewProperties secondNameViewProperties = new TextViewProperties("Отчество владельца счета", "Отчество владельца счета", null);
		secondNameField = new TextFieldViewModel(nameValidator, secondNameViewProperties, binding.secondName, accountCondition);
		binding.setSecondNameViewModel(secondNameField);
	}

	private void addObservables()
	{
		compositeDisposable.add(addInputChecker(binding.cardNumber, binding.cardNumberLayout, cardField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				card.setNumber(param);
			}
		}));
		compositeDisposable.add(addInputChecker(binding.mfo, binding.mfoTextInputLayout, mfoField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				account.setMfo(param);
			}
		}));
		compositeDisposable.add(addInputChecker(binding.accountNumberEditText, binding.accountNumberLayout, accountField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				account.setNumber(param);
			}
		}));
		compositeDisposable.add(addInputChecker(binding.lastName, binding.lastNameLayout, lastNameField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				account.setLastName(param);
			}
		}));
		compositeDisposable.add(addInputChecker(binding.firstName, binding.firstNameLayout, firstNameField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				account.setFirstName(param);
			}
		}));
		compositeDisposable.add(addInputChecker(binding.secondName, binding.secondNameLayout, secondNameField, new Callback<String>()
		{
			@Override
			public void run(String param)
			{
				account.setSecondName(param);
			}
		}));

		compositeDisposable.add(addAccountTypeSelectionChecker(accountTypeField.getView(), cardField, mfoField, accountField, paymentTypeField, lastNameField, firstNameField, secondNameField));
	}

	private Disposable addInputChecker(@NonNull final TextView view, @NonNull final TextInputLayout layout, @NonNull final TextFieldViewModel field, final Callback<String> callback)
	{
		return RxTextView.textChanges(view)
				.skip(1)
				.debounce(3, TimeUnit.SECONDS)
				.subscribeOn(AndroidSchedulers.mainThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<CharSequence>()
				{
					@Override
					public void accept(CharSequence charSequence) throws Exception
					{
						if (!field.getValidator().getPredicate().validate(charSequence.toString()))
						{
							layout.setError(field.getValidator().getMessage());
						}
						else
						{
							layout.setError(null);
							callback.run(charSequence.toString());
						}
					}
				});
	}

	private Disposable addAccountTypeSelectionChecker(@NonNull final Spinner spinner, @NonNull final FieldViewModel... fieldViewModels)
	{
		return RxAdapterView.itemSelections(spinner)
				.subscribeOn(AndroidSchedulers.mainThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Integer>()
				{
					@Override
					public void accept(Integer position) throws Exception
					{
						String value = String.valueOf(((AccountType) spinner.getSelectedItem()).getValue());
						for (FieldViewModel fieldViewModel : fieldViewModels)
						{
							RegexCondition condition = fieldViewModel.getCondition();
							if (condition != null)
							{
								boolean isVisible = condition.validate(value);
								fieldViewModel.isVisible().set(isVisible);
							}
							else
							{
								fieldViewModel.isVisible().set(true);
							}
						}
					}
				});
	}
}
