package com.erebor.tomkins.pos.data.ui

data class SaleHistoryUiModel(val id: String, val date: String, val name: String, val qty: Int, val size: String, val isSuccess: Boolean, val errorMessage: String, val price: Double) {
    constructor(id: String, date: String, name: String, qty: Int, size: String, price: Double) : this(id, date, name, qty, size, true, "", price)
    constructor(id: String, date: String, errorMessage: String) : this(id, date, "", 0, "", false, errorMessage, 0.0)
}
