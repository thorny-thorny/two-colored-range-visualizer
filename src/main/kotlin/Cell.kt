import kotlinx.css.flex
import me.thorny.twoColoredRange.RedBlackIntRange
import react.*
import styled.css
import styled.styledDiv

external interface CellProps: Props {
  var range: RedBlackIntRange
  var segmentRange: ClosedRange<Int>?
  var endPadLength: Int?
}

@JsExport
class Cell(props: CellProps): RComponent<CellProps, State>(props) {
  override fun RBuilder.render() {
    styledDiv {
      css { +Styles.cellContainer }
      styledDiv {
        css { +Styles.cell }
        if (props.segmentRange != null) {
          props.range.subrangesIterator(props.segmentRange!!).asSequence().toList().map { (subrange, color) ->
            styledDiv {
              css {
                +when (color) {
                  props.range.defaultColor -> Styles.cellSegmentEmpty
                  else -> Styles.cellSegmentFull
                }
                flex((1 + subrange.endInclusive - subrange.start).toDouble())
              }
            }
          }
          if (props.endPadLength != null) {
            styledDiv {
              css {
                flex(props.endPadLength!!.toDouble())
              }
            }
          }
        }
      }
    }
  }
}
