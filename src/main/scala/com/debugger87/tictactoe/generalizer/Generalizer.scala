package com.debugger87.tictactoe.generalizer

/**
 * Created by yangchaozhong on 7/14/15.
 */
object Generalizer {
  val w0 = 1.0
  @volatile var parameter: Array[Double] = Array(0.1, 0.1, 1.0, -10.0)

  def update(sample: (Array[Int], Double)) = {
    require(parameter.size == sample._1.size)

    var i = 0
    var vb = w0
    while (i < parameter.length) {
      vb += parameter(i) * sample._1(i)
      i += 1
    }

    i = 0
    while (i < parameter.length) {
      parameter(i) = parameter(i) + 0.1 * (sample._2 - vb) * sample._1(i)
      i += 1
    }
  }
}
