import emotion.react.css
import react.*
import react.dom.html.ReactHTML.div
import web.cssom.Display
import web.cssom.FlexDirection
import kotlin.math.ceil

const val cellsPerRow = 50

external interface GridProps: Props {
  var downloadAtlas: DownloadAtlas
  var cellLength: Int
}

val Grid = FC<GridProps> { props ->
  div {
    css {
      display = Display.flex
      flexDirection = FlexDirection.column
    }
    repeat(ceil(props.downloadAtlas.length.toFloat() / (cellsPerRow * props.cellLength)).toInt()) { row ->
      div {
        css {
          display = Display.flex
        }
        repeat(cellsPerRow) { cell ->
          Cell {
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
