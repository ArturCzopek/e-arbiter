package pl.cyganki.auth.exception


class WrongGithubUserException: RuntimeException("User doesn't exist in GitHub or didn't log in properly!") {
}