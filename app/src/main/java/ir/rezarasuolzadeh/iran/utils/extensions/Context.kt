package ir.rezarasuolzadeh.iran.utils.extensions

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import ir.rezarasuolzadeh.iran.constant.Constants

fun Context.openGithubRepository() {
    val intent = Intent(Intent.ACTION_VIEW, Constants.GITHUB_URL.toUri())
    startActivity(intent)
}