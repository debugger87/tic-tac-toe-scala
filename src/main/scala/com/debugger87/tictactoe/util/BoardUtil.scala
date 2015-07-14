package com.debugger87.tictactoe.util

import com.debugger87.tictactoe.generalizer.Generalizer

/**
 * Created by yangchaozhong on 7/14/15.
 */
trait BoardUtil {
  val lines = Array(
    Array((0, 0), (0, 1), (0, 2)),
    Array((0, 0), (1, 0), (2, 0)),
    Array((2, 0), (2, 1), (2, 2)),
    Array((0, 2), (1, 2), (2, 2)),
    Array((1, 0), (1, 1), (1, 2)),
    Array((0, 1), (1, 1), (2, 1)),
    Array((0, 0), (1, 1), (2, 2)),
    Array((2, 0), (1, 1), (0, 2))
  )

  def isFinalBoard(candidateBoard: Array[Array[Char]]) = {
    lines.count(line => {
      val str = line.map(pos => {
        val x = pos._1
        val y = pos._2

        candidateBoard(x)(y)
      }).mkString("")

      str == "XXX" || str == "OOO"
    }) > 0 || candidateBoard.map(_.count(x => x == '-')).sum == 0
  }

  /**
   *  - X -
   *  - - x
   *  O - O
   *
   * */
  def countWinningChance(candidateBoard: Array[Array[Char]], isComputer: Boolean) = {
    val flag = if (isComputer) 'X' else 'O'

    lines.count {line => {
      line.count(pos => {
        val x = pos._1
        val y = pos._2

        candidateBoard(x)(y) == flag
      }) == 2 && line.count( pos => {
        val x = pos._1
        val y = pos._2

        candidateBoard(x)(y) == '-'
      }) == 1
    }
    }
  }

  def countCorner(candidateBoard: Array[Array[Char]], isComputer: Boolean) = {
    val flag = if (isComputer) 'X' else 'O'
    var count = 0
    if (candidateBoard(0)(0) == flag) count += 1
    if (candidateBoard(2)(0) == flag) count += 1
    if (candidateBoard(0)(2) == flag) count += 1
    if (candidateBoard(2)(2) == flag) count += 1
    count
  }

  def objectiveFun(candidateBoard: Array[Array[Char]]) = {
    if (isFinalBoard(candidateBoard)) {
      def getScore(isComputer: Boolean) = {
        val flag = if (isComputer) 'X' else 'O'
        lines.count(line =>
          line.count(pos => {
            val x = pos._1
            val y = pos._2

            candidateBoard(x)(y) == flag
          }) == 3
        )
      }

      val computerScore = getScore(isComputer = true)
      val yourScore = getScore(isComputer = false)
      if (computerScore > 0) {
        100.0
      } else if (yourScore > 0) {
        -100.0
      } else {
        0.0
      }
    } else if (countWinningChance(candidateBoard, isComputer = false) > 0) {
      -100.0
    } else {
      val x1 = countCorner(candidateBoard, isComputer = true)
      val x2 = countCorner(candidateBoard, isComputer = false)
      val x3 = countWinningChance(candidateBoard, isComputer = true)
      val x4 = countWinningChance(candidateBoard, isComputer = false)
      val arr = Array(x1, x2, x3, x4)

      Generalizer.w0 + (0 to 3).map(i => Generalizer.parameter(i) * arr(i)).sum
    }
  }

  def getBoardProperties(candidateBoard: Array[Array[Char]]) = {
    Array(countCorner(candidateBoard, true), countCorner(candidateBoard, false),
      countWinningChance(candidateBoard, true), countWinningChance(candidateBoard, false))
  }
}
