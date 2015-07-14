package com.debugger87.tictactoe.generalizer

import org.scalatest.FunSuite

/**
 * Created by yangchaozhong on 7/14/15.
 */
class GeneralizerSuite extends FunSuite {

  val currentW = Array(1.0, 3.0, 2.0, 0.5)
  val sample = (Array(2, 2, 1, 0), 2.7)

  test("update parameters in currentW") {
    println(Generalizer.update(currentW, sample).mkString("[", ",", "]"))
    assert(Generalizer.update(currentW, sample).isInstanceOf[Array[Double]])
  }
}
