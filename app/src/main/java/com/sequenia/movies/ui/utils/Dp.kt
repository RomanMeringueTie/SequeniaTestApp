package com.sequenia.movies.ui.utils

import android.content.res.Resources

fun Int.dp(resources: Resources): Int = (this * resources.displayMetrics.density).toInt()