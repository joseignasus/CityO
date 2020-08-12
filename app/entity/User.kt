@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var email: String? = ""

    // getter
    get() = username
    get() = email

    // setter
    set(value) {
        username = value
    }
    set(value) {
        email = value
    }
)


