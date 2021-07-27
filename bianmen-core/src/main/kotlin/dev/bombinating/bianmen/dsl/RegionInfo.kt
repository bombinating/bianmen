package dev.bombinating.bianmen.dsl

import org.apache.wicket.Component

/**
 * Specifies the markup and the root tags associated with the markup.
 */
public class RegionInfo(
    /**
     * HTML tags
     */
    public val markup: String,
    /**
     * [Component]s associated with the top-level tags in the markup
     */
    public val roots: List<Component>
)
