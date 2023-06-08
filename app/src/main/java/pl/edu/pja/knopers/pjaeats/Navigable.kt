package pl.edu.pja.knopers.pjaeats

import androidx.fragment.app.Fragment

interface Navigable {
    fun navigate(destination: Destination)
}

fun Fragment.navigate(destination: Destination) {
    (activity as? Navigable)?.navigate(destination)
}