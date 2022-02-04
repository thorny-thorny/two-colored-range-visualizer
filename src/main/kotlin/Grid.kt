import react.*
import styled.css
import styled.styledDiv
import kotlin.math.ceil

const val cellsPerRow = 50

external interface GridProps: Props {
  var downloadAtlas: DownloadAtlas
  var cellLength: Int
}

@JsExport
class Grid(props: GridProps): RComponent<GridProps, State>(props) {
  override fun RBuilder.render() {
    styledDiv {
      css { +Styles.grid }
      repeat(ceil(props.downloadAtlas.length.toFloat() / (cellsPerRow * props.cellLength)).toInt()) { row ->
        styledDiv {
          css { +Styles.row }
          repeat(cellsPerRow) { cell ->
            child(Cell::class) {
              attrs {
                downloadAtlas = props.downloadAtlas

                val start = 1 + props.cellLength * cell + cellsPerRow * props.cellLength * row
                if (start > props.downloadAtlas.range.endInclusive) {
                  subrange = null
                } else {
                  val endInclusive = start + props.cellLength - 1
                  subrange = start..minOf(endInclusive, props.downloadAtlas.range.endInclusive)
                  if (endInclusive > props.downloadAtlas.range.endInclusive) {
                    endPadLength = endInclusive - props.downloadAtlas.range.endInclusive
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
