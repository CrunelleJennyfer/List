package fr.iutlens.dubois.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_roster.*
import org.jivesoftware.smack.roster.RosterEntry


/**
 * A simple [Fragment] subclass.
 */
class RosterFragment : Fragment() {
    private val rosterModel: RosterModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roster, container, false)
    }

    private lateinit var adapter : RosterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Status.result.observe(viewLifecycleOwner){ if (it is Result.Success) rosterModel.updateConnection() }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // On configure l'adapter, qui prendra les éléments de list, et les affichera en utilisant
        // le layout R.layout.text_row_item
        // On précise aussi les fonctions à appeler lors d'un clic (court / long) sur un élément
        // (ici : appui long pour retirer de la liste)
        adapter = RosterAdapter(R.layout.text_row_item, this::onRosterEntryClick, null)
        recyclerView.adapter =adapter

        rosterModel.entries.observe(viewLifecycleOwner){
            adapter.submitList(it)
            Log.d("RosterFragment","entries :"+it.size)
        }
    }

    fun onRosterEntryClick(entry : RosterEntry){
        rosterModel.selection.value = entry
    }
}