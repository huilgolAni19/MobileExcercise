package com.vrllogistics.mobileexercise.adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vrllogistics.mobileexercise.R
import com.vrllogistics.mobileexercise.dataclasses.MenuItem
import com.vrllogistics.mobileexercise.utils.OnItemClickListener
import com.vrllogistics.mobileexercise.utils.OnMenuItemClickListener


class MenuAdapter(context: Context, menuItems: ArrayList<MenuItem>): RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {

    private var context: Context = context
    private var menuItems: ArrayList<MenuItem> = menuItems
    private var listener: OnMenuItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.menu_item, parent, false)
        return MenuItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    fun setOnMenuItemClickListener(listener: OnMenuItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {

        val item: MenuItem = menuItems[position]

        holder.itemName.text = item.itemName
        holder.itemIcon.setImageResource(item.icon)

        holder.itemIcon.setOnClickListener { _ ->
            listener?.invoke(item, position)
        }
        holder.parentLayout.setOnClickListener { _ ->
            listener?.invoke(item, position)
        }
    }

    class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var parentLayout: RelativeLayout = itemView.findViewById(R.id.parentLayout)
        var itemIcon: ImageView = itemView.findViewById(R.id.itemIcon)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
    }

}