package pl.cyganki.gateway.filter.utils


object FilterRegex {

    /**
     * This regex says:
     * - case insensitive
     * - match 1 or more letters (module name)
     * - match "/inner" path
     * - match any sign (0 or more)
     */
    val INNER_PATH = "(?i)/\\w+/inner\\S*"

    /**
     * This regex says:
     * - case insensitive
     * - match 1 or more letters (module name)
     * - match "/admin" path
     * - match any sign (0 or more)
     */
    val ADMIN_PATH = "(?i)/\\w+/admin\\S*"

    /**
     * This regex says:
     * - case insensitive
     * - match "auth/api/logout" path
     * - match any sign (0 or more)
     */
    val AUTH_LOGOUT_PATH = "(?i)/auth/api/logout\\S*"

    /**
     * This regex says:
     * - case insensitive
     * - match "auth/api/user" path
     * - match any sign (0 or more)
     */
    val AUTH_USER_PATH = "(?i)/auth/api/user\\S*"

    /**
     * This regex says:
     * - case insensitive
     * - match "auth/api/token" path
     * - match any sign (0 or more)
     */
    val AUTH_TOKEN_PATH = "(?i)/auth/api/token\\S*"


    /**
     * This regex says:
     * - case insensitive
     * - match 1 or more letters (module name)
     * - match "/inner/" path
     * - match any sign (0 or more)
     */
    val REST_API_PATH = "(?i)/\\w+/rest-api\\S*"


    /**
     * This regex says:
     * - case insensitive
     * - match 1 or more letters (module name)
     * - match "/logfile/" path
     * - match any sign (0 or more)
     */
    val LOG_PATH = "(?i)/\\w+/logfile\\S*"
}