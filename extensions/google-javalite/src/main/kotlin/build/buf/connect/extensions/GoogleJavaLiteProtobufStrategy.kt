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

package build.buf.connect.extensions

import build.buf.connect.Codec
import build.buf.connect.ErrorDetailParser
import build.buf.connect.SerializationStrategy
import build.buf.connect.codecNameProto
import com.google.protobuf.GeneratedMessageLite
import kotlin.reflect.KClass

/**
 * The Google Java-lite Protobuf serialization strategy.
 */
class GoogleJavaLiteProtobufStrategy : SerializationStrategy {
    override fun serializationName(): String {
        return codecNameProto
    }

    /**
     * This unchecked cast assumes the underlying class type is
     * a Google GeneratedMessageLite.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <E : Any> codec(clazz: KClass<E>): Codec<E> {
        val messageClass = clazz as KClass<GeneratedMessageLite<out GeneratedMessageLite<*, *>, *>>
        return GoogleLiteProtoAdapter(messageClass) as Codec<E>
    }

    override fun errorDetailParser(): ErrorDetailParser {
        return JavaLiteErrorParser
    }
}
