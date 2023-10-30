@file:Suppress("DEPRECATION")

package uz.itschool.handybook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.RatingAdapter
import uz.itschool.handybook.databinding.FragmentAddCommentBinding
import uz.itschool.handybook.model.Book

private const val ARG_PARAM1 = "book"

class AddCommentFragment : Fragment() {
    private lateinit var binding: FragmentAddCommentBinding
    private lateinit var book: Book
    private lateinit var ratingAdapter: RatingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getSerializable(ARG_PARAM1) as Book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCommentBinding.inflate(inflater, container, false)
        binding.addCommentStarRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setBackButton()
        setBookMarkButton()
        setTitleText()
        setSendListener()
        setCancelListener()
        setEmojiRate()

        return binding.root
    }

    private fun setCancelListener() {
        findNavController().popBackStack()
    }

    private fun setSendListener() {
        // TODO: Set listener
    }

    private fun setEmojiRate() {
        ratingAdapter = RatingAdapter(object : RatingAdapter.RatingClickInterface {
            override fun onClick(previous: Int, current: Int) {
                val nextEye = if (current == 2) R.drawable.emoji_eye2 else R.drawable.emoji_eye1
                val nextMouth = when (current) {
                    1 -> R.drawable.emoji_mouth1
                    2 -> R.drawable.emoji_mouth2
                    3 -> R.drawable.emoji_mouth3
                    4 -> R.drawable.emoji_mouth5
                    else -> {
                        R.drawable.emoji_mouth5
                    }
                }
                animateMouth(nextMouth, previous, current)

                if (previous == 5) {
                    disappearHeart(nextEye)
                    return
                }
                if (previous == 2 || current == 2) {
                    if (current == 5) {
                        val animEye =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_disappear)
                        animEye.setAnimationListener(object : AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {}
                            override fun onAnimationRepeat(animation: Animation?) {}
                            override fun onAnimationEnd(animation: Animation?) {
                                val anim2 = AnimationUtils.loadAnimation(
                                    requireContext(),
                                    R.anim.emoji_appear
                                )
                                anim2.setAnimationListener(object : AnimationListener {
                                    override fun onAnimationStart(animation: Animation?) {}
                                    override fun onAnimationRepeat(animation: Animation?) {}
                                    override fun onAnimationEnd(animation: Animation?) {
                                        appearHeart()
                                    }
                                })
                                binding.addCommentEmojiEye.setImageResource(nextEye)
                                binding.addCommentEmojiEye.startAnimation(anim2)
                            }
                        })
                        binding.addCommentEmojiEye.startAnimation(animEye)
                    }else{
                        animateEye(nextEye)
                    }
                    return
                }
                if (current == 5) appearHeart()
            }
        })
        binding.addCommentStarRecycler.adapter = ratingAdapter
    }

    private fun animateEye(nextEye: Int) {
        val animEye = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_disappear)
        animEye.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                val animEye2 = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_appear)
                binding.addCommentEmojiEye.setImageResource(nextEye)
                binding.addCommentEmojiEye.startAnimation(animEye2)
            }
        })
        binding.addCommentEmojiEye.startAnimation(animEye)
    }

    private fun appearHeart() {
        val animHeart = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_heart_appear)
        binding.addCommentEyeHeart.visibility = View.VISIBLE
        binding.addCommentEyeHeart.startAnimation(animHeart)
    }

    private fun disappearHeart(nextEye: Int) {
        val animEye = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_heart_disappear)
        animEye.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.addCommentEyeHeart.visibility = View.INVISIBLE
            }

        })
        binding.addCommentEmojiEye.setImageResource(nextEye)
        binding.addCommentEyeHeart.startAnimation(animEye)
    }

    private fun animateMouth(nextMouth: Int, previous:Int, current:Int) {
        if (previous == 4 && current == 5) return
        if (previous == 5 && current == 4) return
        val animMouth = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_disappear)
        animMouth.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.addCommentEmojiMouth.setImageResource(nextMouth)
                val animMouth2 = AnimationUtils.loadAnimation(requireContext(), R.anim.emoji_appear)
                binding.addCommentEmojiMouth.startAnimation(animMouth2)
            }
        })
        binding.addCommentEmojiMouth.startAnimation(animMouth)
    }

    @SuppressLint("SetTextI18n")
    private fun setTitleText() {
//        binding.addCommentTitleText.text = """"“${book.name}” asari sizga qanchalik manzur keldi?"""
    }

    private fun setBookMarkButton() {
        // TODO: Set listener
    }

    private fun setBackButton() {
        binding.addCommentBack.setOnClickListener { findNavController().popBackStack() }
    }

}