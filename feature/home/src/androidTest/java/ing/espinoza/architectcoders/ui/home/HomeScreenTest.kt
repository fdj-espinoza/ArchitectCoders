package ing.espinoza.architectcoders.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ing.espinoza.architectcoders.domain.movie.sampleMovies
import ing.espinoza.architectcoders.ui.common.LOADING_INDICATOR_TAG
import ing.espinoza.architectcoders.ui.common.Result
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                onMovieClick = {},
                state = Result.Loading
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                onMovieClick = {},
                state = Result.Error(Exception("An error occurred"))
            )
        }

        onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun whenSuccesState_showMovies(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                onMovieClick = {},
                state = Result.Success(sampleMovies(1, 2, 3))
            )
        }

        onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenMovieClicked_listenerIsCalled(): Unit = with(composeTestRule) {
        var clickedMovieId = -1
        val movies = sampleMovies(1, 2, 3)
        setContent {
            HomeScreen(
                onMovieClick = { clickedMovieId = it.id },
                state = Result.Success(movies)
            )
        }

        onNodeWithText("Title 2").performClick()

        assertEquals(2, clickedMovieId)
    }
}