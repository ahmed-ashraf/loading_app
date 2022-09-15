package com.udacity


sealed class ButtonState(var text: Int) {
    object Default : ButtonState(R.string.button_name)
    object Loading : ButtonState(R.string.button_loading)
    object Completed : ButtonState(R.string.button_name)
}