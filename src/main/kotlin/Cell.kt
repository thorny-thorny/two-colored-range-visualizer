import emotion.react.css
import react.*
import react.dom.html.ReactHTML.div
import web.cssom.*

external interface CellProps: Props {
  var downloadAtlas: DownloadAtlas
  var subrange: ClosedRange<Int>?
  var endPadLength: Int?
}

val Cell = FC<CellProps> { props ->
  div {
    css {
      flex = Auto.auto
      height = Auto.auto
      display = Display.flex

      before {
        content = Content("")
        float = Float.left
        paddingTop = 100.pct
      }
    }
    div {
      css {
        width = 90.pct
        height = 90.pct
        display = Display.flex
      }
      props.subrange?.let { subrange ->
        props.downloadAtlas.subrangesIterator(subrange).asSequence().toList().map { (subrange, state) ->
          div {
            css {
              backgroundColor = when (state) {
                DownloadState.Waiting -> Color("#007931")
                DownloadState.Downloaded -> Color("#00DF5B")
              }
              flex = Flex(grow = number((1 + subrange.endInclusive - subrange.start).toDouble()), basis = Auto.auto)
            }
          }
        }
        props.endPadLength?.let { endPadLength ->
          div {
            css {
              flex = Flex(grow = number(endPadLength.toDouble()), basis = Auto.auto)
            }
          }
        }
      }
    }
  }
}
