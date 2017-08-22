package pl.cyganki.gateway.filter.utils

import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

/**
 * Group class for testing all regex from modules.
 * There is no sense to mock everything. We can test only regexes at this level
 */

class FilterRegexTest {

    @Test
    fun `Inner path - should match for inner path from auth module`() {
        // given
        val path = "auth/inner/"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match for inner path from auth module (mixed case)`() {
        // given
        val path = "auth/INneR/"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match for filter inner path from auth module with one subpath`() {
        // given
        val path = "auth/inner/user"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match for filter inner path from auth module with two subpaths`() {
        // given
        val path = "auth/inner/user/12"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should not filter API request`() {
        // given
        val path = "auth/API/hello"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertFalse(matches)
    }


    @Test
    fun `Inner path - should not filter API request with 'inner' later`() {
        // given
        val path = "auth/api/inner/hello"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertFalse(matches)
    }
}