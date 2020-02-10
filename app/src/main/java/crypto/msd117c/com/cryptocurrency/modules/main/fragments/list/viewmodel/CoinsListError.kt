package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.viewmodel

sealed class CoinsListError
object EmptyList : CoinsListError()
object NoConnection : CoinsListError()
object UnknownError : CoinsListError()