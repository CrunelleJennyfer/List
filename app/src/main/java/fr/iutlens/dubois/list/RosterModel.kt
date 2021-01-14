package fr.iutlens.dubois.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.roster.RosterListener
import org.jxmpp.jid.BareJid
import org.jxmpp.jid.Jid
import org.jxmpp.jid.impl.JidCreate

class RosterModel() : ViewModel(), RosterListener {
    val entries = MutableLiveData<List<RosterEntry>>()

    fun updateConnection() {
        SmackStore.roster?.addRosterListener(this)
        update()
    }

    private fun update(){
        SmackStore.roster?.entries?.let { entries.postValue(it.sortedBy {entry -> entry.jid }.toList())
            Log.d("RosterModel","entries : "+it.size)
        }
    }

    fun add(jid: String) {
        SmackStore.roster?.sendSubscriptionRequest(JidCreate.bareFrom(jid))
    }

    fun remove(entry: RosterEntry) {
        SmackStore.roster?.removeEntry(entry)
    }

    override fun entriesAdded(addresses: MutableCollection<Jid>?) {
        update()
    }

    override fun entriesUpdated(addresses: MutableCollection<Jid>?) {
        update()
    }

    override fun entriesDeleted(addresses: MutableCollection<Jid>?) {
        update()
    }

    override fun presenceChanged(presence: Presence?) {
        update()
    }



}