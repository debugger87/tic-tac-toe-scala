package com.debugger87.tictactoe.performance

import com.debugger87.tictactoe.generalizer.Generalizer

/**
 * Created by yangchaozhong on 7/14/15.
 */
object PerformanceSystem extends App {
  import java.util.{Scanner}
  import java.io.{PrintWriter}

  val in = new Scanner(System.in)
  val out = new PrintWriter(System.out)
  var board = Array.fill(3, 3)('-')
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

  var history = Array[Array[Array[Char]]]()

  def nextInt = in.nextInt

  def printBoard = {
    board.foreach( arr =>
      println(arr.mkString(" "))
    )
  }

  def input: (Int, Int) = {
    (nextInt, nextInt)
  }

  def play(x: Int, y: Int, isComputer: Boolean): Option[Array[Array[Char]]] = {
    if (x >=0 && x <=2 && y >=0 && y <=2 && board(x)(y) == '-') {
      val tempBoard = board.map(_.clone())
      if (isComputer) {
        tempBoard(x)(y) = 'X'
      } else {
        tempBoard(x)(y) = 'O'
      }

      Some(tempBoard)
    } else {
      None
    }
  }

  def search: Array[(Int, Int)] = {
    var result = Array[(Int, Int)]()

    var i = 0
    while (i < board.size) {
      val arr = board(i)
      var j = 0
      while (j < arr.size) {
        if (arr(j) == '-') {
          result :+= (i, j)
        }
        j += 1
      }

      i += 1
    }

    result
  }

  /**
   *  - X -
   *  - - x
   *  O - O
   *
   * */
  def countWinningChance(candidateBoard: Array[Array[Char]], isComputer: Boolean) = {
    val flag = if (isComputer) 'X' else 'O'

    lines.count(line =>
      line.count(pos => {
        val x = pos._1
        val y = pos._2

        candidateBoard(x)(y) == flag
      }) == 2 && line.count( pos => {
        val x = pos._1
        val y = pos._2

        candidateBoard(x)(y) == '-'
      }) == 1
    )
  }

  def isFinalBoard = {
    lines.count(line => {
      val str = line.map(pos => {
        val x = pos._1
        val y = pos._2

        board(x)(y)
      }).mkString("")

      str == "XXX" || str == "OOO"
    }) > 0 || board.map(_.count(x => x == '-')).sum == 0
  }

  def objectiveFun(candidateBoard: Array[Array[Char]]) = {
    if (isFinalBoard) {
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
    } else {
      val x1 = candidateBoard.map(_.count(elem => elem == 'X')).sum
      val x2 = candidateBoard.map(_.count(elem => elem == 'O')).sum
      val x3 = countWinningChance(candidateBoard, isComputer = true)
      val x4 = countWinningChance(candidateBoard, isComputer = false)
      val arr = Array(x1, x2, x3, x4)

      Generalizer.w0 + (0 to 3).map(i => Generalizer.parameter(i) * arr(i)).sum
    }
  }

  while (!isFinalBoard) {
    val blankGrids = search
    val candidateBoards = blankGrids.map( grid =>
      play(grid._1, grid._2, isComputer = true)
    )

    val selectedBoard = candidateBoards.filter(_.isDefined).maxBy( boardOption => {
      val b = boardOption.get
      val s = objectiveFun(b)
      s
    })

    if (selectedBoard.isDefined) {
      history :+= selectedBoard.get
      board = selectedBoard.get

      println("Computer play this:")
      printBoard

      if (!isFinalBoard) {
        var x = nextInt
        var y = nextInt
        var userBoard: Option[Array[Array[Char]]] = None
        while (!userBoard.isDefined) {
          userBoard = play(x, y, isComputer = false)
          if (userBoard.isDefined) {
            board = userBoard.get

            println("Your play:")
            printBoard
          } else {
            println("invalid play, please retry!")
            x = nextInt
            y = nextInt
          }
        }
      }
    }
  }

  val finalScore = objectiveFun(board)
  if (finalScore == 100.0) {
    println("Computer wins!!!")
  } else if (finalScore == -100.0) {
    println("You are the winner!!!")
  } else {
    println("Game drawn!")
  }
}
