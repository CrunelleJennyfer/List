package fr.iutlens.dubois.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate


class ListViewModel() : ViewModel() {

    fun insert(element: Element) =  viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.insertAll(element)
    }

    fun delete(element: Element) = viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.delete(element)
    }

    fun allElements(): LiveData<List<Element>>? {
        return AppDatabase.getDatabase()?.elementDao()?.getAll()?.asLiveData()
    }





}