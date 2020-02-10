package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.viewmodel

sealed class CoinDetailError
object NoData: CoinDetailError()
object NoConnection: CoinDetailError()
object UnknownError: CoinDetailError()