package fr.iutlens.dubois.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val row_item_layout: Int,
                    private val onItemClickListener: ((Int) -> Unit)?,
                    private val onItemLongClickListener: ((Int) -> Boolean)?):
    ListAdapter<Element, CustomAdapter.ViewHolder>(ElementComparator()) {

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
        viewHolder.textView.text = getItem(position).description

        // Set listeners on item (?.let content is executed only when listener is not null)
        onItemClickListener?.let {
            viewHolder.itemView.setOnClickListener {  it(viewHolder.adapterPosition) }
        }

        onItemLongClickListener?.let {
            viewHolder.itemView.setOnLongClickListener { it(viewHolder.adapterPosition) }
        }
    }


    class ElementComparator : DiffUtil.ItemCallback<Element>() {
        override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
            return oldItem.description == newItem.description && oldItem.num == newItem.num
        }
    }
}
