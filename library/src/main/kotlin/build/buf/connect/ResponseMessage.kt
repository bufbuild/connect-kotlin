// Copyright 2022-2023 Buf Technologies, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package build.buf.connect

/**
 * Typed unary response from an RPC.
 */
sealed class ResponseMessage<Output>(
    // The status code of the response.
    open val code: Code,
    // Response headers specified by the server.
    open val headers: Headers,
    // Trailers provided by the server.
    open val trailers: Trailers
) {
    class Success<Output>(
        // The message.
        val message: Output,
        // The status code of the response.
        override val code: Code,
        // Response headers specified by the server.
        override val headers: Headers,
        // Trailers provided by the server.
        override val trailers: Trailers
    ) : ResponseMessage<Output>(code, headers, trailers)

    class Failure<Output>(
        // The error.
        val error: ConnectError,
        // The status code of the response.
        override val code: Code,
        // Response headers specified by the server.
        override val headers: Headers,
        // Trailers provided by the server.
        override val trailers: Trailers
    ) : ResponseMessage<Output>(code, headers, trailers)

    fun <E> failure(function: (Failure<Output>) -> E?): E? {
        if (this is Failure) {
            return function(this)
        }
        return null
    }

    fun <E> success(function: (Success<Output>) -> E?): E? {
        if (this is Success) {
            return function(this)
        }
        return null
    }
}
