# tic-tac-toe-scala
[Tic Tac Toe](https://en.wikipedia.org/wiki/Tic-tac-toe) implemented in Scala

### Build

```
sbt clean assembly
```

### Run

```
$ java -cp target/scala-2.10/tic-tac-toe-scala-assembly-1.0.jar com.debugger87.tictactoe.performance.PerformanceSystem
loaded model from file: 26.789046490793943,-8.196950584570022,18.632860527618167,-9.223599223967508
Usage: the computer use 'X', and you use 'O'. '-' represents blank space.
Initializing board...
Computer plays this:
X - O
- - -
X - -
1 0
Your play:
X - O
O - -
X - -
Computer plays this:
X - O
O - -
X - X
1 1
Your play:
X - O
O O -
X - X
Computer plays this:
X - O
O O -
X X X
Computer wins!!!

updated model:37.15085052145653,-3.2289523404020875,12.91192597753557,-3.6662290556788824
Enter into another game! Notice: the computer will be smarter!!!
Initializing board...
- - O
- - -
X - -
Computer plays this:
X - O
- - -
X - -
```
