package dev.bombinating.bianmen.example

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.request.cycle.IRequestCycleListener
import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.request.http.WebResponse
import org.apache.wicket.resource.CssUrlReplacer
import java.lang.System.currentTimeMillis
import kotlin.reflect.KClass

abstract class BianmenExampleWicketApp : WebApplication() {

    protected abstract val home: KClass<out Page>

    override fun getHomePage(): Class<out Page> = home.java

    override fun init() {
        super.init()
        debugSettings.isDevelopmentUtilitiesEnabled = true
        resourceSettings.cssCompressor = CssUrlReplacer()
        requestCycleListeners.add(object : IRequestCycleListener {
            override fun onEndRequest(cycle: RequestCycle) {
                (cycle.response as? WebResponse)?.addHeader("Server-Timing",
                """server;desc="Wicket rendering time";dur=${currentTimeMillis() - cycle.startTime}""")
            }
        })
    }
}
