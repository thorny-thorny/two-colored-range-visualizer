import kotlinx.css.*
import styled.StyleSheet

object Styles : StyleSheet("Styles", isStatic = true) {
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

  val cellSegmentWaiting by css {
    backgroundColor = Color("#007931")
  }

  val cellSegmentDownloaded by css {
    backgroundColor = Color("#00DF5B")
  }
} 
