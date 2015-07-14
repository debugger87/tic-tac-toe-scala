package com.debugger87.tictactoe.experiment

import org.scalatest.FunSuite

/**
 * Created by yangchaozhong on 7/14/15.
 */
class GeneratorSuite extends FunSuite {
  test("generate experiment chess board") {
    assert(Generator.generate.isInstanceOf[Array[Array[Char]]])
  }
}
