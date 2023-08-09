package com.yoo.mediaapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import com.yoo.mediaapp.fragment.InfoFragment
import com.yoo.mediaapp.fragment.MainFragment
import com.yoo.mediaapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import java.util.prefs.Preferences

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle= ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(MainFragment(), "Yulduzli Tunlar")

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {replaceFragment(MainFragment(), "Yulduzli Tunlar")
                    binding.drawerLayout.close()
                }
                R.id.telegram -> {
                    val openUrl=Intent(android.content.Intent.ACTION_VIEW)
                    openUrl.data= Uri.parse("https://t.me/surayyo006")
                    startActivity(openUrl)
                    Toast.makeText(this, "Telegram", Toast.LENGTH_SHORT).show()
                }
                R.id.baholash ->{
                    showCustonDialog()
                    Toast.makeText(this, "Baholash", Toast.LENGTH_SHORT).show()
                }
                R.id.share -> Toast.makeText(this, "Ulashish", Toast.LENGTH_SHORT).show()
                R.id.info -> {
                    replaceFragment(InfoFragment(), "Ilova Haqida")
                    binding.drawerLayout.close()
                }
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        setTitle(title)
    }

    private fun showCustonDialog(){
        val dialog=Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.rating_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val ratingBar:RatingBar=dialog.findViewById(R.id.ratingBar)
        val ok:Button=dialog.findViewById(R.id.ok)
        val cancel:Button=dialog.findViewById(R.id.cancel)

        cancel.setOnClickListener{
            dialog.dismiss()
        }
        ok.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()


    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            true
        }

        return super.onOptionsItemSelected(item)
    }
}
