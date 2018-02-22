package com.github.gibbrich.banking.model

/**
 * Created by Dvurechenskiyi on 22.02.2018.
 */
enum class PaymentType(val value: Int, val title: String)
{
    COMMON_PAYMENT(0, "Обычный платеж"),
    URGENT_PAYMENT(1, "Срочный платеж");
}