package com.feelfree.ctb.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.feelfree.ctb.CatchTheBlocks

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(CatchTheBlocks(), config)
    }
}
