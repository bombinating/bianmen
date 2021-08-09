package dev.bombinating.bianmen.example.helloworld

import dev.bombinating.bianmen.example.BianmenExampleWicketApp
import org.apache.wicket.Page
import kotlin.reflect.KClass

class HelloWorldApp : BianmenExampleWicketApp() {
    override val home: KClass<out Page> = HelloWorldPage::class
}
