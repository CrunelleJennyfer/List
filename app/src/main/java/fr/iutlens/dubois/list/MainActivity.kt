package fr.iutlens.dubois.list

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.security.ProviderInstaller
import fr.iutlens.dubois.list.ui.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_roster.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jivesoftware.smack.android.AndroidSmackInitializer

class MainActivity : AppCompatActivity() {

    private val messageModel: MessageModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Décommentez la ligne suivante pour vider les informations de login au prochain lancement
//        getSharedPreferences("login", MODE_PRIVATE).edit().clear().apply()

        setContentView(R.layout.activity_main)

        Status.result.observe(this){
            textViewStatus.text=it.description
            textViewStatus.visibility =  if (it is Result.Success) View.GONE else View.VISIBLE
            progressBar.visibility = if (it is Result.Processing) View.VISIBLE else View.GONE

            if (it is Result.Success){
                messageModel.updateConnection()
                val fragment = RosterFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss()
            }
        }

        messageModel.selection.observe(this){
            Log.d("Selection",it.jid.toString())
            val fragment = MessageFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                    .addToBackStack("OpenChat")
                .commitAllowingStateLoss()
        }


        if(savedInstanceState == null) { // initial transaction should be wrapped like this
            val fragment : Fragment = if (SmackStore.neverLogged(this))  LoginFragment() else RosterFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
        }
        init()
    }

    private fun init() {
        GlobalScope.launch {
            AppDatabase.getDatabase(this@MainActivity)
            ProviderInstaller.installIfNeeded(this@MainActivity)
            AndroidSmackInitializer.initialize(this@MainActivity);
            SmackStore.attemptDefaultLogin(this@MainActivity)
        }
    }
}