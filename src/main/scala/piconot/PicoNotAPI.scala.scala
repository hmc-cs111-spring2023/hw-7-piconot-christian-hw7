
package piconot.internal
object Car {
    import picolib._
    import picolib.maze._
    import picolib.semantics._
    import java.io.File

    val state = 0

    def facing(direction: Char): State =
        direction match {
            case 'N' => State("0")
            case 'E' => State("1")
            case 'W' => State("2")
            case 'S' => State("3")
        }

    def walls(direction: Char): Surroundings =
        direction match {
            case 'N' => Surroundings(Open, Anything, Anything, Anything)
            case 'E' => Surroundings(Anything, Open, Anything, Anything)
            case 'W' => Surroundings(Anything, Anything, Open, Anything)
            case 'S' => Surroundings(Anything, Anything, Anything, Open)
        }

    def move(direction: Char): MoveDirection = 
        direction match {
            case 'N' => North
            case 'E' => East
            case 'W' => West
            case 'S' => South
        }

    // def moving(move: Char):
    
    // def open(direction: Char): Surroundings =
    //     direction match {
    //         case 'N' => Surroundings(Open, Anything, Anything, Anything)
    //         case 'E' => Surroundings(Anything, Open, Anything, Anything)
    //         case 'W' => Surroundings(Anything, Anything, Open, Anything)
    //         case 'S' => Surroundings(Anything, Anything, Anything, Open)
    //     }
    def ruleConstructor(face_0: Char, open: Char, moveDir: Char, face_final: Char): Rule =
        Rule(
            facing(face_0),
            walls(open),
            move(moveDir),
            facing(face_final)
        )

    // Need to distinguish between following two, they'll depend on body if face and/or move are called.
    def ruleConstructor1(face_0: Char, open: Char, moveDir: Char): Rule =
        Rule(
            facing(face_0),
            walls(open),
            move(moveDir),
            facing(face_0)
        )

    def ruleConstructor2(face_0: Char, open: Char, face_final: Char): Rule =
        Rule(
            facing(face_0),
            walls(open),
            StayHere,
            facing(face_final)
        )

    def ruleConstructor3(face_0: Char, open: Char): Rule =
        Rule(
            facing(face_0),
            walls(open),
            StayHere,
            facing(face_0)
        )

        

    def rule1(face_0: Char, open: Char, moveDir: Char, face_final: Char): Rule =
        Rule(
            facing(face_0),
            walls(open),
            move(moveDir),
            facing(face_final)
        )
        // Rule(
        //     State("0"),
        //     Surroundings(Anything, Anything, Open, Anything),
        //     West,
        //     State("0")
        // )

    def rule2(): Rule = 
        Rule(
            State("0"),
            Surroundings(Anything, Anything, Blocked, Anything),
            StayHere,
            State("1")
        )

    def rule3(): Rule =
        Rule(
            State("1"),
            Surroundings(Open, Anything, Anything, Anything),
            North,
            State("1")
        )

    def rule4(): Rule =
        Rule(
            State("1"),
            Surroundings(Blocked, Anything, Anything, Open),
            South,
            State("2")
        )
    
    def rule5(): Rule =
        Rule(
            State("2"),
            Surroundings(Anything, Anything, Anything, Open),
            South,
            State("2")
        )
    
    def rule6(): Rule = 
        Rule(
            State("2"),
            Surroundings(Anything, Open, Anything, Blocked),
            East,
            State("3")
        )
    
    def rule7(): Rule = 
        Rule(
            State("3"),
            Surroundings(Open, Anything, Anything, Anything),
            North,
            State("3")
        )
    def rule8(): Rule =
        Rule(
            State("3"),
            Surroundings(Blocked, Open, Anything, Anything),
            East,
            State("2")
        )
}
/// imports for calls below 
import picolib._
import picolib.maze._
import picolib.semantics._
import java.io.File
///

/** This is an intentionally bad internal language, but it uses all the parts of
  * the picolib library that you might need to implement your language
  */

val emptyMaze = Maze("resources" + File.separator + "empty.txt")

val rules = List(
  /////////////////////////////////////////////////////////
  // State 0: go West
  /////////////////////////////////////////////////////////

  // as long as West is unblocked, go West
    Car.rule1('N', 'W', 'W', 'N'),
            // Rule(
        //     State("0"),
        //     Surroundings(Anything, Anything, Open, Anything),
        //     West,
        //     State("0")
        // )

  // can't go West anymore, so try to go North (by transitioning to State 1)
    Car.rule2(),

    /////////////////////////////////////////////////////////
    // State 1: go North
    /////////////////////////////////////////////////////////

    // as long as North is unblocked, go North
    Car.rule3(),

    // can't go North any more, so try to go South (by transitioning to State 2)
    Car.rule4(),

    /////////////////////////////////////////////////////////
    // States 2 & 3: fill from North to South, West to East
    /////////////////////////////////////////////////////////

    // State 2: fill this column from North to South
    Car.rule5(),

    // can't go South anymore, move one column to the East and go North
    // (by transitioning to State 3)
    Car.rule6(),

    // State 3: fill this column from South to North
    Car.rule7(),

    // can't go North anymore, move one column to the East and go South
    // (by transitioning to State 2)
    Car.rule8()
)

////////////////////////////////////////////////////////////////////////////////
// Create and run simulations with the maze and rules defined above
////////////////////////////////////////////////////////////////////////////////

// text-based simulation
object EmptyText extends TextSimulation(emptyMaze, rules)
