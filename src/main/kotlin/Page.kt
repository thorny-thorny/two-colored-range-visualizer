import me.thorny.twoColoredRange.RedGreenIntArrayRange
import react.*
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.id
import org.w3c.dom.HTMLInputElement
import react.dom.*
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput

val validRangeLength = 1..10000
val validBlockLength = 5..20

data class PageState(
  var blockLength: Int?,
  val range: RedGreenIntArrayRange,
  var didStart: Boolean,
): State

@JsExport
class Page: RComponent<Props, PageState>() {
  init {
    state = PageState(
      10,
      RedGreenIntArrayRange(1..2795),
      false,
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
//    label {
//      attrs {
//        htmlFor = "range-length"
//      }
//      +"Range length (1..10000): "
//    }
//    styledInput {
//      attrs {
//        id = "range-length"
//        type = InputType.text
//        value = state.range.range.endInclusive.toString()
//        disabled = state.didStart
//      }
//    }
//    styledDiv {
//      css { +Styles.clear }
//    }
//    label {
//      attrs {
//        htmlFor = "block-length"
//      }
//      +"Block length (5..20): "
//    }
//    styledInput {
//      attrs {
//        id = "length"
//        type = InputType.text
//        value = state.blockLength.toString()
//        disabled = state.didStart
//        onChange = {
//          console.log((it.target as HTMLInputElement).value)
//        }
//      }
//    }
//    styledDiv {
//      css { +Styles.clear }
//    }
    styledButton {
      css { +Styles.button }
      attrs {
        onClick = {
          setState {
            didStart = true
          }
          startFillingRandomSubrange()
        }
        disabled = state.didStart
      }
      +"Start filling"
    }
//    styledButton {
//      css { +Styles.button }
//      attrs {
//        onClick = {
//          startFillingRandomSubrange()
//        }
//      }
//      +"Reset"
//    }
    child(Grid::class) {
      attrs {
        range = state.range
      }
    }
  }
}
