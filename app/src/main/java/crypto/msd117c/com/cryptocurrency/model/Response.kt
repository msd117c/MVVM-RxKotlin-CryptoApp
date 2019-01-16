package crypto.msd117c.com.cryptocurrency.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("symbol")
    @Expose
    private var symbol: String? = null
    @SerializedName("rank")
    @Expose
    private var rank: String? = null
    @SerializedName("price_usd")
    @Expose
    private var priceUsd: String? = null
    @SerializedName("price_btc")
    @Expose
    private var priceBtc: String? = null
    @SerializedName("24h_volume_usd")
    @Expose
    private var _24hVolumeUsd: String? = null
    @SerializedName("market_cap_usd")
    @Expose
    private var marketCapUsd: String? = null
    @SerializedName("available_supply")
    @Expose
    private var availableSupply: String? = null
    @SerializedName("total_supply")
    @Expose
    private var totalSupply: String? = null
    @SerializedName("max_supply")
    @Expose
    private var maxSupply: String? = null
    @SerializedName("percent_change_1h")
    @Expose
    private var percentChange1h: String? = null
    @SerializedName("percent_change_24h")
    @Expose
    private var percentChange24h: String? = null
    @SerializedName("percent_change_7d")
    @Expose
    private var percentChange7d: String? = null
    @SerializedName("last_updated")
    @Expose
    private var lastUpdated: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getSymbol(): String? {
        return symbol
    }

    fun setSymbol(symbol: String) {
        this.symbol = symbol
    }

    fun getRank(): String? {
        return rank
    }

    fun setRank(rank: String) {
        this.rank = rank
    }

    fun getPriceUsd(): String? {
        return priceUsd
    }

    fun setPriceUsd(priceUsd: String) {
        this.priceUsd = priceUsd
    }

    fun getPriceBtc(): String? {
        return priceBtc
    }

    fun setPriceBtc(priceBtc: String) {
        this.priceBtc = priceBtc
    }

    fun get24hVolumeUsd(): String? {
        return _24hVolumeUsd
    }

    fun set24hVolumeUsd(_24hVolumeUsd: String) {
        this._24hVolumeUsd = _24hVolumeUsd
    }

    fun getMarketCapUsd(): String? {
        return marketCapUsd
    }

    fun setMarketCapUsd(marketCapUsd: String) {
        this.marketCapUsd = marketCapUsd
    }

    fun getAvailableSupply(): String? {
        return availableSupply
    }

    fun setAvailableSupply(availableSupply: String) {
        this.availableSupply = availableSupply
    }

    fun getTotalSupply(): String? {
        return totalSupply
    }

    fun setTotalSupply(totalSupply: String) {
        this.totalSupply = totalSupply
    }

    fun getMaxSupply(): String? {
        return maxSupply
    }

    fun setMaxSupply(maxSupply: String) {
        this.maxSupply = maxSupply
    }

    fun getPercentChange1h(): String? {
        return percentChange1h
    }

    fun setPercentChange1h(percentChange1h: String) {
        this.percentChange1h = percentChange1h
    }

    fun getPercentChange24h(): String? {
        return percentChange24h
    }

    fun setPercentChange24h(percentChange24h: String) {
        this.percentChange24h = percentChange24h
    }

    fun getPercentChange7d(): String? {
        return percentChange7d
    }

    fun setPercentChange7d(percentChange7d: String) {
        this.percentChange7d = percentChange7d
    }

    fun getLastUpdated(): String? {
        return lastUpdated
    }

    fun setLastUpdated(lastUpdated: String) {
        this.lastUpdated = lastUpdated
    }
}