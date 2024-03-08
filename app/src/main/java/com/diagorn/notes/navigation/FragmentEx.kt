package com.diagorn.notes.navigation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import java.io.Serializable

const val NAVIGATION_DATA = "navigation data"

/**
 * Navigate to desired fragment
 *
 * @param actionId - id of navigation action
 * @param hostId - id of navigation host. Current host is taken by default
 * @param data - data passed to next fragment
 */
fun Fragment.navigate(actionId: Int, hostId: Int? = null, data: Serializable? = null) {
    val navController = if (hostId == null) {
        findNavController()
    } else {
        Navigation.findNavController(requireActivity(), hostId)
    }

    val bundle = Bundle().apply { putSerializable("navigation data", data) }

    navController.navigate(actionId, bundle)
}

inline fun <reified T: Serializable> Fragment.getNavigationData(): T? = this.run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getSerializable(NAVIGATION_DATA, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        arguments?.getSerializable(NAVIGATION_DATA) as? T
    }
}