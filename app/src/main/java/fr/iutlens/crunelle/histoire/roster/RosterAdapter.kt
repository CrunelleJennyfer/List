package fr.iutlens.crunelle.histoire.roster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.iutlens.dubois.list.R
import org.jivesoftware.smack.roster.RosterEntry

class RosterAdapter(private val row_item_layout: Int,
                    private val onItemClickListener: ((RosterEntry) -> Unit)?,
                    private val onItemLongClickListener: ((RosterEntry) -> Boolean)?):
    ListAdapter<RosterEntry, RosterAdapter.ViewHolder>(ElementComparator()) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(row_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val entry : RosterEntry = getItem(position)
        viewHolder.textView.text = entry.jid.toString()

        // Set listeners on item (?.let content is executed only when listener is not null)
        onItemClickListener?.let {
            viewHolder.itemView.setOnClickListener { it(entry) }
        }

        onItemLongClickListener?.let {
            viewHolder.itemView.setOnLongClickListener { it(entry) }
        }
    }


    class ElementComparator : DiffUtil.ItemCallback<RosterEntry>() {
        override fun areItemsTheSame(oldItem: RosterEntry, newItem: RosterEntry): Boolean {
            return oldItem.jid == newItem.jid
        }

        override fun areContentsTheSame(oldItem: RosterEntry, newItem: RosterEntry): Boolean {
            return oldItem == newItem
        }
    }
}
