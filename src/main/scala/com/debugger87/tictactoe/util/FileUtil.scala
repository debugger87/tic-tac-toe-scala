package com.debugger87.tictactoe.util

import java.io.{BufferedReader, PrintWriter, FileReader, File}

/**
 * Created by yangchaozhong on 7/14/15.
 */
object FileUtil {
  def loadModel = {
    try {
      val reader = new BufferedReader(new FileReader(new File("src/main/resources/model.txt")))
      val line = reader.readLine()
      reader.close()
      Some(line.split(",").map(_.toDouble))
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        None
    }
  }

  def saveModel(s: String) = {
    try {
      val pw = new PrintWriter(new File("src/main/resources/model.txt"))
      try pw.write(s) finally pw.close()
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
