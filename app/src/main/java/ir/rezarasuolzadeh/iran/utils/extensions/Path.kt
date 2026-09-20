package ir.rezarasuolzadeh.iran.utils.extensions

import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region

/**
 * Converts this path into a [Region], so it can be used for hit-testing —
 * for example, to check whether a tap or click falls inside the shape.
 */
fun Path.toHitRegion(): Region {
    val bounds = RectF()
    computeBounds(bounds, true)
    return Region().apply {
        setPath(
            this@toHitRegion,
            Region(
                bounds.left.toInt(),
                bounds.top.toInt(),
                bounds.right.toInt() + 1,
                bounds.bottom.toInt() + 1
            )
        )
    }
}