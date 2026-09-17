package ir.rezarasuolzadeh.iran.extensions

import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region

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