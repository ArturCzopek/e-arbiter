package pl.cyganki.gateway.filter.utils

import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

/**
 * Group class for testing all regex from modules.
 * There is no sense to mock everything. We can test only regexes at this level
 */

class FilterRegexTest {

    /**
     * Inner path test
     */
    
    @Test
    fun `Inner path - should match inner path from auth module`() {
        // given
        val path = "/auth/inner/"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match inner path from auth module (mixed case)`() {
        // given
        val path = "/auth/INneR/"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match inner path from auth module with one subpath`() {
        // given
        val path = "/auth/inner/user"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should match inner path from auth module with two subpaths`() {
        // given
        val path = "/auth/inner/user/12"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Inner path - should not match API request`() {
        // given
        val path = "/auth/API/hello"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertFalse(matches)
    }


    @Test
    fun `Inner path - should not match API request with 'inner' later`() {
        // given
        val path = "/auth/api/inner/hello"

        // when
        val matches = Pattern.matches(FilterRegex.INNER_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * Admin path test
     */
    
    @Test
    fun `Admin path - should match admin path from executor module`() {
        // given
        val path = "/exec/admin/"

        // when
        val matches = Pattern.matches(FilterRegex.ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Admin path - should match admin path from executor module (mixed case)`() {
        // given
        val path = "/exEc/aDMin/"

        // when
        val matches = Pattern.matches(FilterRegex.ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Admin path - should match admin path from exec module with one subpath`() {
        // given
        val path = "/exec/admin/test"

        // when
        val matches = Pattern.matches(FilterRegex.ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Admin path - should match admin path from exec module with two subpaths`() {
        // given
        val path = "/exec/admin/test/15"

        // when
        val matches = Pattern.matches(FilterRegex.ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Admin path - should not match API request`() {
        // given
        val path = "/exec/API/hello"

        // when
        val matches = Pattern.matches(FilterRegex.ADMIN_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    @Test
    fun `Admin path - should not match API request with admin inside`() {
        // given
        val path = "/exec/API/admin/hello"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * SysAdmin path test
     */
    
    @Test
    fun `SysAdmin path - should match sysadmin path from executor module`() {
        // given
        val path = "/exec/sysadmin/"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `SysAdmin path - should match sysadmin path from executor module (mixed case)`() {
        // given
        val path = "/exEc/sySaDMin/"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `SysAdmin path - should match sysadmin path from exec module with one subpath`() {
        // given
        val path = "/exec/sysadmin/test"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `SysAdmin path - should match sysadmin path from exec module with two subpaths`() {
        // given
        val path = "/exec/sysadmin/test/15"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `SysAdmin path - should not match API request`() {
        // given
        val path = "/exec/API/hello"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    @Test
    fun `SysAdmin path - should not match API request with sysadmin inside`() {
        // given
        val path = "/exec/API/sysadmin/hello"

        // when
        val matches = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * Auth Logout path test
     */

    @Test
    fun `Auth Logout path - should match logout path`() {
        // given
        val path = "/auth/api/logout"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Logout path - should match logout path - mixed case`() {
        // given
        val path = "/auTh/aPI/loGout"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Logout path - should match logout path - with params`() {
        // given
        val path = "/auth/api/logout?jsessionid=3402359054"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertTrue(matches)
    }
    
    @Test
    fun `Auth Logout path - should match logout path with subpath`() {
        // given
        val path = "/auth/api/logout/15"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Logout path - should match logout path with two subpaths`() {
        // given
        val path = "/auth/api/logout/user/13"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Logout path - should not match API request`() {
        // given
        val path = "/auth/api/hello"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * Auth User path test
     */

    @Test
    fun `Auth User path - should match user path`() {
        // given
        val path = "/auth/api/user"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth User path - should match user path - mixed case`() {
        // given
        val path = "/auTh/aPI/uSER"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth User path - should match user path - with params`() {
        // given
        val path = "/auth/api/user?jsessionid=3402359054"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth User path - should match user path with subpath`() {
        // given
        val path = "/auth/api/user/15"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth User path - should match user path with two subpaths`() {
        // given
        val path = "/auth/api/user/hi/333"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth User path - should not match API request`() {
        // given
        val path = "/auth/api/hello"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_USER_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * Auth Token path test
     */

    @Test
    fun `Auth Token path - should match token path`() {
        // given
        val path = "/auth/api/token"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Token path - should match token path - mixed case`() {
        // given
        val path = "/auTh/aPI/toKeN"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Token path - should match token path - with params`() {
        // given
        val path = "/auth/api/token?jsessionid=34023519054"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Token path - should match token path with subpath`() {
        // given
        val path = "/auth/api/token/15"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Token path - should match token path with two subpaths`() {
        // given
        val path = "/auth/api/token/hi/333"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `Auth Token path - should not match API request`() {
        // given
        val path = "/auth/api/hello"

        // when
        val matches = Pattern.matches(FilterRegex.AUTH_TOKEN_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    /**
     * Rest API path test
     */


    @Test
    fun `REST API path - should match rest-api path from executor module`() {
        // given
        val path = "/exec/rest-api/"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `REST API path - should match rest-api path from executor module (mixed case)`() {
        // given
        val path = "/exEc/reST-aPI/"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `REST API path - should match rest-api path from exec module with one subpath`() {
        // given
        val path = "/exec/rest-api/test"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `REST API path - should match rest-api path from exec module with two subpaths`() {
        // given
        val path = "/exec/rest-api/test/15"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertTrue(matches)
    }

    @Test
    fun `REST API path - should not match API request`() {
        // given
        val path = "/exec/API/hello"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertFalse(matches)
    }

    @Test
    fun `REST API path - should not match API request with rest-api inside`() {
        // given
        val path = "/exec/API/rest-api/hello"

        // when
        val matches = Pattern.matches(FilterRegex.REST_API_PATH, path)

        // then
        Assert.assertFalse(matches)
    }
}