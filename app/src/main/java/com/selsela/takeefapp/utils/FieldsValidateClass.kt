package com.selsela.jobsapp.utils

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isEmailValidPattern
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isPasswordValidLength
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isPasswordWeak
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isPasswordsMatches
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isPhoneNumberOnly
import com.selsela.takeefapp.utils.FieldsValidateConstraintsClass.isPhoneNumberValidLength
import com.selsela.takeefapp.R


fun EditText.validateEmail(mContext: Context,fieldName: String): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    } else if (!isEmailValidPattern(this.text.toString())) {
        message = mContext.getString(R.string.warning_email_pattern)
    }
    return message
}
fun String.validateEmail(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    } else if (!isEmailValidPattern(this)) {
        message = mContext.getString(R.string.warning_email_pattern)
    }
    return message
}

fun EditText.validateRequired(mContext: Context,fieldName: String): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    }
    return message
}
fun String.validateRequired(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    }
    return message
}

fun TextView.validateRequired(mContext: Context): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required)
    }
    return message
}

fun EditText.validateUserName(mContext: Context): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required)
    }
    return message
}

fun EditText.validatePassword(mContext: Context): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPasswordValidLength(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_length)
    }
    return message
}
fun EditText.validatePassword(mContext: Context,fieldName:String): String {
    var message = ""
    if (this.text.toString() == "") {
        message =mContext.getString(R.string.valid_required2) +" " + fieldName
    } else if (!isPasswordValidLength(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_length)
    }
    return message
}

fun EditText.validatePasswordSignup(mContext: Context): String {
    var message = ""
    if (this.text.toString() == "") {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPasswordValidLength(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_length)
    } else if (isPasswordWeak(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_weak)
    }
    return message
}
fun EditText.validatePasswordSignup(mContext: Context,fieldName:String): String {
    var message = ""
    if (this.text.toString() == "") {
        message =mContext.getString(R.string.valid_required2) +" " + fieldName
    } else if (!isPasswordValidLength(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_length)
    } else if (isPasswordWeak(this.text.toString())) {
        message = mContext.getString(R.string.warning_password_weak)
    }
    return message
}

fun EditText.validRePassword(mContext: Context, password1: String?, password2: String): String {
    var message = ""
    if (password2 == "") {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPasswordsMatches(password1!!, password2)) {
        message = mContext.getString(R.string.warning_password_not_match)
    }
    return message
}
fun EditText.validRePassword(mContext: Context, password1: String?, password2: String,fieldName:String): String {
    var message = ""
    if (password2 == "") {
        message =mContext.getString(R.string.valid_required2) +" " + fieldName
    } else if (!isPasswordsMatches(password1!!, password2)) {
        message = mContext.getString(R.string.warning_password_not_match)
    }
    return message
}

fun String.validatePhone(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    } else if (!isPhoneNumberOnly(this)) {
        message = mContext.getString(R.string.warning_phone_pattern)
    } else if (!isPhoneNumberValidLength(this)) {
        message = mContext.getString(R.string.warning_phone_length_pattern)
    } else if (this.startsWith("0", ignoreCase = true)) {
        message = mContext.getString(R.string.not_start_with_zero)
    }
    return message
}

fun String?.validatePhone(mContext: Context): String {
    var message = ""
    if (this == "" || this == null) {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPhoneNumberOnly(this)) {
        message = mContext.getString(R.string.warning_phone_pattern)
    } else if (!isPhoneNumberValidLength(this)) {
        message = mContext.getString(R.string.warning_phone_length_pattern)
    }
    return message
}
//
//
//fun String?.validatePhoneWithDigits(mContext: Context, country: CountriesItem): String {
//    var message = ""
//    if (this == "" || this == null) {
//        message = mContext.getString(R.string.valid_required)
//    } else if (!isPhoneNumberOnly(this)) {
//        message = mContext.getString(R.string.warning_phone_pattern)
//    } else if (this.length != country.mobileDigits) {
//        message = String.format(mContext.getString(R.string.warning_phone_length_pattern_2),
//            country.mobileDigits,
//            country.name)
//
//    }
//    return message
//}
//
//
//fun String?.validatePhoneWithDigits(mContext: Context, country: CountriesItem,fieldName:String): String {
//    var message = ""
//    if (this == "" || this == null) {
//        message =mContext.getString(R.string.valid_required2) +" " + fieldName
//    } else if (!isPhoneNumberOnly(this)) {
//        message = mContext.getString(R.string.warning_phone_pattern)
//    } else if (this.length != country.mobileDigits) {
//        message = String.format(mContext.getString(R.string.warning_phone_length_pattern_2),
//            country.mobileDigits,
//            country.name)
//
//    }
//    return message
//}
//
