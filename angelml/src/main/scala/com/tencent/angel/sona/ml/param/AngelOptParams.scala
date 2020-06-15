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

package com.tencent.angel.sona.ml.param
import com.tencent.angel.mlcore.conf.MLCoreConf
import com.tencent.angel.sona.ml.param.shared.HasMaxIter
import com.tencent.angel.sona.tree.objective.metric.EvalMetric


trait AngelOptParams extends Params with HasMaxIter with HasLearningRate
  with HasNumBatch with HasDecayConf with HasIncTrain with HasEarlyStopCheck with ParamsHelper {

  def setMaxIter(value: Int): this.type = setInternal(maxIter, value)

  setDefault(maxIter -> MLCoreConf.DEFAULT_ML_EPOCH_NUM)

  def setLearningRate(value: Double): this.type = setInternal(learningRate, value)

  setDefault(learningRate -> MLCoreConf.DEFAULT_ML_LEARN_RATE)

  def setNumBatch(value: Int): this.type = setInternal(numBatch, value)

  setDefault(numBatch -> MLCoreConf.DEFAULT_ML_NUM_UPDATE_PER_EPOCH)

  def setDecayClass(value: String): this.type = setInternal(decayClass, value)

  setDefault(decayClass -> MLCoreConf.DEFAULT_ML_OPT_DECAY_CLASS_NAME)

  def setDecayAlpha(value: Double): this.type = setInternal(decayAlpha, value)

  setDefault(decayAlpha -> MLCoreConf.DEFAULT_ML_OPT_DECAY_ALPHA)

  def setDecayBeta(value: Double): this.type = setInternal(decayBeta, value)

  setDefault(decayBeta -> MLCoreConf.DEFAULT_ML_OPT_DECAY_BETA)

  def setDecayIntervals(value: Int): this.type = setInternal(decayIntervals, value)

  setDefault(decayIntervals -> MLCoreConf.DEFAULT_ML_OPT_DECAY_INTERVALS)

  def setDecayOnBatch(value: Boolean): this.type = setInternal(decayOnBatch, value)

  setDefault(decayOnBatch -> MLCoreConf.DEFAULT_ML_OPT_DECAY_ON_BATCH)

  setDefault(incTrain, false)

  def setInitModelPath(value: String): this.type = {
    set(incTrain, true)
    setInternal(initModelPath, value)
  }

  setDefault(earlyStopMetric -> EvalMetric.Kind.LOG_LOSS.toString)
  setDefault(earlyStopPatience -> 3)
  setDefault(earlyStopThreshold -> 1e-4)

  def setEarlyStopMetric(value: String): this.type = {
    setInternal(earlyStopMetric, value)
  }

  def setEarlyStopPatience(value: Int): this.type = {
    setInternal(earlyStopPatience, value)
  }

  def setEarlyStopThreshold(value: Double): this.type = {
    setInternal(earlyStopThreshold, value)
  }
}


trait HasLearningRate extends Params {
  final val learningRate: DoubleParam = new DoubleParam(this, "learningRate",
    "learning rate (> 0)", ParamValidators.gt(0))

  final def getLearningRate: Double = $(learningRate)
}


trait HasNumBatch extends Params {
  final val numBatch: IntParam = new IntParam(this, "numBatch",
    "number of batches in each epock (> 0)", ParamValidators.gt(0))

  final def getNumBatch: Int = $(numBatch)
}


trait HasDecayConf extends Params {
  final val decayClass: Param[String] = new Param[String](this, "decayClass",
    "the learning rate decay class name", (value: String) => value != null && value.nonEmpty)

  final val decayAlpha: DoubleParam = new DoubleParam(this, "decayAlpha",
    "the first params of decay", ParamValidators.gt(0))

  final val decayBeta: DoubleParam = new DoubleParam(this, "decayBeta",
    "the second params of decay", ParamValidators.gt(0))

  final val decayIntervals: IntParam = new IntParam(this, "decayIntervals",
    "the decay intervals", ParamValidators.gt(0))

  final val decayOnBatch: BooleanParam = new BooleanParam(this, "decayOnBatch",
    "is the decay on batch or epoch ?")

  final def getDecayClass: String = $(decayClass)

  final def getDecayAlpha: Double = $(decayAlpha)

  final def getDecayBeta: Double = $(decayBeta)

  final def getDecayIntervals: Int = $(decayIntervals)

  final def getDecayOnBatch: Boolean = $(decayOnBatch)
}


trait HasIncTrain extends Params {
  final val incTrain: BooleanParam = new BooleanParam(this, "incTrain",
    "is increase training ?")

  final val initModelPath: Param[String] = new Param[String](this, "initModelPath",
    "the model file path", (value: String) => value != null && value.nonEmpty)

  final def getIncTrain: Boolean = $(incTrain)

  final def getInitModelPath: String = $(initModelPath)
}


trait HasEarlyStopCheck extends Params {
  final val earlyStopPatience: IntParam = new IntParam(this, "earlyStopPatience",
    "early stop when metric not optimize after patience times (>0)", ParamValidators.gt(0))

  final def getEarlyStopPatience: Int = $(earlyStopPatience)

  final val earlyStopMetric: Param[String] = new Param[String](this, "earlyStopMetric",
  "early stop metric, one of (rmse error log-loss cross-entropy precision auc)", (value: String) => value != null && value.nonEmpty)

  final def getEarlyStopMetric: String = $(earlyStopMetric)

  final val earlyStopThreshold: DoubleParam = new DoubleParam(this, "earlyStopThreshold",
    "minimum change in the metric quantity to qualify as an improvement, i.e. an absolute change of less than min_delta, will count as no improvement", ParamValidators.gt(0))

  final def getEarlyStopThreshold: Double = $(earlyStopThreshold)
}
