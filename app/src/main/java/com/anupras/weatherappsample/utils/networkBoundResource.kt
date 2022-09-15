package com.anupras.weatherappsample.utils

import kotlinx.coroutines.flow.*

/**
 * Created by anamika on 16,September,2022
 */

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()      //Get List

    val flow = if (shouldFetch(data)) {    //Check if data need update
        emit(Resource.Loading(data))    // Display Progress

        //If FetchResult fails , because of Internet Connection - Using try catch
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}