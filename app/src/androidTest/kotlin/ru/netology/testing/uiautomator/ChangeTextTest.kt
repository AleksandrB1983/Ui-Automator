package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"
    private val twoTextToSet = "     "
    private val newTextToSet = "Rodina"

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testInternetSettings() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
        waitForPackage(SETTINGS_PACKAGE)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(SETTINGS_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(SETTINGS_PACKAGE)), TIMEOUT)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    @Test
    fun testChangeText() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
        waitForPackage(SETTINGS_PACKAGE)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)


        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = textToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val result: UiObject2 = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged"))
        assertEquals(result.text, textToSet)
    }

    @Test
    fun testZeroChangeText() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
        waitForPackage(SETTINGS_PACKAGE)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)


        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = twoTextToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonChange")).click()

        val result: UiObject2 = device.findObject(By.res(MODEL_PACKAGE, "textToBeChanged"))
        assertEquals(result.text, "Hello UiAutomator!")
    }

    @Test
    fun testNewActivityChangeText() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
        waitForPackage(SETTINGS_PACKAGE)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(MODEL_PACKAGE)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(MODEL_PACKAGE)), TIMEOUT)

        device.findObject(By.res(MODEL_PACKAGE, "userInput")).text = newTextToSet
        device.findObject(By.res(MODEL_PACKAGE, "buttonActivity")).click()
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

        val result: UiObject2 = device.findObject(By.res(MODEL_PACKAGE, "text"))
        assertEquals(result.text, newTextToSet)

    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

//    @Before
//    fun beforeEachTest() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//    }

    @Test
    fun testZeroInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    @Test
    fun testOpenChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }

}



