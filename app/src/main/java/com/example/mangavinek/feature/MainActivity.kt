package com.example.mangavinek.feature

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.startActivity
import com.example.mangavinek.feature.catalog.presentation.ui.activity.SearchActivity
import com.example.mangavinek.feature.favorite.presentation.ui.fragment.FavoriteFragment
import com.example.mangavinek.feature.home.presentation.ui.fragment.HomeFragment
import com.example.mangavinek.feature.publishing.presentation.ui.fragment.PublishingFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

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
                R.id.ic_favorite -> {
                    supportFragmentManager.addFragment(FavoriteFragment(), FavoriteFragment.TAG)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.item_search) {
            startActivity<SearchActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishApp()
    }

    private fun finishApp() {
        Snackbar.make(constraint_main, R.string.title_snack_bar, Snackbar.LENGTH_LONG)
            .setAction(R.string.subtitle_confirmation_snack_bar) {
                exitProcess(0)
            }
            .show()
    }
}
