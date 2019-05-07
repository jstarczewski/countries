package com.clakestudio.pc.countries.vo

class ViewObject<out T>(
    val isLoading: Boolean,
    val isHasError: Boolean,
    val data: T?,
    val errorMessage: String?,
    val isUpToDate: Boolean?
) {
    companion object {
        fun <T> success(data: T, isUpToDate: Boolean): ViewObject<T> = ViewObject(false, false, data, null, isUpToDate)
        fun <T> loading(data: T?): ViewObject<T> = ViewObject(true, false, data, null, null)
        fun <T> error(errorMessage: String, data: T?): ViewObject<T> = ViewObject(false, true, data, errorMessage, null)
    }
}