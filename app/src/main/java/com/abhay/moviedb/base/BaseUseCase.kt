package com.abhay.moviedb.base

abstract class BaseUseCase<in Params, out Result> {

    abstract suspend fun execute(params: Params): Result

    suspend operator fun invoke(params: Params): Result {
        return execute(params)
    }
}