package piconot.internal

import scala.language.postfixOps

object RightHand extends Picobor("resources/maze.txt") {

  // A picobot program that can solve a maze, using the right-hand rule

  // State 0: moving north
  (ifFacing('N') walls(isOpen('E'))) thenGo moveEastAndFace('E') // we can thenGo right (to the east)
  (ifFacing('N') walls(isOpen('N') and isBlocked('E'))) thenGo moveNorthAndFace('N') // we can't thenGo right, then try going forward (north)
  (ifFacing('N') walls(isBlocked('N') and isBlocked('E'))) thenGo stayHereAndFace('S') // we can't thenGo right or forward, try south

  // State 1: moving east
  (ifFacing('E') walls(isOpen('S'))) thenGo moveSouthAndFace('S') // we can thenGo right (to the south)
  (ifFacing('E') walls(isOpen('E') and isBlocked('S'))) thenGo moveEastAndFace('E') // we can't thenGo right, then try going forward (east)
  (ifFacing('E') walls(isBlocked('E') and isBlocked('S'))) thenGo stayHereAndFace('W') // we can't thenGo right or forward, try west

  // State 2: moving west
  (ifFacing('W') walls(isOpen('N'))) thenGo moveNorthAndFace('N') // we can thenGo right (to the north)
  (ifFacing('W') walls(isBlocked('N') and isOpen('W'))) thenGo moveWestAndFace('W') // we can't thenGo right, then try going forward (west)
  (ifFacing('W') walls(isBlocked('N') and isBlocked('W'))) thenGo stayHereAndFace('E') // we can't thenGo right or forward, try east

  // State 3: moving south
  (ifFacing('S') walls(isOpen('W'))) thenGo moveWestAndFace('W') // we can thenGo right (to the west)
  (ifFacing('S') walls(isBlocked('W') and isOpen('S'))) thenGo moveSouthAndFace('S') // we can't thenGo right, then try going forward (south)
  (ifFacing('S') walls(isBlocked('W') and isBlocked('S'))) thenGo stayHereAndFace('N') // we can't thenGo right or forward, try north

  run
}
