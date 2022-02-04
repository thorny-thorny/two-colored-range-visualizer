import react.*
import kotlinx.browser.window
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.id
import org.w3c.dom.HTMLInputElement
import react.dom.*

val validFileLength = 1..10000
val validCellLength = 5..20

data class PageState(
  var downloadAtlas: DownloadAtlas,
  var cellLength: Int,
  var downloadTimeoutId: Int?,
): State

@JsExport
class Page: RComponent<Props, PageState>() {
  init {
    state = PageState(
      DownloadAtlas(validFileLength.last),
      validCellLength.last,
      null,
    )
  }

  private fun setFileSize(size: Int) {
    setState {
      downloadAtlas = DownloadAtlas(size)
    }
  }

  private fun setCellLength(length: Int) {
    setState {
      cellLength = length
    }
  }

  private fun startDownloading() {
    doDownloadStep()
  }

  private fun doDownloadStep() {
    setState {
      val didUpdate = downloadAtlas.setRandomAwaitingSubrangeDownloaded(cellLength)
      downloadTimeoutId = when {
        didUpdate -> window.setTimeout({ doDownloadStep() }, 100)
        else -> null
      }
    }
  }

  private fun reset() {
    setState {
      downloadTimeoutId?.let {
        window.clearTimeout(it)
        downloadTimeoutId = null
      }

      downloadAtlas.clear()
    }
  }

  override fun RBuilder.render() {
    val canStart = state.downloadTimeoutId == null && state.downloadAtlas.hasWaitingSubranges()
    div {
      attrs { classes = setOf("container") }

      h1 {
        +"Two-colored range visualizer"
      }
      p {
        +"This is an example of two-colored range application: tracking file status during partial download."
      }
      div {
        attrs { classes = setOf("row") }

        div {
          attrs { classes = setOf("col-lg-6") }

          div {
            attrs { classes = setOf("card", "mb-3") }

            div {
              attrs { classes = setOf("card-body") }

              form {
                div {
                  attrs { classes = setOf("mb-3") }

                  label {
                    attrs {
                      htmlFor = "file-size"
                      classes = setOf("form-label")
                    }
                    +"File size: ${state.downloadAtlas.length} bytes "
                  }
                  input {
                    attrs {
                      id = "file-size"
                      type = InputType.range
                      classes = setOf("form-range")
                      min = validFileLength.first.toString()
                      max = validFileLength.last.toString()
                      value = state.downloadAtlas.length.toString()
                      disabled = !canStart
                      onChange = {
                        setFileSize((it.target as HTMLInputElement).value.toInt())
                      }
                    }
                  }
                }
                div {
                  attrs { classes = setOf("mb-3") }

                  label {
                    attrs {
                      htmlFor = "cell-length"
                      classes = setOf("form-label")
                    }
                    +"Cell length: ${state.cellLength} bytes"
                  }
                  input {
                    attrs {
                      id = "cell-length"
                      type = InputType.range
                      classes = setOf("form-range")
                      min = validCellLength.first.toString()
                      max = validCellLength.last.toString()
                      value = state.cellLength.toString()
                      disabled = !canStart
                      onChange = {
                        setCellLength((it.target as HTMLInputElement).value.toInt())
                      }
                    }
                  }
                }
                button {
                  attrs {
                    type = ButtonType.button
                    classes = setOf("btn", "btn-primary")
                    onClick = { startDownloading() }
                    disabled = !canStart
                  }
                  +"Start download simulation"
                }
                button {
                  attrs {
                    type = ButtonType.button
                    classes = setOf("btn", "btn-primary", "ms-2")
                    onClick = { reset() }
                  }
                  +"Reset"
                }
              }
            }
          }
        }
      }

      div {
        attrs { classes = setOf("card", "mb-3") }

        div {
          attrs { classes = setOf("card-body") }

          child(Grid::class) {
            attrs {
              downloadAtlas = state.downloadAtlas
              cellLength = state.cellLength
            }
          }
        }
      }

      p {
        +"Links:"
      }
      ul {
        li {
          a {
            attrs { href = "https://two-colored-range.thorny.me" }
            +"Project page"
          }
        }
        li {
          a {
            attrs { href = "https://github.com/thorny-thorny/two-colored-range-visualizer" }
            +"Source code"
          }
        }
      }
    }
  }
}
