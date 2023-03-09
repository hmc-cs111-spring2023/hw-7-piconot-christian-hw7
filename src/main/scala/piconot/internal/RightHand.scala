package piconot.internal

import scala.language.postfixOps

object RightHand extends Picofun("resources/maze.txt") {

  // A picobot program that can solve a maze, using the right-hand rule

  // State 0: moving north
  (ifFacingNorth walls(isOpen('E'))) {
    moveEastAndFace('E') 
  } // we can thenGo right (to the east)

  (ifFacingNorth walls(isOpen('N') and isBlocked('E'))) {
    moveNorthAndFace('N') 
  } // we can't thenGo right, then try going forward (north)
  
  (ifFacingNorth walls(isBlocked('N') and isBlocked('E'))) {
    stayHereAndFace('S')
  } // we can't thenGo right or forward, try south

  // State 1: moving east
  (ifFacingEast walls(isOpen('S'))) {
    moveSouthAndFace('S')
  } // we can thenGo right (to the south)
  
  (ifFacingEast walls(isOpen('E') and isBlocked('S'))) {
    moveEastAndFace('E')
  } // we can't thenGo right, then try going forward (east)

  (ifFacingEast walls(isBlocked('E') and isBlocked('S'))) {
    stayHereAndFace('W')
  } // we can't thenGo right or forward, try west

  // State 2: moving west
  (ifFacingWest walls(isOpen('N'))) {
    moveNorthAndFace('N')
  } // we can thenGo right (to the north)

  (ifFacingWest walls(isBlocked('N') and isOpen('W'))) {
    moveWestAndFace('W')
  } // we can't thenGo right, then try going forward (west)

  (ifFacingWest walls(isBlocked('N') and isBlocked('W'))) {
    stayHereAndFace('E')
  } // we can't thenGo right or forward, try east

  // State 3: moving south
  (ifFacingSouth walls(isOpen('W'))) { 
    moveWestAndFace('W') 
  } // we can thenGo right (to the west)
  
  (ifFacingSouth walls(isBlocked('W') and isOpen('S'))) { 
    moveSouthAndFace('S')
  } // we can't thenGo right, then try going forward (south)

  (ifFacingSouth walls(isBlocked('W') and isBlocked('S'))) {
    stayHereAndFace('N')
  } // we can't thenGo right or forward, try north

  run
}
