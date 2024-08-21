import react.create
import react.dom.client.createRoot

fun main() {
  createRoot(web.dom.document.getElementById("root")!!).render(Page.create())
}
