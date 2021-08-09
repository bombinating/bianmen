package dev.bombinating.bianmen.example

import dev.bombinating.bianmen.example.helloworld.HelloWorldApp
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WicketFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import java.util.EnumSet.allOf
import javax.servlet.DispatcherType

@SpringBootApplication
class BianmenExampleApp {

    @Bean
    fun helloWorld() = wicketConfig(::HelloWorldApp, "helloworld")

    private fun wicketConfig(appFactory: () -> WebApplication, path: String) = FilterRegistrationBean(
        WicketFilter(appFactory()).apply { filterPath = path }
    ).apply {
        addUrlPatterns("/$path/*")
        setName(path)
        setDispatcherTypes(allOf(DispatcherType::class.java))
    }

}


fun main() {
    SpringApplication.run(BianmenExampleApp::class.java)
}
