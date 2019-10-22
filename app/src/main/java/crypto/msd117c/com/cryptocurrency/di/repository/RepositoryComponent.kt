package crypto.msd117c.com.cryptocurrency.di.repository

import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository

interface RepositoryComponent {
    val coinsRepository: CoinsRepository
}