package piconot.internal

import picolib.maze.Maze
import picolib.semantics._
import picolib.display.TextDisplay

import scala.collection.mutable.ListBuffer

/** @author
  *   christian
  */

class Picofun(val mazeFilename: String) extends App {

  // the list of rules, which is built up as the Picofun program executes
  private val rules = ListBuffer.empty[Rule]

  def addRule(rule: Rule) = rules += rule

  def run = {
    val maze = Maze(mazeFilename)
    object bot extends Picobot(maze, rules.toList) with TextDisplay
    bot.run()
  }

  /////////////////////////////////////////////////////////////////////////////
  // Internal DSL definition
  /////////////////////////////////////////////////////////////////////////////

  def ifFacingNorth : Char = 'N'
  def ifFacingEast : Char = 'E'
  def ifFacingWest : Char = 'W'
  def ifFacingSouth : Char = 'S'
  def isOpen(d: Char): Surroundings = 
    d match {
      case 'N' => Surroundings(Open, Anything, Anything, Anything)
      case 'E' => Surroundings(Anything, Open, Anything, Anything)
      case 'W' => Surroundings(Anything, Anything, Open, Anything)
      case 'S' => Surroundings(Anything, Anything, Anything, Open)
      case _ => Surroundings(Anything, Anything, Anything, Anything)
    }

  def isBlocked(d: Char): Surroundings = 
    d match {
      case 'N' => Surroundings(Blocked, Anything, Anything, Anything)
      case 'E' => Surroundings(Anything, Blocked, Anything, Anything)
      case 'W' => Surroundings(Anything, Anything, Blocked, Anything)
      case 'S' => Surroundings(Anything, Anything, Anything, Blocked)
      case _ => Surroundings(Anything, Anything, Anything, Anything)
    }

  extension (s: Surroundings) {
    def and(s2: Surroundings): Surroundings =
      var computedWalls: Array[RelativeDescription] = Array(Anything, Anything, Anything, Anything)
      if (s.north == Blocked || s2.north == Blocked) {
        computedWalls(0) = Blocked
      }
      else if (s.north == Open || s2.north == Open) {
        computedWalls(0) = Open
      }
      if (s.east == Blocked || s2.east == Blocked) {
        computedWalls(1) = Blocked
      }
      else if (s.east == Open || s2.east == Open) {
        computedWalls(1) = Open
      }
      if (s.west == Blocked || s2.west == Blocked) {
        computedWalls(2) = Blocked
      }
      else if (s.west == Open || s2.west == Open) {
        computedWalls(2) = Open
      }
      if (s.south == Blocked || s2.south == Blocked) {
        computedWalls(3) = Blocked
      }
      else if (s.south == Open || s2.south == Open) {
        computedWalls(3) = Open
      }
      Surroundings(computedWalls(0), computedWalls(1), computedWalls(2), computedWalls(3))
  }

  // a class to gather the start state and environment description
  extension (n: Char) {
    def walls(s: Surroundings): RuleBuilder = RuleBuilder(
      State(n.toString),
      s
    )
    def apply(s: Surroundings): RuleBuilder = RuleBuilder(
      State(n.toString),
      s
    )
  }

  // a class to gather the move direction and next state
  class RHSBuilder(val moveDirection: MoveDirection) {
    def apply(nextState: Char): (MoveDirection, State) =
      (moveDirection, State(nextState.toString))
  }

  // internal DSL names for move directions
  object moveNorthAndFace extends RHSBuilder(North)
  object moveEastAndFace extends RHSBuilder(East)
  object moveWestAndFace extends RHSBuilder(West)
  object moveSouthAndFace extends RHSBuilder(South)
  object stayHereAndFace extends RHSBuilder(StayHere)

  // a class to build a rule from its parts and add the rule to the running
  // list of rules in this picobor program
  class RuleBuilder(val startState: State, val surroundings: Surroundings) {
    val program = Picofun.this
    def apply(rhs: (MoveDirection, State)) = {
      val (moveDirection, nextState) = rhs
      val rule = new Rule(startState, surroundings, moveDirection, nextState)
      program.addRule(rule)
    }
  }
}