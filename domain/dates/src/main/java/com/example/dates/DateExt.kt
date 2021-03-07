package com.example.dates

import java.text.SimpleDateFormat
import java.util.*

fun Date?.formatForUi(
    format: String = "MMMM dd, yyyy",
    default: String = "TBD"
) = this?.let { SimpleDateFormat(format).format(this) } ?: kotlin.run { default }
