import react.*
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul
import web.cssom.ClassName
import web.html.ButtonType
import web.html.InputType
import web.timers.Timeout
import web.timers.clearTimeout
import web.timers.setTimeout

val validFileLength = 1..10000
val validCellLength = 5..20

data class PageState(
  var downloadAtlas: DownloadAtlas,
  var cellLength: Int,
  var downloadTimeoutId: Timeout?,
)

val Page = FC {
  val (state, setState) = useState {
    PageState(
      DownloadAtlas(validFileLength.last),
      validCellLength.last,
      null,
    )
  }

  val setFileSize = useCallback(*emptyArray()) { size: Int ->
    setState {
      it.copy(downloadAtlas = DownloadAtlas(size))
    }
  }

  val setCellLength = useCallback(*emptyArray()) { length: Int ->
    setState {
      it.copy(cellLength = length)
    }
  }

  fun doDownloadStep() {
    setState {
      val didUpdate = it.downloadAtlas.setRandomAwaitingSubrangeDownloaded(it.cellLength)
      it.copy(
        downloadTimeoutId = when {
          didUpdate -> setTimeout({ doDownloadStep() }, 100)
          else -> null
        }
      )
    }
  }

  val startDownloading = useCallback(*emptyArray()) {
    doDownloadStep()
  }

  val reset = useCallback(*emptyArray()) {
    setState {
      it.downloadTimeoutId?.let { id ->
        clearTimeout(id)
      }

      it.downloadAtlas.clear()
      it.copy(downloadTimeoutId = null)
    }
  }

  val canStart = state.downloadTimeoutId == null && state.downloadAtlas.hasWaitingSubranges()

  div {
    className = ClassName("container")

    h1 {
      +"Two-colored range visualizer"
    }
    p {
      +"This is an example of two-colored range application: tracking file status during partial download."
    }
    div {
      className = ClassName("row")

      div {
        className = ClassName("col-lg-6")

        div {
          className = ClassName("card mb-3")

          div {
            className = ClassName("card-body")

            form {
              div {
                className = ClassName("mb-3")

                label {
                  htmlFor = "file-size"
                  className = ClassName("form-label")
                  +"File size: ${state.downloadAtlas.length} bytes "
                }
                input {
                  id = "file-size"
                  type = InputType.range
                  className = ClassName("form-range")
                  min = validFileLength.first.toString()
                  max = validFileLength.last.toString()
                  value = state.downloadAtlas.length.toString()
                  disabled = !canStart
                  onChange = {
                    setFileSize(it.target.value.toInt())
                  }
                }
              }
              div {
                className = ClassName("mb-3")

                label {
                  htmlFor = "cell-length"
                  className = ClassName("form-label")
                  +"Cell length: ${state.cellLength} bytes"
                }
                input {
                  id = "cell-length"
                  type = InputType.range
                  className = ClassName("form-range")
                  min = validCellLength.first.toString()
                  max = validCellLength.last.toString()
                  value = state.cellLength.toString()
                  disabled = !canStart
                  onChange = {
                    setCellLength(it.target.value.toInt())
                  }
                }
              }
              button {
                type = ButtonType.button
                className = ClassName("btn btn-primary")
                onClick = { startDownloading() }
                disabled = !canStart
                +"Start download simulation"
              }
              button {
                type = ButtonType.button
                className = ClassName("btn btn-primary ms-2")
                onClick = { reset() }
                +"Reset"
              }
            }
          }
        }
      }
    }

    div {
      className = ClassName("card mb-3")

      div {
        className = ClassName("card-body")

        Grid {
          downloadAtlas = state.downloadAtlas
          cellLength = state.cellLength
        }
      }
    }

    p {
      +"Links:"
    }
    ul {
      li {
        a {
          href = "https://two-colored-range.thorny.me"
          +"Project page"
        }
      }
      li {
        a {
          href = "https://github.com/thorny-thorny/two-colored-range-visualizer"
          +"Source code"
        }
      }
    }
  }
}
