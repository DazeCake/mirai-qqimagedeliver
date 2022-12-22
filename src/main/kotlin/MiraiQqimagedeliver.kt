package moe.dazecake

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object MiraiQqimagedeliver : KotlinPlugin(
    JvmPluginDescription(
        id = "moe.dazecake.mirai-qqimagedeliver",
        name = "mirai-qqimagedeliver",
        version = "0.1.0",
    ) {
        author("DazeCake")
        info("""qqimagedeliver的mirai实现""")
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}