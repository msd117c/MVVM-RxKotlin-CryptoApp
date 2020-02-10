package crypto.msd117c.com.cryptocurrency.data.model.detail

import com.google.gson.annotations.SerializedName

data class CoinDetail (
	@SerializedName("urls") var urls : Urls,
	@SerializedName("logo") var logo : String,
	@SerializedName("id") var id : Int,
	@SerializedName("name") var name : String,
	@SerializedName("symbol") var symbol : String,
	@SerializedName("slug") var slug : String?,
	@SerializedName("description") var description : String?,
	@SerializedName("tags") var tags : List<String>?,
	@SerializedName("platform") var platform : String?,
	@SerializedName("category") var category : String?
)