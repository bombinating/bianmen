package dev.bombinating.bianmen

import org.apache.wicket.request.resource.PackageResourceReference
import org.apache.wicket.request.resource.ResourceReference
import kotlin.reflect.KClass

public object ResourceExt {

    public operator fun KClass<*>.div(path: String): ResourceReference = PackageResourceReference(java, path)

}
