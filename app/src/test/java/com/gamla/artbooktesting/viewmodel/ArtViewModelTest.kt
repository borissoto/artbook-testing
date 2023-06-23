package com.gamla.artbooktesting.viewmodel

import com.gamla.artbooktesting.repository.FakeArtRepository
import org.junit.Before

class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        //Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }


}