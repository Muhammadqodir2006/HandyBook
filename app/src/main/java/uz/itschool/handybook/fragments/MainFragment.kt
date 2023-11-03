package uz.itschool.handybook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setFragmentView()
        setBottomNavigation()

        return binding.root
    }

    private fun setBottomNavigation() {
        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.home_sc) loadFragment(
                    HomeFragment()
                )

                R.id.reading_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.reading_sc) loadFragment(
                    ReadFragment()
                )

                R.id.bookmark_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.bookmark_sc) loadFragment(
                    SavedFragment()
                )

                R.id.profile_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.profile_sc) loadFragment(
                    ProfileFragment()
                )
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.fragment_appear)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit()
            }

            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

        })
        binding.mainFragmentContainer.startAnimation(anim)
    }

    private fun setFragmentView() {
        parentFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment())
            .commit()
    }
}