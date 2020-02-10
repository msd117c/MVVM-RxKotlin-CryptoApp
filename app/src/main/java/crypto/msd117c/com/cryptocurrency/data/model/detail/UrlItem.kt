package crypto.msd117c.com.cryptocurrency.data.model.detail

sealed class UrlItem(val url: String)
class UrlSection(url: String): UrlItem(url)
class UrlLink(url: String): UrlItem(url)