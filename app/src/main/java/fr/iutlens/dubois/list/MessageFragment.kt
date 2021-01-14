package fr.iutlens.dubois.list

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ListAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_roster.*


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment(), TextView.OnEditorActionListener {
    private var messageList: LiveData<List<Message>>? = null
    private val messageModel: MessageModel by activityViewModels()
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerViewMessage.layoutManager = LinearLayoutManager(requireContext())

        adapter = MessageAdapter(null, null)
        recyclerViewMessage.adapter = adapter

        Status.result.observe(viewLifecycleOwner){
            if (it is Result.Success) messageModel.updateConnection()
        }

        messageModel.selection.observe(viewLifecycleOwner){
            textViewContact.text = it.jid
            messageModel.updateConnection()

            messageList?.removeObservers(viewLifecycleOwner)
            messageList = messageModel.allMessagesWith(it.jid.toString())
            messageList?.observe(viewLifecycleOwner) { list->
                Log.d("Adapter", "new message")
                adapter.submitList(list)
                recyclerViewMessage.smoothScrollToPosition(list.lastIndex)
            }
        }

        editTextMessage.setOnEditorActionListener(this)

        // Gestion de l'ouverture du clavier virtuel : on se positionne en bas de la liste
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val pos : Int? = (recyclerViewMessage.adapter as MessageAdapter).currentList.lastIndex
            if (pos != null && pos != -1){ recyclerViewMessage.smoothScrollToPosition(pos) }
        }

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_SEND) {
            val success : Boolean = messageModel.send(editTextMessage.text.toString())
            if (success) editTextMessage.text.clear()
            return true
        }
        return false
    }

}