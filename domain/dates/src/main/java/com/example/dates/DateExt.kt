package com.example.dates

import java.text.SimpleDateFormat
import java.util.Date

fun Date?.formatForUi(
    format: String = "MMMM dd, yyyy",
    default: String = "TBD"
) = this?.let { SimpleDateFormat(format).format(this) } ?: kotlin.run { default }
