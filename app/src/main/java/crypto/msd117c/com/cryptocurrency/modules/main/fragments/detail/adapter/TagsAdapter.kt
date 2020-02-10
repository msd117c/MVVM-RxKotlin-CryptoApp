package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.ItemViewTagBinding
import javax.inject.Inject

class TagsAdapter @Inject constructor() : RecyclerView.Adapter<TagsAdapter.TagHolder>() {

    private val listOfTags = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder =
        TagHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view_tag, parent, false
            )
        )

    override fun getItemCount(): Int = listOfTags.size

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.bind(listOfTags[position])
    }

    fun setItems(listOfTags: List<String>) {
        this.listOfTags.clear()
        this.listOfTags.addAll(listOfTags)
        notifyDataSetChanged()
    }

    class TagHolder(private val binding: ItemViewTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: String) {
            binding.tag = tag
        }

    }

}