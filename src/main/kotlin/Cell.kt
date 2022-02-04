import kotlinx.css.flex
import react.*
import styled.css
import styled.styledDiv

external interface CellProps: Props {
  var downloadAtlas: DownloadAtlas
  var subrange: ClosedRange<Int>?
  var endPadLength: Int?
}

@JsExport
class Cell(props: CellProps): RComponent<CellProps, State>(props) {
  override fun RBuilder.render() {
    styledDiv {
      css { +Styles.cellContainer }
      styledDiv {
        css { +Styles.cell }
        props.subrange?.let { subrange ->
          props.downloadAtlas.subrangesIterator(subrange).asSequence().toList().map { (subrange, state) ->
            styledDiv {
              css {
                +when (state) {
                  DownloadState.WAITING -> Styles.cellSegmentWaiting
                  DownloadState.DOWNLOADED -> Styles.cellSegmentDownloaded
                }
                flex((1 + subrange.endInclusive - subrange.start).toDouble())
              }
            }
          }
          props.endPadLength?.let { endPadLength ->
            styledDiv {
              css {
                flex(endPadLength.toDouble())
              }
            }
          }
        }
      }
    }
  }
}
