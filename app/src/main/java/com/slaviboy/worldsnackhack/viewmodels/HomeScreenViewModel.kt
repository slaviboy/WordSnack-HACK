package com.slaviboy.worldsnackhack.viewmodels

import android.content.Context
import android.content.res.AssetManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaviboy.worldsnackhack.entities.Language
import com.slaviboy.worldsnackhack.entities.Language.Bulgarian
import com.slaviboy.worldsnackhack.entities.Language.English
import com.slaviboy.worldsnackhack.entities.Language.French
import com.slaviboy.worldsnackhack.entities.Language.Russian
import com.slaviboy.worldsnackhack.entities.Language.Spanish
import com.slaviboy.worldsnackhack.entities.Language.Turkish
import com.slaviboy.worldsnackhack.extensions.findActivity
import com.slaviboy.worldsnackhack.extensions.hasSameChars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val assetManager: AssetManager
) : ViewModel() {

    val languages by mutableStateOf(listOf(English, Bulgarian, Russian, French, Spanish, Turkish))
    var expanded by mutableStateOf(false)
    var enteredText by mutableStateOf("")
    var wordsArray by mutableStateOf(listOf<CharArray>())
    var answerWords by mutableStateOf(listOf<String>())
    var rangeSliderMinMax by mutableStateOf(0f..20f)
    var rangeSliderLabel by mutableStateOf<Int?>(null)

    val rangeSliderMin: Int
        get() = rangeSliderMinMax.start.roundToInt()

    val rangeSliderMax: Int
        get() = rangeSliderMinMax.endInclusive.roundToInt()

    var selectedItem by mutableStateOf(languages[0].also {
        changeLanguage(it)
    })

    fun generateWords() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val minNumberOfChars = rangeSliderMinMax.start.roundToInt()
            val maxNumberOfChars = rangeSliderMinMax.endInclusive.roundToInt()
            val matchesList = mutableListOf<String>()
            for (i in wordsArray.indices) {
                val hasSameChars = wordsArray[i].hasSameChars(enteredText.toCharArray())
                if (hasSameChars) {
                    matchesList.add(String(wordsArray[i]))
                }
            }
            answerWords = matchesList
                .filter {
                    it.isNotEmpty() &&
                            it.length >= minNumberOfChars &&
                            it.length <= maxNumberOfChars
                }
                .mapIndexed { i, s -> "$i) $s" }
        }
    }

    fun clearAll(language: Language) {
        selectedItem = language
        expanded = false
        answerWords = listOf()
        enteredText = ""
    }

    fun updateLanguage(context: Context, localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        context.findActivity()?.recreate()
    }

    private fun changeLanguage(language: Language) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try {
                val inputStream = assetManager.open("${language.localeCode}.txt")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                withContext(Dispatchers.Main) {
                    wordsArray = String(buffer).split(" ").map {
                        it.toCharArray()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}