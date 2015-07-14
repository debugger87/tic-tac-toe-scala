package com.debugger87.tictactoe.experiment

import scala.util.Random

/**
 * Created by yangchaozhong on 7/14/15.
 */
object Generator {

  /**
   * Always generate this chess board where "-" indicates blank space
   * "-" "X" "O"
   * "X" "-" "O"
   * "-" "-" "-"
   *
   * @return chess board
   */
  def generate: Array[Array[Char]] = {
    val arr = Array.fill(3, 3)('-')
    var x = math.abs(Random.nextInt()) % 9
    var y = (x + 3) % 9
    arr(x / 3)(0) = 'X'
    arr(y / 3)(2) = 'O'
    arr
  }
}
