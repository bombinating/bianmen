package dev.bombinating.bianmen.example

import org.apache.wicket.devutils.debugbar.DebugBar
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.markup.html.panel.Panel
import dev.bombinating.bianmen.ResourceExt.div

class BianmenExampleHeader(id: String) : Panel(id) {
    init {
        renderBodyOnly = true
        add(Image("exampleheaderimage", BianmenExamplePage::class / "logo-apachewicket-examples-white.svg"))
        add(DebugBar("debug"))
    }
}
