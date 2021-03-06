/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.holdenkarau.kafka.python.cthulhu;

import scala.io.Source

import org.apache.kafka.streams.kstream.{ValueTransformer, ValueTransformerSupplier}

class PythonStringValueTransformerSupplier(val code: String)
    extends ValueTransformerSupplier[String, String] {
  override def get(): ValueTransformer[String, String] = {
    new PythonValueTransformer(code)
  }
}

object PythonStringValueTransformerSupplier {
  def apply(filePath: String): PythonStringValueTransformerSupplier = {
    val files = List(filePath, "kafka_streams_python_cthulhu/main.py")
    val code = files.map(path => Source.fromFile(path).mkString)
      .fold("")((str1, str2) => str1 + str2)
    new PythonStringValueTransformerSupplier(code)
  }
}
