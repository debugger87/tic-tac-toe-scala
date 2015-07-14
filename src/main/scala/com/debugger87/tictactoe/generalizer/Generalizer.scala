package com.debugger87.tictactoe.generalizer

/**
 * Created by yangchaozhong on 7/14/15.
 */
object Generalizer {
  val w0 = 1.0
  @volatile var parameter: Array[Double] = Array(0.1, 0.1, 1.0, -2.0)

  def update(w: Array[Double], sample: (Array[Int], Double)): Array[Double] = {
    require(w.size == sample._1.size)

    var i = 0
    var Vb = w0
    while (i < w.length) {
      Vb += w(i) * sample._1(i)
      i += 1
    }

    val result = new Array[Double](w.size)
    i = 0
    while (i < result.length) {
      result(i) = w(i) + 0.1 * (sample._2 - Vb) * sample._1(i)

      i += 1
    }

    parameter = result

    result
  }
}
