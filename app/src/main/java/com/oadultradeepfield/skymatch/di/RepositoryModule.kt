package com.oadultradeepfield.skymatch.di

import com.oadultradeepfield.skymatch.BuildConfig
import com.oadultradeepfield.skymatch.data.fake.FakeHistoryRepository
import com.oadultradeepfield.skymatch.data.fake.FakeSearchRepository
import com.oadultradeepfield.skymatch.data.fake.FakeSolveRepository
import com.oadultradeepfield.skymatch.domain.repository.IHistoryRepository
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import com.oadultradeepfield.skymatch.domain.repository.ISolveRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  @Singleton
  fun provideSolveRepository(
      fakeSolveRepository: FakeSolveRepository,
  ): ISolveRepository {
    return if (BuildConfig.IS_DEBUG) {
      fakeSolveRepository
    } else {
      fakeSolveRepository
    }
  }

  @Provides
  @Singleton
  fun provideHistoryRepository(
      fakeHistoryRepository: FakeHistoryRepository,
  ): IHistoryRepository {
    return if (BuildConfig.IS_DEBUG) {
      fakeHistoryRepository
    } else {
      fakeHistoryRepository
    }
  }

  @Provides
  @Singleton
  fun provideSearchRepository(
      fakeSearchRepository: FakeSearchRepository,
  ): ISearchRepository {
    return if (BuildConfig.IS_DEBUG) {
      fakeSearchRepository
    } else {
      fakeSearchRepository
    }
  }
}
