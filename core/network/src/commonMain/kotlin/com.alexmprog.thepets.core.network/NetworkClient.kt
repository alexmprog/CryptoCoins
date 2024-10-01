package com.alexmprog.thepets.core.network

import com.alexmprog.common.logger.Logger
import com.alexmprog.common.utils.resource.Error
import com.alexmprog.common.utils.resource.GenericError
import com.alexmprog.common.utils.resource.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger as KtorLogger

internal fun createKtorClient(): HttpClient = HttpClient {
    install(HttpTimeout) {
        requestTimeoutMillis = 15_000
    }
    install(ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
        )
    }
    install(Logging) {
        level = LogLevel.ALL
        logger = object : KtorLogger {
            override fun log(message: String) {
                Logger.log("NetworkClient", message = message)
            }
        }
    }
}

suspend inline fun <reified T> HttpClient.fetch(
    block: HttpRequestBuilder.() -> Unit
): Resource<T, Error> {
    val response = try {
        request(block)
    } catch (e: UnresolvedAddressException) {
        return Resource.Failure(GenericError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Resource.Failure(GenericError.SERIALIZATION)
    }
    return response.toResult<T>()
}

suspend inline fun <reified T> HttpClient.fetchUrl(url: String): Resource<T, Error> {
    val response = try {
        get(url)
    } catch (e: UnresolvedAddressException) {
        return Resource.Failure(GenericError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Resource.Failure(GenericError.SERIALIZATION)
    }
    return response.toResult<T>()
}

suspend inline fun <reified T> HttpResponse.toResult(): Resource<T, Error> {
    return when (status.value) {
        in 200..299 -> Resource.Success(body<T>())
        in 500..599 -> Resource.Failure(GenericError.SERVER_ERROR)
        else -> Resource.Failure(GenericError.UNKNOWN)
    }
}