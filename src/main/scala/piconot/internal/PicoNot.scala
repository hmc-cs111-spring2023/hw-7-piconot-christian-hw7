// package piconot.internal

// import picolib._
// import picolib.maze._
// import picolib.semantics._
// import java.io.File

// class Picofun(val mazeFilename: String) extends App {


//     val state = 0

//     def facing(direction: Char): State =
//         direction match {
//             case 'N' => State("N")
//             case 'E' => State("E")
//             case 'W' => State("W")
//             case 'S' => State("S")
//         }

//     def createSurroundings(directions: Array[RelativeDescription]): Surroundings =
//         Surroundings(directions(0), directions(1), directions(2), directions(3))

//     def walls(direction: Char): Surroundings =
//             direction match {
//                 case 'N' => Surroundings(Open, Anything, Anything, Anything)
//                 case 'E' => Surroundings(Anything, Open, Anything, Anything)
//                 case 'W' => Surroundings(Anything, Anything, Open, Anything)
//                 case 'S' => Surroundings(Anything, Anything, Anything, Open)
//             }

//     def blocked(direction: Char): Surroundings =
//         direction match {
//             case 'N' => Surroundings(Blocked, Anything, Anything, Anything)
//             case 'E' => Surroundings(Anything, Blocked, Anything, Anything)
//             case 'W' => Surroundings(Anything, Anything, Blocked, Anything)
//             case 'S' => Surroundings(Anything, Anything, Anything, Blocked)
//         }

//     def move(direction: Char): MoveDirection = 
//         direction match {
//             case 'N' => North
//             case 'E' => East
//             case 'W' => West
//             case 'S' => South
//         }

//     // def moving(move: Char):
    
//     // def open(direction: Char): Surroundings =
//     //     direction match {
//     //         case 'N' => Surroundings(Open, Anything, Anything, Anything)
//     //         case 'E' => Surroundings(Anything, Open, Anything, Anything)
//     //         case 'W' => Surroundings(Anything, Anything, Open, Anything)
//     //         case 'S' => Surroundings(Anything, Anything, Anything, Open)
//     //     }
//     def ruleConstructor(face_0: Char, body: Array[String], face_final: Char): Rule =
//         val wallsFOR: Array[RelativeDescription] = Array(Anything, Anything, Anything, Anything)
//         var m: MoveDirection = StayHere
//         for (e <- body) {
//             val Array(instruction, directions) = e.split(" ")
//             instruction match {
//                 case "blocked" => 
//                     for (dir <- directions) {
//                         dir match {
//                             case 'N' => wallsFOR(0) = Blocked
//                             case 'E' => wallsFOR(1) = Blocked
//                             case 'W' => wallsFOR(2) = Blocked
//                             case 'S' => wallsFOR(3) = Blocked
//                         }
//                     }
//                 case "open" => 
//                     for (dir <- directions) {
//                         dir match {
//                             case 'N' => wallsFOR(0) = Open
//                             case 'E' => wallsFOR(1) = Open
//                             case 'W' => wallsFOR(2) = Open
//                             case 'S' => wallsFOR(3) = Open
//                         }
//                     }
//                 case "move" =>
//                     directions match {
//                         case "N" => m = North
//                         case "E" => m = East
//                         case "W" => m = West
//                         case "S" => m = South
//                     }
//                 // case _ => SOME ERROR
//             }
//         }
//         Rule(
//             facing(face_0),
//             createSurroundings(wallsFOR),
//             m,
//             facing(face_final)
//         )

//     // Need to distinguish between following two, they'll depend on body if face and/or move are called.
//     def ruleConstructor1(face_0: Char, open: Char, moveDir: Char): Rule =
//         Rule(
//             facing(face_0),
//             walls(open),
//             move(moveDir),
//             facing(face_0)
//         )

//     def ruleConstructor2(face_0: Char, open: Char, face_final: Char): Rule =
//         Rule(
//             facing(face_0),
//             walls(open),
//             StayHere,
//             facing(face_final)
//         )
//     def ruleConstructorBlocked2(face_0: Char, b: Char, face_final: Char): Rule =
//         Rule(
//             facing(face_0),
//             blocked(b),
//             StayHere,
//             facing(face_final)
//         )

//     def ruleConstructor3(face_0: Char, open: Char): Rule =
//         Rule(
//             facing(face_0),
//             walls(open),
//             StayHere,
//             facing(face_0)
//         )

        

//     def rule1(face_0: Char, open: Char, moveDir: Char, face_final: Char): Rule =
//         Rule(
//             facing(face_0),
//             walls(open),
//             move(moveDir),
//             facing(face_final)
//         )
//         // Rule(
//         //     State("0"),
//         //     Surroundings(Anything, Anything, Anything, Anything),
//         //     StayHere,
//         //     State("W")
//         // )

//     def rule2(): Rule = 
//         ruleConstructorBlocked2('W', 'W', 'N')

//     def rule3(): Rule =
//         ruleConstructor1('N', 'N', 'N')

//     def rule4() =
//         ruleConstructor('N', Array("blocked N", "open S", "move S"), 'S')
//         // Rule(
//         //     State("N"),
//         //     Surroundings(Blocked, Anything, Anything, Open),
//         //     South,
//         //     State("S")
//         // )
    
//     def rule5(): Rule =
//         Rule(
//             State("S"),
//             Surroundings(Anything, Anything, Anything, Open),
//             South,
//             State("S")
//         )
    
//     def rule6(): Rule = 
//         Rule(
//             State("S"),
//             Surroundings(Anything, Open, Anything, Blocked),
//             East,
//             State("E")
//         )
    
//     def rule7(): Rule = 
//         Rule(
//             State("E"),
//             Surroundings(Open, Anything, Anything, Anything),
//             North,
//             State("E")
//         )
//     def rule8(): Rule =
//         Rule(
//             State("E"),
//             Surroundings(Blocked, Open, Anything, Anything),
//             East,
//             State("S")
//         )
// }
// /// imports for calls below 
// import picolib._
// import picolib.maze._
// import picolib.semantics._
// import java.io.File
// ///

// /** This is an intentionally bad internal language, but it uses all the parts of
//   * the picolib library that you might need to implement your language
//   */

// val emptyMaze = Maze("resources" + File.separator + "empty.txt")

// val rules = List(
//   /////////////////////////////////////////////////////////
//   // State 0: go West
//   /////////////////////////////////////////////////////////
//     Rule(
//             State("0"),
//             Surroundings(Anything, Anything, Anything, Anything),
//             StayHere,
//             State("W")
//         ),
//   // as long as West is unblocked, go West
//     Car.rule1('W', 'W', 'W', 'W'),

//   // can't go West anymore, so try to go North (by transitioning to State 1)
//     Car.rule2(),

//     /////////////////////////////////////////////////////////
//     // State 1: go North
//     /////////////////////////////////////////////////////////

//     // as long as North is unblocked, go North
//     Car.rule3(),

//     // can't go North any more, so try to go South (by transitioning to State 2)
//     ruleConstructor('N', Array("blocked N", "open S", "move S"), 'S'),

//     /////////////////////////////////////////////////////////
//     // States 2 & 3: fill from North to South, West to East
//     /////////////////////////////////////////////////////////

//     // State 2: fill this column from North to South
//     Car.rule5(),

//     // can't go South anymore, move one column to the East and go North
//     // (by transitioning to State 3)
//     Car.rule6(),

//     // State 3: fill this column from South to North
//     Car.rule7(),

//     // can't go North anymore, move one column to the East and go South
//     // (by transitioning to State 2)
//     Car.rule8()
// )

// ////////////////////////////////////////////////////////////////////////////////
// // Create and run simulations with the maze and rules defined above
// ////////////////////////////////////////////////////////////////////////////////

// // text-based simulation
// object EmptyText extends TextSimulation(emptyMaze, rules)
