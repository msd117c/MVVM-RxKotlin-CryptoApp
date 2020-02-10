package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.viewmodel

import crypto.msd117c.com.cryptocurrency.data.model.detail.CoinDetail
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlItem
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlLink
import crypto.msd117c.com.cryptocurrency.data.model.detail.UrlSection

class CoinDetailViewModel(private val coinDetail: CoinDetail) {

    private val urls = mutableListOf<UrlItem>()

    init {
        if (coinDetail.urls.website.isNotEmpty()) {
            urls.add(UrlSection("Website"))
            urls.addAll(coinDetail.urls.website.map(::UrlLink))
        }
        if (coinDetail.urls.technical_doc.isNotEmpty()) {
            urls.add(UrlSection("Technical documentation"))
            urls.addAll(coinDetail.urls.technical_doc.map(::UrlLink))
        }
        if (coinDetail.urls.twitter.isNotEmpty()) {
            urls.add(UrlSection("Twitter"))
            urls.addAll(coinDetail.urls.twitter.map(::UrlLink))
        }
        if (coinDetail.urls.reddit.isNotEmpty()) {
            urls.add(UrlSection("Reddit"))
            urls.addAll(coinDetail.urls.reddit.map(::UrlLink))
        }
        if (coinDetail.urls.message_board.isNotEmpty()) {
            urls.add(UrlSection("Message Board"))
            urls.addAll(coinDetail.urls.message_board.map(::UrlLink))
        }
        if (coinDetail.urls.announcement.isNotEmpty()) {
            urls.add(UrlSection("Announcement"))
            urls.addAll(coinDetail.urls.announcement.map(::UrlLink))
        }
        if (coinDetail.urls.chat.isNotEmpty()) {
            urls.add(UrlSection("Chat"))
            urls.addAll(coinDetail.urls.chat.map(::UrlLink))
        }
        if (coinDetail.urls.explorer.isNotEmpty()) {
            urls.add(UrlSection("Explorer"))
            urls.addAll(coinDetail.urls.explorer.map(::UrlLink))
        }
        if (coinDetail.urls.source_code.isNotEmpty()) {
            urls.add(UrlSection("Source code"))
            urls.addAll(coinDetail.urls.source_code.map(::UrlLink))
        }
    }

    fun getIconUrl(): String = coinDetail.logo

    fun getName(): String = "${coinDetail.name} - ${coinDetail.symbol}"

    fun getDescription(): String = coinDetail.description ?: "N/A"

    fun getCategory(): String = coinDetail.category ?: "N/A"

    fun getPlatform(): String = coinDetail.platform ?: "N/A"

    fun getSlug(): String = coinDetail.slug ?: "N/A"

    fun getTags(): List<String> = coinDetail.tags ?: mutableListOf()

    fun getUrls(): List<UrlItem> = urls
}