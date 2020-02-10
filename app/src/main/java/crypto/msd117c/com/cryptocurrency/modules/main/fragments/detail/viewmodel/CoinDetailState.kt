package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.viewmodel

import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.ui.CoinDetailUiModel

sealed class CoinDetailState
object Loading : CoinDetailState()
data class Loaded(val coinDetailUiModel: CoinDetailUiModel) : CoinDetailState()
data class Error(val error: CoinDetailError) : CoinDetailState()