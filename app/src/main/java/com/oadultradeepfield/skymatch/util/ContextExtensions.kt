package com.oadultradeepfield.skymatch.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/** Finds the Activity from a Context, traversing ContextWrappers if necessary. */
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
