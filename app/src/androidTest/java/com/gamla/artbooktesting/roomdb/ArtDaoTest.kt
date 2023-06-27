package com.gamla.artbooktesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.gamla.artbooktesting.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

//    private lateinit var database: ArtDatabase
    private lateinit var artDao: ArtDao

    @Before
    fun setup() {
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArtDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()

        artDao = database.artDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertArtTesting() = runTest{
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1800, "test.com", 1)
        artDao.insertArt(exampleArt)

        val list = artDao.observeArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runTest {
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1800, "test.com", 1)
        artDao.insertArt(exampleArt)
        artDao.deleteArt(exampleArt)

        val list = artDao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)

    }

}