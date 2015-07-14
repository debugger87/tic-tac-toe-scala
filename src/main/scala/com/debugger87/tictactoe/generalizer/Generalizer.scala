package com.debugger87.tictactoe.generalizer

/**
 * Created by yangchaozhong on 7/14/15.
 */
class Generalizer {
  val w0 = 1.0

  def update(w: Array[Double], sample: (Array[Int], Double)): Array[Double] = {
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

    result
  }
}
