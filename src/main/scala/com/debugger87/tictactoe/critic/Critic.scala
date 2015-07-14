package com.debugger87.tictactoe.critic

import com.debugger87.tictactoe.util.BoardUtil

/**
 * Created by yangchaozhong on 7/14/15.
 */
object Critic extends BoardUtil {
  def completeSampleData(history: Array[Array[Array[Char]]]) = {
    var result = Array[(Array[Int], Double)]()

    var i = 0
    while (i < history.size - 1) {
      val board = history(i)

      val vbt = objectiveFun(history(i + 1))
      result :+= (getBoardProperties(board), vbt)

      i += 1
    }

    val lastBoard = history.last
    if (isFinalBoard(lastBoard)) {
      result :+= (getBoardProperties(lastBoard), objectiveFun(lastBoard))
    } else {
      result :+= (getBoardProperties(lastBoard), -100.0)
    }

    result
  }
}
