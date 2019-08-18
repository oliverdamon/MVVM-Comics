package com.example.mangavinek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mangavinek.home.presentation.view.fragment.HomeFragment
import com.example.mangavinek.publishing.presentation.view.fragment.PublishingFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addFragment(HomeFragment(), HomeFragment.TAG)

        initUi()
    }

    private fun initUi() {
        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    supportFragmentManager.addFragment(HomeFragment(), HomeFragment.TAG)
                }
                R.id.ic_hqs -> {
                    supportFragmentManager.addFragment(PublishingFragment(), PublishingFragment.TAG)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun FragmentManager.addFragment(fragment: Fragment, tag: String) {
        var current = findFragmentByTag(tag)
        beginTransaction().apply {
            primaryNavigationFragment?.let { hide(it) }
            if (current == null) {
                current = fragment
                add(R.id.view_pager, current!!, tag)
            } else {
                show(current!!)
            }
        }
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .setPrimaryNavigationFragment(current)
            .setReorderingAllowed(true)
            .commitNowAllowingStateLoss()
    }

    override fun onBackPressed() {
        finishApp()
    }

    private fun finishApp() {
        Snackbar.make(constraint_main, "Sair do app?", Snackbar.LENGTH_LONG)
            .setAction("Sim") {
                System.exit(0)
            }.show()
    }
}
