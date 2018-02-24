package com.github.gibbrich.banking.model

/**
 * Created by Артур on 24.02.2018.
 */
class Card(var number: String? = null)

class Account(
        var mfo: String? = null,
        var number: String? = null,
        var paymentType: PaymentType? = null,
        var lastName: String? = null,
        var firstName: String? = null,
        var secondName: String? = null
)