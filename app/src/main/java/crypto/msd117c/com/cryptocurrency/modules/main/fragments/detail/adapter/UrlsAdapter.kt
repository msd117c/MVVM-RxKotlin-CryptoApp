package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.constants.ItemViewType
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlItem
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlLink
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlSection
import crypto.msd117c.com.cryptocurrency.databinding.ItemViewUrlLinkBinding
import crypto.msd117c.com.cryptocurrency.databinding.ItemViewUrlSectionBinding
import javax.inject.Inject

class UrlsAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfUrls = mutableListOf<UrlItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ItemViewType.URL_SECTION_VIEW_TYPE -> UrlSectionHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_view_url_section, parent, false
                )
            )
            ItemViewType.URL_LINK_VIEW_TYPE -> UrlLinkHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_view_url_link, parent, false
                )
            )
            else -> object : RecyclerView.ViewHolder(View(parent.context)) {}
        }

    override fun getItemViewType(position: Int): Int = when (listOfUrls[position]) {
        is UrlSection -> ItemViewType.URL_SECTION_VIEW_TYPE
        is UrlLink -> ItemViewType.URL_LINK_VIEW_TYPE
    }

    override fun getItemCount(): Int = listOfUrls.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UrlHolder).bind(listOfUrls[position].url)
    }

    fun setItems(listOfUrls: List<UrlItem>) {
        this.listOfUrls.clear()
        this.listOfUrls.addAll(listOfUrls)
        notifyDataSetChanged()
    }

    class UrlSectionHolder(private val binding: ItemViewUrlSectionBinding) :
        RecyclerView.ViewHolder(binding.root), UrlHolder {

        override fun bind(url: String) {
            binding.urlSection = url
        }
    }

    class UrlLinkHolder(private val binding: ItemViewUrlLinkBinding) :
        RecyclerView.ViewHolder(binding.root), UrlHolder {

        override fun bind(url: String) {
            binding.urlLink = url
        }

    }

    interface UrlHolder {
        fun bind(url: String)
    }

}