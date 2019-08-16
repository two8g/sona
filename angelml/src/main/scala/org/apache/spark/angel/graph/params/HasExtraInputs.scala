/*
 * Tencent is pleased to support the open source community by making Angel available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/Apache-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package org.apache.spark.angel.graph.params

import org.apache.spark.angel.ml.param.{Params, StringArrayParam}

trait HasExtraInputs extends Params {

  final val extraInputs = new StringArrayParam(this, "extraInput", "extraInput")

  final def getExtraInputs: Array[String] = $(extraInputs)

  setDefault(extraInputs, Array.empty[String])

  final def setExtraInputs(arr: Array[String]): this.type = set(extraInputs, arr)

}
