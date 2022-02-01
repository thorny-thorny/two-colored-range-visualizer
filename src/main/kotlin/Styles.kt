import kotlinx.css.*
import styled.StyleSheet

object Styles : StyleSheet("Styles", isStatic = true) {
  val clear by css {
    clear = Clear.both
  }

  val button by css {
    margin(5.px)
  }

  val grid by css {
    display = Display.flex
    flexDirection = FlexDirection.column
  }

  val row by css {
    display = Display.flex
  }

  val cellContainer by css {
    flex(1.0)
    height = LinearDimension.auto
    display = Display.flex

    before {
      content = QuotedString("")
      float = kotlinx.css.Float.left
      padding(top = 100.pct)
    }
  }

  val cell by css {
    width = 90.pct
    height = 90.pct
    display = Display.flex
  }

  val cellSegmentEmpty by css {
    backgroundColor = Color("#007931")
  }

  val cellSegmentFull by css {
    backgroundColor = Color("#00DF5B")
  }
} 
