package fr.iutlens.crunelle.histoire

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import fr.iutlens.dubois.list.R
import fr.iutlens.crunelle.histoire.message.MessageModel
import fr.iutlens.crunelle.histoire.roster.RosterAdapter
import fr.iutlens.crunelle.histoire.roster.RosterModel
import fr.iutlens.crunelle.histoire.util.Result
import fr.iutlens.crunelle.histoire.util.Status
import kotlinx.android.synthetic.main.fragment_roster.*
import org.jivesoftware.smack.roster.RosterEntry


/**
 * A simple [Fragment] subclass.
 */
class RosterFragment : Fragment() {
    private val rosterModel: RosterModel by activityViewModels()
    private val messageModel: MessageModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roster, container, false)
    }

    private lateinit var adapter : RosterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Status.result.observe(viewLifecycleOwner){ if (it is Result.Success) rosterModel.updateConnection() }

        recyclerViewRoster.layoutManager = LinearLayoutManager(requireContext())

        // On configure l'adapter, qui prendra les éléments de list, et les affichera en utilisant
        // le layout R.layout.text_row_item
        // On précise aussi les fonctions à appeler lors d'un clic (court / long) sur un élément
        // (ici : appui long pour retirer de la liste)
        adapter = RosterAdapter(R.layout.contact_item, this::onRosterEntryClick, this::onRosterEntryLongClick)
        recyclerViewRoster.adapter =adapter

        rosterModel.entries.observe(viewLifecycleOwner){
            adapter.submitList(it)
            floatingActionButton.show()
            Log.d("RosterFragment","entries :"+it.size)
        }

        floatingActionButton.setOnClickListener {
            val editText =  EditText(requireContext());
            editText.hint = "Identifiant (ex : example@server.org)"
            MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Demande d'ami")
                    .setView(editText)
                    .setNegativeButton("annuler"){ dialogInterface: DialogInterface, i: Int -> }
                    .setPositiveButton("envoyer"){ dialogInterface: DialogInterface, i: Int ->
                        rosterModel.add(editText.text.toString())
                    }
                    .create().show()
        }
    }

    fun onRosterEntryClick(entry : RosterEntry){
        messageModel.selection.value = entry
    }

    fun onRosterEntryLongClick(entry :RosterEntry): Boolean {
        rosterModel.remove(entry)
        return true
    }
}