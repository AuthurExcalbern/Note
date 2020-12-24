
package com.example.mynewsapp.models.states

import androidx.annotation.StringRes

/**
 * Represents error states in a given view state
 */
internal data class Error(@StringRes val message: Int)