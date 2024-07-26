package com.example.lotrwiki.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
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
//        applyScaleAnimation()
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

    }

    private inner class CustomClickableSpan(
        private val url: String
    ) : ClickableSpan() {
        override fun onClick(widget: View) {
            when {
//                url.startsWith("location:") -> {
//                    val locationId = url.substringAfter("location:")
//                    navigateToLocation(locationId)
//                }
                url.startsWith("character") -> {
                    val characterId = url.substringAfter("character:")
                    navigateToCharacter(characterId)
                }
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = resources.getColor(R.color.golden, null)
            ds.isUnderlineText = false
            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
    }

//    private fun applyScaleAnimation() {
//        val scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_click)
//        val scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_click_back)
//
//        setOnClickListener {
//            startAnimation(scaleUp)
//            postDelayed({
//                startAnimation(scaleDown)
//            }, scaleUp.duration)
//        }
//    }

    private fun navigateToCharacter(characterId: String) {
        val action = NavGraphDirections.actionGlobalCharacterDetailsFragment(characterId)
        findNavController().navigate(action)
    }

//    private fun navigateToLocation(locationId: String) {
//
//    }


}


