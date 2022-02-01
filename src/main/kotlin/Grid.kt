import me.thorny.twoColoredRange.RedGreenIntArrayRange
import react.*
import styled.css
import styled.styledDiv
import kotlin.math.ceil

external interface GridProps: Props {
  var range: RedGreenIntArrayRange
}

const val segmentLength = 10
const val cellsPerRow = 50

@JsExport
class Grid(props: GridProps): RComponent<GridProps, State>(props) {
  override fun RBuilder.render() {
    styledDiv {
      css { +Styles.grid }
      repeat(ceil(props.range.length.toFloat() / (cellsPerRow * segmentLength)).toInt()) { row ->
        styledDiv {
          css { +Styles.row }
          repeat(cellsPerRow) { cell ->
            child(Cell::class) {
              attrs {
                range = props.range

                val start = 1 + segmentLength * cell + cellsPerRow * segmentLength * row
                if (start > props.range.range.endInclusive) {
                  segmentRange = null
                } else {
                  val endInclusive = start + segmentLength - 1
                  segmentRange = start..minOf(endInclusive, props.range.range.endInclusive)
                  if (endInclusive > props.range.range.endInclusive) {
                    endPadLength = endInclusive - props.range.range.endInclusive
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
