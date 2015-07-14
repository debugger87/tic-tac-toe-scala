package com.debugger87.tictactoe.performance

import com.debugger87.tictactoe.critic.Critic
import com.debugger87.tictactoe.experiment.Generator
import com.debugger87.tictactoe.generalizer.Generalizer
import com.debugger87.tictactoe.util.{FileUtil, BoardUtil}

/**
 * Created by yangchaozhong on 7/14/15.
 */
object PerformanceSystem extends App with BoardUtil {
  import java.util.{Scanner}
  import java.io.{PrintWriter}

  val in = new Scanner(System.in)
  val out = new PrintWriter(System.out)
  var board = Generator.generate

  var history = Array[Array[Array[Char]]]()

  def nextInt = in.nextInt

  def printBoard(candidateBoard: Array[Array[Char]]) = {
    candidateBoard.foreach( arr =>
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
      var j = 0
      while (j < board(i).size) {
        if (board(i)(j) == '-') {
          result :+= (i, j)
        }
        j += 1
      }

      i += 1
    }

    result
  }

  FileUtil.loadModel.foreach(x => {
    Generalizer.parameter = x
    println(s"loaded model from file: ${x.mkString(",")}")
  })

  println("Usage: the computer use 'X', and you use 'O'. '-' represents blank space.")
  println("Initializing board...")
  while (true) {
    if (!isFinalBoard(board)) {
      val blankGrids = search
      val candidateBoards = blankGrids.map( grid =>
        play(grid._1, grid._2, isComputer = true)
      )

      val selectedBoard = candidateBoards.filter(_.isDefined).map( boardOption => {
        val b = boardOption.get
        val s = objectiveFun(b)
        (boardOption, s)
      }).maxBy(_._2)._1

      if (selectedBoard.isDefined) {
        history :+= selectedBoard.get
        board = selectedBoard.get

        println("Computer plays this:")
        printBoard(board)

        if (!isFinalBoard(board)) {
          var x = nextInt
          var y = nextInt
          var userBoard: Option[Array[Array[Char]]] = None
          while (!userBoard.isDefined) {
            userBoard = play(x, y, isComputer = false)
            if (userBoard.isDefined) {
              board = userBoard.get

              println("Your play:")
              printBoard(board)
            } else {
              println("invalid play, please retry!")
              x = nextInt
              y = nextInt
            }
          }
        }
      }
    } else {
      val finalScore = objectiveFun(board)
      if (finalScore == 100.0) {
        println("Computer wins!!!")
      } else if (finalScore == -100.0) {
        println("You are the winner!!!")
      } else {
        println("Game drawn!")
      }

      val sampleData = Critic.completeSampleData(history)
      sampleData.foreach( sample => {
        Generalizer.update(sample)
      })
      FileUtil.saveModel(Generalizer.parameter.mkString(","))

      println()
      println(s"updated model:${Generalizer.parameter.mkString(",")}")
      println("Enter into another game! Notice: the computer will be smarter!!!")
      // clear data
      println("Initializing board...")
      board = Generator.generate
      printBoard(board)
      history = Array[Array[Array[Char]]]()
    }
  }
}
