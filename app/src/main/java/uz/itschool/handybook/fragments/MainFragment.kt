package uz.itschool.handybook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                R.id.home_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.home_sc) loadFragment(HomeFragment())
                R.id.reading_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.reading_sc){}
                R.id.bookmark_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.bookmark_sc){}
                R.id.profile_sc -> if (binding.mainBottomNavigation.selectedItemId != R.id.profile_sc) loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    private fun setFragmentView() {
        parentFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment())
            .commit()
    }
}