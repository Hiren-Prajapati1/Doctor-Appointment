package com.example.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appointment.Fragment.BookingFragment
import com.example.appointment.Fragment.HomeFragment
import com.example.appointment.Fragment.ProfileFragment
import com.example.appointment.databinding.ActivityHomePageBinding
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class HomePage : AppCompatActivity() {

    lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.bottomNavigationView.add(
//            CurvedBottomNavigation.Model(1 , "Home" , R.drawable.ic_home)
//        )
//        binding.bottomNavigationView.add(
//            CurvedBottomNavigation.Model(2 , "Explore" , R.drawable.ic_booking)
//        )
//        binding.bottomNavigationView.add(
//            CurvedBottomNavigation.Model(3 , "Plan" , R.drawable.ic_profile)
//        )
//
//        binding.bottomNavigationView.setOnClickMenuListener {
//            when(it.id){
//                1 -> { replaceFragment(HomeFragment()) }
//                2 -> { replaceFragment(BookingFragment()) }
//                3 -> { replaceFragment(ProfileFragment()) }
//            }
//        }
//
//        replaceFragment(HomeFragment())
//        binding.bottomNavigationView.show(1)
        
    //    val navController = findNavController(R.id.fragmentFrameHome)

        val homeFragment = HomeFragment()
        val bookingFragment = BookingFragment()
        val profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFrameHome , homeFragment)
                .commit()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){

                R.id.bottomHome -> currentFragment(homeFragment)
                R.id.bottomBooking -> currentFragment(bookingFragment)
                R.id.bottomProfile -> currentFragment(profileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentFrameHome,fragment)
            .commit()
    }

    fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFrameHome , fragment)
            addToBackStack(null)
            commit()
        }
}