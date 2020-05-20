package jp.hoangvu.navigationexample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?) {
    recyclerView.adapter?.let { adapter ->
        if (adapter is BindableAdapter<*> && data != null) {
            (adapter as BindableAdapter<T>).setData(data)
        }
    }
}

@BindingAdapter("animatedVisibility")
fun setVisibility(view: View, visibility: Int) {
    when (visibility) {
        View.VISIBLE -> {
            view.slideAnimation(SlideDirection.LEFT, SlideType.SHOW)
        }
        else -> {
            view.slideAnimation(SlideDirection.RIGHT, SlideType.HIDE)
        }
    }
}

/*
@BindingAdapter("animatedVisibility")
fun setVisibility(view: View, visibility: Int) {
    // Were we animating before? If so, what was the visibility?
    val endAnimVisibility = view.getTag(R.id.finalVisibility) as Int?
    val oldVisibility = endAnimVisibility ?: view.visibility

    if (oldVisibility == visibility) {
        // Either we're already animating to the target visibility, or we're not animating and
        // are already at the target visibility. Don't start a new animation in either case.
        return
    }

    val isVisible = oldVisibility == View.VISIBLE
    val willBeVisible = visibility == View.VISIBLE

    view.visibility = View.VISIBLE
    val startAlpha = if (endAnimVisibility != null) {
        view.alpha
    } else {
        if (isVisible) 1f else 0f
    }
    val endAlpha = if (willBeVisible) 1f else 0f

    val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
    alpha.setAutoCancel(true)

    alpha.addListener(object : AnimatorListenerAdapter() {
        private var isCanceled: Boolean = false

        override fun onAnimationStart(anim: Animator) {
            view.setTag(R.id.finalVisibility, visibility)
        }

        override fun onAnimationCancel(anim: Animator) {
            isCanceled = true
        }

        override fun onAnimationEnd(anim: Animator) {
            view.setTag(R.id.finalVisibility, null)
            if (!isCanceled) {
                view.alpha = 1f
                view.visibility = visibility
            }
        }
    })
    alpha.start()
}*/
