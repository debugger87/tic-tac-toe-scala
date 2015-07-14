package com.debugger87.tictactoe.experiment

/**
 * Created by yangchaozhong on 7/14/15.
 */
object Generator {

  /**
   * Always generate this chess board where "-" indicates whitespace
   * "-" "X" "O"
   * "X" "-" "O"
   * "-" "-" "-"
   *
   * @return chess board
   */
  def generate: Array[Array[Char]] = {
    val arr = Array.ofDim[Char](3, 3)

    arr(0)(0) = '-'
    arr(0)(1) = 'X'
    arr(0)(2) = 'O'
    arr(1)(0) = 'X'
    arr(1)(1) = '-'
    arr(1)(2) = 'O'
    arr(2)(0) = '-'
    arr(2)(1) = '-'
    arr(2)(2) = '-'

    arr
  }
}
