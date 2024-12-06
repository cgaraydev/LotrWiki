package com.example.lotrwiki.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.navigation.findNavController
import com.example.lotrwiki.NavGraphDirections
import com.example.lotrwiki.R
import com.example.lotrwiki.utils.LinkNavigator
import com.example.lotrwiki.utils.LinkValidator

class CustomLinkTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
//    private val navigator: LinkNavigator? = null
) : AppCompatTextView(context, attrs, defStyleAttr) {


    init {
        movementMethod = LinkMovementMethod.getInstance()
    }


    fun setHtmlText(htmlText: String?) {
        val spanned = HtmlCompat.fromHtml(htmlText.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        val spannable = spanned.toSpannable()

        val urlSpans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
        for (urlSpan in urlSpans) {
            val start = spannable.getSpanStart(urlSpan)
            val end = spannable.getSpanEnd(urlSpan)
            val flags = spannable.getSpanFlags(urlSpan)
            val isValidLink = validateLink(urlSpan.url)
//            val clickableSPan = CustomClickableSpan(urlSpan.url)
            val clickableSpan = CustomClickableSpan(urlSpan.url, isValidLink)
            spannable.setSpan(clickableSpan, start, end, flags)
            spannable.removeSpan(urlSpan)
        }

        text = spannable
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)


    }

    private fun validateLink(url: String): Boolean {
        return when {
            url.startsWith("location:") -> url.substringAfter("location:").isNotEmpty()
            url.startsWith("character:") -> url.substringAfter("character:").isNotEmpty()
            url.startsWith("other:") -> url.substringAfter("other:").isNotEmpty()
            url.startsWith("language:") -> url.substringAfter("language:").isNotEmpty()
            url.startsWith("event:") -> url.substringAfter("event:").isNotEmpty()
            url.startsWith("race:") -> url.substringAfter("race:").isNotEmpty()
            else -> false
        }
    }

    private inner class CustomClickableSpan(
        private val url: String,
        private val isValid: Boolean
    ) : ClickableSpan() {

        override fun onClick(widget: View) {
            when {
                url.startsWith("location:") -> {
                    val locationId = url.substringAfter("location:")
                    if (locationId.isNotEmpty()) navigateToLocation(locationId)
                    else navigateToEmpty()
                }

                url.startsWith("character:") -> {
                    val characterId = url.substringAfter("character:")
                    if (characterId.isNotEmpty()) navigateToCharacter(characterId)
                    else navigateToEmpty()
                }

                url.startsWith("other") -> {
                    val otherId = url.substringAfter("other:")
                    if (otherId.isNotEmpty()) navigateToOther(otherId)
                    else navigateToEmpty()
                }

                url.startsWith("language") -> {
                    val languageId = url.substringAfter("language:")
                    if (languageId.isNotEmpty()) navigateToLanguage(languageId)
                    else navigateToEmpty()
                }

                url.startsWith("event") -> {
                    val eventId = url.substringAfter("event:")
                    if (eventId.isNotEmpty()) navigateToEvent(eventId)
                    else navigateToEmpty()
                }

                url.startsWith("race") -> {
                    val raceId = url.substringAfter("race:")
                    if (raceId.isNotEmpty()) navigateToRace(raceId)
                    else navigateToEmpty()
                }

                else -> {
                    navigateToEmpty()
                }
            }
        }


        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = if (isValid) {
                ContextCompat.getColor(context, R.color.golden)
            } else {
                ContextCompat.getColor(context, R.color.invalid_link)
            }
            ds.isUnderlineText = false
        }
    }


    private fun navigateToCharacter(characterId: String) {
        val action = NavGraphDirections.actionGlobalCharacterDetailsFragment(characterId)
        findNavController().navigate(action)
    }

    private fun navigateToLocation(locationId: String) {
        val action = NavGraphDirections.actionGlobalLocationDetailsFragment(locationId)
        findNavController().navigate(action)
    }

    private fun navigateToOther(otherId: String) {
        val action = NavGraphDirections.actionGlobalOtherDetailsFragment(otherId)
        findNavController().navigate(action)
    }

    private fun navigateToLanguage(languageId: String) {
        val action = NavGraphDirections.actionGlobalLanguageDetailsFragment(languageId)
        findNavController().navigate(action)
    }

    private fun navigateToEvent(eventId: String) {
        val action = NavGraphDirections.actionGlobalEventDetailsFragment(eventId)
        findNavController().navigate(action)
    }

    private fun navigateToRace(raceId: String) {
        val action = NavGraphDirections.actionGlobalRaceDetailsFragment(raceId)
        findNavController().navigate(action)
    }

    private fun navigateToEmpty() {
        val action = NavGraphDirections.actionGlobalEmptyFragment()
        findNavController().navigate(action)
    }

}

//

///**
// * Configura el texto HTML con enlaces que puedan ser clicables.
// */
//fun setHtmlText(htmlText: String?) {
//    if (isInEditMode) {
//        // Evita procesar lógica compleja en el editor
//        text = htmlText
//        return
//    }
//    // Convierte el texto HTML en un objeto Spannable
//    val spanned = HtmlCompat.fromHtml(htmlText!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
//    val spannable = spanned.toSpannable()
//
//    // Detecta todos los URLSpan dentro del texto
//    val urlSpans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
//
//    // Itera sobre los spans y reemplaza cada URLSpan con un CustomClickableSpan
//    for (urlSpan in urlSpans) {
//        val start = spannable.getSpanStart(urlSpan)
//        val end = spannable.getSpanEnd(urlSpan)
//        val flags = spannable.getSpanFlags(urlSpan)
//
//        // Validación del enlace
//        val isValid = LinkValidator.isValidLink(urlSpan.url)
//
//        // Reemplaza el span original con un CustomClickableSpan
//        spannable.setSpan(CustomClickableSpan(urlSpan.url, isValid), start, end, flags)
//        spannable.removeSpan(urlSpan)
//    }
//
//    // Asigna el texto procesado al TextView
//    text = spannable
//}
//
///**
// * Clase interna para gestionar los clics en los enlaces.
// */
//private inner class CustomClickableSpan(
//    private val url: String,
//    private val isValid: Boolean
//) : ClickableSpan() {
//
//    override fun onClick(widget: View) {
//        if (!isValid) {
//            navigator?.navigateToEmpty()
//            return
//        }
//
//        // Extrae la parte del destino y el ID del enlace
//        val parts = url.split(":")
//        if (parts.size == 2) {
//            val destination = parts[0]
//            val id = parts[1]
//            navigator?.navigateToDestination(destination, id)
//        } else {
//            navigator?.navigateToEmpty()
//        }
//    }
//
//    override fun updateDrawState(ds: TextPaint) {
//        super.updateDrawState(ds)
//        ds.color = if (isValid) {
//            ContextCompat.getColor(context, R.color.golden)
//        } else {
//            ContextCompat.getColor(context, R.color.invalid_link)
//        }
//        ds.isUnderlineText = false
//    }
//}


//private inner class CustomClickableSpan(
//        private val url: String
//    ) : ClickableSpan() {
//        override fun onClick(widget: View) {
//            when {
//                url.startsWith("location:") -> {
//                    val locationId = url.substringAfter("location:")
//                    if (locationId.isNotEmpty()) navigateToLocation(locationId)
//                    else navigateToEmpty()
//                }
//
//                url.startsWith("character") -> {
//                    val characterId = url.substringAfter("character:")
//                    if (characterId.isNotEmpty()) navigateToCharacter(characterId)
//                    else navigateToEmpty()
//                }
//
//                url.startsWith("other") -> {
//                    val otherId = url.substringAfter("other:")
//                    if (otherId.isNotEmpty()) navigateToOther(otherId)
//                    else navigateToEmpty()
//                }
//
//                url.startsWith("language") -> {
//                    val languageId = url.substringAfter("language:")
//                    if (languageId.isNotEmpty()) navigateToLanguage(languageId)
//                    else navigateToEmpty()
//                }
//
//                url.startsWith("event") -> {
//                    val eventId = url.substringAfter("event:")
//                    if (eventId.isNotEmpty()) navigateToEvent(eventId)
//                    else navigateToEmpty()
//                }
//
//                url.startsWith("race") -> {
//                    val raceId = url.substringAfter("race:")
//                    if (raceId.isNotEmpty()) navigateToRace(raceId)
//                    else navigateToEmpty()
//                }
//
//                else -> {
//                    navigateToEmpty()
//                }
//
//            }
//        }
//
//        override fun updateDrawState(ds: TextPaint) {
//            super.updateDrawState(ds)
//            ds.color = resources.getColor(R.color.golden, null)
//            ds.isUnderlineText = false
////            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
////            val typeface = ResourcesCompat.getFont(context, R.font.opensans_regular)
////            ds.typeface = typeface
//        }
//    }




