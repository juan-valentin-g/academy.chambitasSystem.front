package com.example.chambitassystemfront.data.remote

import com.google.gson.JsonParser
import java.io.IOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class ApiException(
    val statusCode: Int?,
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

suspend inline fun <T> apiCall(crossinline request: suspend () -> T): Result<T> = try {
    Result.success(request())
} catch (error: CancellationException) {
    throw error
} catch (error: Throwable) {
    Result.failure(error.toApiException())
}

fun Throwable.toApiException(): ApiException = when (this) {
    is ApiException -> this
    is SocketTimeoutException -> ApiException(
        statusCode = null,
        message = "El servidor tardo demasiado en responder",
        cause = this
    )
    is HttpException -> ApiException(
        statusCode = code(),
        message = readApiMessage() ?: defaultHttpMessage(code()),
        cause = this
    )
    is IOException -> ApiException(
        statusCode = null,
        message = "No se pudo establecer conexion con el servidor",
        cause = this
    )
    else -> ApiException(
        statusCode = null,
        message = message ?: "Ocurrio un error inesperado",
        cause = this
    )
}

private fun HttpException.readApiMessage(): String? {
    val rawBody = response()?.errorBody()?.string()?.takeIf { it.isNotBlank() }
        ?: return null

    return runCatching {
        @Suppress("DEPRECATION")
        val message = JsonParser().parse(rawBody)
            .asJsonObject
            .get("message")
            ?: return@runCatching null

        when {
            message.isJsonArray -> message.asJsonArray.joinToString("\n") { it.asString }
            message.isJsonPrimitive -> message.asString
            else -> null
        }
    }.getOrNull()
}

private fun defaultHttpMessage(statusCode: Int): String = when (statusCode) {
    400 -> "La solicitud contiene datos invalidos"
    401 -> "La sesion no es valida o ha vencido"
    403 -> "No tienes permiso para realizar esta accion"
    404 -> "No se encontro el recurso solicitado"
    409 -> "La operacion entra en conflicto con el estado actual"
    in 500..599 -> "El servidor no pudo completar la solicitud"
    else -> "La solicitud no pudo completarse"
}
