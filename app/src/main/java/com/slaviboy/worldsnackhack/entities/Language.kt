package com.slaviboy.worldsnackhack.entities

sealed class Language(
    val localeCode: String,
    val name: String
) {
    object English : Language("en", "English")
    object Bulgarian : Language("bg", "Български")
    object Russian : Language("ru", "Ру̀ски")
    object French : Language("fr", "Français")
    object Spanish : Language("es", "Español")
    object Turkish : Language("tr", "Türkçe")
}