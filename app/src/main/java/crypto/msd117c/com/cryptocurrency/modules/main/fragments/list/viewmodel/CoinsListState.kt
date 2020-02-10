package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.viewmodel

import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.ui.CoinsListUiModel

sealed class CoinsListState
object Loading : CoinsListState()
data class Loaded(val mainUiModel: CoinsListUiModel) : CoinsListState()
data class Error(val error: CoinsListError) : CoinsListState()