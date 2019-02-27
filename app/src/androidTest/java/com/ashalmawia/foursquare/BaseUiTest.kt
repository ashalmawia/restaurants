package com.ashalmawia.foursquare

import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.runner.RunWith

const val PACKAGE = "com.ashalmawia.foursquare"
const val TIMEOUT = 5000L

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {

    protected lateinit var device: UiDevice

    @Before
    open fun before() {
        device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()
        startLauncher()
        startFourSquareApp()
    }

    private fun startFourSquareApp() {
        val context = applicationContext()
        val intent = context.packageManager.getLaunchIntentForPackage(PACKAGE)!!
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), TIMEOUT)
    }

    private fun startLauncher() {
        val launcherPackageName = getLauncherPackageName()
        assertThat(launcherPackageName, notNullValue())
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), TIMEOUT)
    }

    private fun getLauncherPackageName(): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        val app = applicationContext()
        val info = app.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return info.activityInfo.packageName

    }

    fun applicationContext() = ApplicationProvider.getApplicationContext<TestApplication>()

}