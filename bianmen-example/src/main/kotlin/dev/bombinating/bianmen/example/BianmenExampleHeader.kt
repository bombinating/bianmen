package dev.bombinating.bianmen.example

import org.apache.wicket.devutils.debugbar.DebugBar
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.request.resource.PackageResourceReference

class BianmenExampleHeader(id: String) : Panel(id) {
    init {
        renderBodyOnly = true
        add(
            Image(
                "exampleheaderimage",
                PackageResourceReference(BianmenExamplePage::class.java, "logo-apachewicket-examples-white.svg")
            )
        )
        add(DebugBar("debug"))
    }
}
