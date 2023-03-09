package piconot.internal

// A Picobot program that can fill an empty room
object EmptyBot extends Picobor("resources/empty.txt") {

  // States 0 and 1: move to the top left

  // State 0: move left
  (ifFacing('W') walls(isOpen('W'))) thenGo moveWestAndFace('W') // thenGo all the way to the left
  (ifFacing('W') walls(isBlocked('W'))) thenGo stayHereAndFace('N') // can't thenGo left anymore, so try to thenGo up

  // State 1: move up
  (ifFacing('N') walls(isOpen('N'))) thenGo moveNorthAndFace('N') // thenGo all the way to the top
  (ifFacing('N') walls(isBlocked('N') and isOpen('S'))) thenGo moveSouthAndFace('S') // can't thenGo up any more, so try to thenGo down

  // States 2 and 3: fill from top to bottom, left to right

  // State 2: fill this column to the bottom
  (ifFacing('S') walls(isOpen('S'))) thenGo moveSouthAndFace('S') // thenGo all the way to the bottom
  (ifFacing('S') walls(isOpen('E') and isBlocked('S'))) thenGo moveEastAndFace('E') // can't thenGo down anymore, so try to thenGo right

  // State 3: fill this column to the top
  (ifFacing('E') walls(isOpen('N'))) thenGo moveNorthAndFace('E') // thenGo all the way to the top
  (ifFacing('E') walls(isBlocked('N') and isOpen('E'))) thenGo moveEastAndFace('S') // can't thenGo up anymore, so try to thenGo right

  run
}