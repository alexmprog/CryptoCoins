package com.alexmprog.common.utils.resource

interface Error

enum class GenericError : Error {
    NO_INTERNET,
    DATABASE_ERROR,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}