package jp.hoangvu.navigationexample

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
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
            //view.slideAnimation(SlideDirection.LEFT, SlideType.SHOW)
            view.visibility = View.VISIBLE
            view.startAnimation(
                AnimationUtils.loadAnimation(view.context, R.anim.slide_right_to_left_in)
            )
        }
        else -> {
            //view.slideAnimation(SlideDirection.RIGHT, SlideType.HIDE)
            Log.d("Animation", "Go")
            val animation =
                AnimationUtils.loadAnimation(view.context, R.anim.slide_left_to_right_out)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animation?) {
                    Log.d("Animation", "Finish")
                    view.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {

                }

            })
            view.startAnimation(animation)
            //view.visibility = View.GONE
        }
    }
}

@BindingAdapter("animateBackground")
fun setBackgroundResource(view: View, isAnimate: Boolean) {
    if (isAnimate) {
        val from = ContextCompat.getColor(view.context, R.color.colorAccent)
        val to = ContextCompat.getColor(view.context, R.color.transparent)

        val anim = ValueAnimator()
        anim.setIntValues(from, to)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener { valueAnimator -> view.setBackgroundColor((valueAnimator.animatedValue as Int)) }

        anim.duration = 300
        anim.start()
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
