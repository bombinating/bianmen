package dev.bombinating.bianmen.example.helloworld

import dev.bombinating.bianmen.ModelExt.unaryPlus
import dev.bombinating.bianmen.example.BianmenExamplePage
import dev.bombinating.bianmen.factory.LabelFactory.label

class HelloWorldPage : BianmenExamplePage() {
    init {
        add(label(id = "message", model = +"Hello World!"))
    }
}
