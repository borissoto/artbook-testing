package com.gamla.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gamla.artbooktesting.MainCoroutineRule
import com.gamla.artbooktesting.repository.FakeArtRepository
import com.gamla.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import com.gamla.artbooktesting.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        //Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("mona lisa", "davinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(Status.ERROR).isEqualTo(value.status)

    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("", "davinci", "1815")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("mona lisa", "", "1815")
        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}