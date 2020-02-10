package crypto.msd117c.com.cryptocurrency.data.model.detail

import com.google.gson.annotations.SerializedName

data class Urls (
	@SerializedName("website") val website : List<String>,
	@SerializedName("technical_doc") val technical_doc : List<String>,
	@SerializedName("twitter") val twitter : List<String>,
	@SerializedName("reddit") val reddit : List<String>,
	@SerializedName("message_board") val message_board : List<String>,
	@SerializedName("announcement") val announcement : List<String>,
	@SerializedName("chat") val chat : List<String>,
	@SerializedName("explorer") val explorer : List<String>,
	@SerializedName("source_code") val source_code : List<String>
)