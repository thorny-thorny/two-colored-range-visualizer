import me.thorny.twoColoredRange.RedGreenIntArrayRange
import react.*
import kotlinx.browser.window
import kotlinx.css.h3
import kotlinx.html.InputType
import kotlinx.html.for_
import kotlinx.html.id
import react.dom.*
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput

data class PageState(
  val range: RedGreenIntArrayRange,
): State

@JsExport
class Page: RComponent<Props, PageState>() {
  init {
    state = PageState(
      RedGreenIntArrayRange(1..2795)
    )
  }

  private fun startFillingRandomSubrange() {
    val otherColorSubranges = state.range.getSubrangesOfDefaultColor()
    val randomSubrange = otherColorSubranges.randomOrNull()
    if (randomSubrange != null) {
      val start = (randomSubrange.start..randomSubrange.endInclusive).random()
      val length = minOf((1..10).random() - 1, randomSubrange.endInclusive - start)
      state.range.setSubrangeOtherColor(start..(start + length))
      setState { }

      window.setTimeout({ startFillingRandomSubrange() }, 50)
    }
  }

  override fun RBuilder.render() {
    h1 {
      +"Two colored range visualizer"
    }
    label {
      attrs {
        htmlFor = "length"
      }
      +"Range length: "
    }
    styledInput {
      attrs {
        id = "length"
        type = InputType.text
      }
    }
    styledDiv {
      css { +Styles.clear }
    }
    styledButton {
      css { +Styles.button }
      attrs {
        onClick = {
          startFillingRandomSubrange()
        }
      }
      +"Start filling"
    }
    styledButton {
      css { +Styles.button }
      attrs {
        onClick = {
          startFillingRandomSubrange()
        }
      }
      +"Reset"
    }
    child(Grid::class) {
      attrs {
        range = state.range
      }
    }
  }
}
