package jp.pinkikki.account.book.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import jp.pinkikki.account.book.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.item, R.string.amount).apply {
            isDrawerIndicatorEnabled = false
            setToolbarNavigationClickListener { drawerLayout.openDrawer(GravityCompat.START) }
            setHomeAsUpIndicator(R.drawable.ic_action_apple)
        }

        navigationView.setNavigationItemSelectedListener { item -> onNavigationItemSelectedWrapper(drawerLayout, item) }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_contents, Home())
            commit()
        }
    }

    private fun onNavigationItemSelectedWrapper(drawerLayout: DrawerLayout, menuItem: MenuItem): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_contents, getContent(menuItem))
            commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getContent(menuItem: MenuItem): Fragment {
        return when (menuItem.itemId) {
            R.id.nav_item_save -> Save()
            R.id.nav_item_pending -> Pending()
            R.id.nav_sub_item_pending -> SubPending()
            else -> Home()
        }
    }
}
