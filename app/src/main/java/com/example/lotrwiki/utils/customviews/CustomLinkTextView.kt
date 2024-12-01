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
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.navigation.findNavController
import com.example.lotrwiki.NavGraphDirections
import com.example.lotrwiki.R

class CustomLinkTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
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
            val clickableSPan = CustomClickableSpan(urlSpan.url)
            spannable.setSpan(clickableSPan, start, end, flags)
            spannable.removeSpan(urlSpan)
        }

        text = spannable
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)


    }

    private inner class CustomClickableSpan(
        private val url: String
    ) : ClickableSpan() {
        override fun onClick(widget: View) {
            when {
                url.startsWith("location:") -> {
                    val locationId = url.substringAfter("location:")
                    navigateToLocation(locationId)
                }

                url.startsWith("character") -> {
                    val characterId = url.substringAfter("character:")
                    navigateToCharacter(characterId)
                }

                url.startsWith("other") -> {
                    val otherId = url.substringAfter("other:")
                    navigateToOther(otherId)
                }

                url.startsWith("language") -> {
                    val languageId = url.substringAfter("language:")
                    navigateToLanguage(languageId)
                }

                url.startsWith("event") -> {
                    val eventId = url.substringAfter("event:")
                    navigateToEvent(eventId)
                }
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = resources.getColor(R.color.golden, null)
            ds.isUnderlineText = false
//            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
//            val typeface = ResourcesCompat.getFont(context, R.font.opensans_regular)
//            ds.typeface = typeface
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

}


