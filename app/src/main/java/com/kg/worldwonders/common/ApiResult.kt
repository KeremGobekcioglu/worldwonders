package com.kg.worldwonders.common

/**
 * Wraps the outcome of an operation that can fail.
 *
 * Instead of returning List<Webcam> and hoping nothing throws, we return
 * Result<List<Webcam>>. The caller is forced to handle both outcomes:
 *
 *     when (val result = repository.getWebcams()) {
 *         is Result.Success -> showWebcams(result.data)
 *         is Result.Error   -> showMessage(result.message)
 *         Result.Loading    -> showSpinner()
 *     }
 *
 * ---
 * About `out T`:
 * It means T only ever comes OUT of this type (as a return value), never goes
 * in (as a parameter). Because of that guarantee, Kotlin lets a Result<Webcam>
 * be used where a Result<Any> is expected.
 *
 * The practical reason we need it: Loading and Error carry no data, so they're
 * declared as Result<Nothing>. Without `out`, this line wouldn't compile:
 *
 *     val state: Result<List<Webcam>> = Result.Loading
 *
 * That's it. You don't have to think about it again — it just makes the
 * no-data cases usable everywhere.
 */
sealed interface ApiResult<out T> {

    /** The operation worked. [data] is the thing you asked for. */
    data class Success<T>(val data: T) : ApiResult<T>

    /**
     * The operation failed.
     *
     * @param message text you can show the user.
     * @param cause the original exception — for logging, not for the UI.
     *        Keep it so you can tell a timeout apart from a 401 in Crashlytics.
     *        A user-facing string can't tell you that.
     */
    data class Error(val message: String, val cause: Throwable? = null) : ApiResult<Nothing>

    /**
     * Still in flight. Emitted by the ViewModel before the call starts —
     * the repository never returns this, since a finished function isn't loading.
     *
     * `Nothing` is Kotlin's "no value can exist here" type. Using it says
     * Loading carries no data, and lets this single shared instance work as
     * a Result<List<Webcam>>, a Result<User>, or anything else.
     */
    data object Loading : ApiResult<Nothing>
}