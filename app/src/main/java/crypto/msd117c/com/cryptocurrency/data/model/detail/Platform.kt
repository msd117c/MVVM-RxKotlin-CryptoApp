package crypto.msd117c.com.cryptocurrency.data.model.detail

import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("id") var id: Int? = -1,
    @SerializedName("name") var name: String? = "",
    @SerializedName("symbol") var symbol: String? = "",
    @SerializedName("slug") var slug: String? = "",
    @SerializedName("token_address") var tokenAddress: String? = ""
)