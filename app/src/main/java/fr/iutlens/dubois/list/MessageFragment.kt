package fr.iutlens.dubois.list

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_message.*


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment(), TextView.OnEditorActionListener {
    private val messageModel: MessageModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Status.result.observe(viewLifecycleOwner){
            if (it is Result.Success) messageModel.updateConnection()
        }

        messageModel.selection.observe(viewLifecycleOwner){
            textViewContact.text = it.jid
            messageModel.updateConnection()
        }

        editTextMessage.setOnEditorActionListener(this)
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