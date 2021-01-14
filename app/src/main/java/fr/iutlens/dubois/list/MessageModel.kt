package fr.iutlens.dubois.list

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate


class MessageModel() : ViewModel(), IncomingChatMessageListener {

    val selection = MutableLiveData<RosterEntry>()
    private var _chat : Chat? = null

    val chat : Chat? get(){
        if (_chat == null) _chat = selection.value?.let{SmackStore.chatManager?.chatWith(it.jid.asEntityBareJidIfPossible())}
        return _chat
    }

    fun updateConnection() {
        _chat = null
        SmackStore.chatManager?.addIncomingListener(this)
    }




    fun insert(element: Element) =  viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.insertAll(element)
    }

    fun delete(element: Element) = viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.delete(element)
    }

    fun allElements(): LiveData<List<Element>>? {
        return AppDatabase.getDatabase()?.elementDao()?.getAll()?.asLiveData()
    }

    override fun newIncomingMessage(from: EntityBareJid?, message: Message?, chat: Chat?) {
    }

    fun send(msg: String): Boolean {
        val currentChat : Chat = chat ?: return false
        try {
            currentChat.send(msg)
            return true
        } catch (e:Exception){
            Log.d("chat","Error sending message")
        }
        return false
    }


}