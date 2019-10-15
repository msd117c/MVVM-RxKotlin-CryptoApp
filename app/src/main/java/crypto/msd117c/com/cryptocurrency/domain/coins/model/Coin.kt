package crypto.msd117c.com.cryptocurrency.domain.coins.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("symbol")
    @Expose
    var symbol: String? = null,
    @SerializedName("rank")
    @Expose
    var rank: String? = null,
    @SerializedName("price_usd")
    @Expose
    var priceUsd: String? = null,
    @SerializedName("price_btc")
    @Expose
    var priceBtc: String? = null,
    @SerializedName("24h_volume_usd")
    @Expose
    var _24hVolumeUsd: String? = null,
    @SerializedName("market_cap_usd")
    @Expose
    var marketCapUsd: String? = null,
    @SerializedName("available_supply")
    @Expose
    var availableSupply: String? = null,
    @SerializedName("total_supply")
    @Expose
    var totalSupply: String? = null,
    @SerializedName("max_supply")
    @Expose
    var maxSupply: String? = null,
    @SerializedName("percent_change_1h")
    @Expose
    var percentChange1h: String? = null,
    @SerializedName("percent_change_24h")
    @Expose
    var percentChange24h: String? = null,
    @SerializedName("percent_change_7d")
    @Expose
    var percentChange7d: String? = null,
    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null
)