package com.anupras.weatherappsample.utils

/**
 * Created by anamika on 16,September,2022
 */
sealed class Resource<T> (val data: T? = null,
                          val error: Throwable? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data,throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}