package com.gamla.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.gamla.artbooktesting.R
import com.gamla.artbooktesting.adapter.ImageRecyclerAdapter
import com.gamla.artbooktesting.getOrAwaitValueTest
import com.gamla.artbooktesting.launchFragmentInHiltContainer
import com.gamla.artbooktesting.repository.FakeArtRepositoryTest
import com.gamla.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var factoryFragment: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun selectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())
        val fakeImageUrl = "thisisanimage.com"

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = factoryFragment
        ) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            imageRecyclerAdapter.images = listOf(fakeImageUrl)
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ItemViewHolder>(
                0, click()
            )
        )

        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.selectedImagesUrl.getOrAwaitValueTest()).isEqualTo(fakeImageUrl)

    }
}