package piconot.internal

// A Picobot program that can fill an empty room
object EmptyBot extends Picofun("resources/empty.txt") {

  // States 0 and 1: move to the top left

  // State 0: move left
// if (facingWest) {
//   if (isOpen('W') and isBlocked('S')) {
//     moveWestAndFace('W')
//   }
// }

// (ifFacingWest {
//   (walls(isOpen('W') and isBlocked('S'))) {
//     moveWestAndFace('W')
//   }
// })

  (ifFacingWest {
      isOpen('W')
    }
  ) {
    moveWestAndFace('W')
  } // thenGo all the way to the left

  (ifFacingWest {isBlocked('W')}) {
    stayHereAndFace('N')
  } // can't thenGo left anymore, so try to thenGo up

  // State 1: move up
  (ifFacingNorth {isOpen('N')}) {
    moveNorthAndFace('N')
  } // thenGo all the way to the top
  
  (ifFacingNorth walls(isBlocked('N') and isOpen('S'))) {
    moveSouthAndFace('S')
  } // can't thenGo up any more, so try to thenGo down

  // States 2 and 3: fill from top to bottom, left to right

  // State 2: fill this column to the bottom
  (ifFacingSouth walls(isOpen('S'))) {
    moveSouthAndFace('S')
  } // thenGo all the way to the bottom
  
  (ifFacingSouth walls(isOpen('E') and isBlocked('S'))) {
    moveEastAndFace('E')
  } // can't thenGo down anymore, so try to thenGo right

  // State 3: fill this column to the top
  (ifFacingEast walls(isOpen('N'))) {
    moveNorthAndFace('E')
  } // thenGo all the way to the top
  
  (ifFacingEast walls(isBlocked('N') and isOpen('E'))) {
    moveEastAndFace('S')
  } // can't thenGo up anymore, so try to thenGo right

  run
}