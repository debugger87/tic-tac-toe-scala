package com.debugger87.tictactoe.generalizer

import org.scalatest.FunSuite

/**
 * Created by yangchaozhong on 7/14/15.
 */
class GeneralizerSuite extends FunSuite {

  val sample = (Array(2, 2, 1, 0), 2.7)

  test("update parameters in currentW") {
    Generalizer.update(sample)
    assert(Generalizer.parameter.isInstanceOf[Array[Double]])
  }
}
