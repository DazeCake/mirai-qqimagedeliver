package moe.dazecake

import io.ktor.serialization.gson.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.launch
import moe.dazecake.entity.Sender
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.ContactUtils.getFriendOrGroupOrNull
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.util.Base64

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
    @OptIn(ConsoleExperimentalApi::class)
    override fun onEnable() {
        logger.info { "消息推送插件已载入，等待机器人登录..." }

        GlobalEventChannel.subscribeOnce<BotOnlineEvent>{
            event -> val bot = event.bot
            launch {
                logger.info { "已开启消息推送服务，浏览器访问 http://本机ip:49875/status 获取运行状态" }
                embeddedServer(Netty, port = 49875) {
                    install(ContentNegotiation){
                        gson()
                    }
                    routing {
                        get("/status") {
                            call.respondText("通知服务器正常运行中\n" +
                                    "当前Bot: ${bot.id}\n" +
                                    "状态: ${if (bot.isOnline) "在线" else "掉线，请重新登录或执行重启"}" +
                                    "终端填写地址: http://本机ip:49875/")
                        }
                        post("/") {
                            val msg = (if (call.parameters["image"]!=null) call.parameters["image"] else "")?.let { it1 ->
                                Sender(
                                    it1,
                                    call.parameters["to"]!!.toLong(),
                                    call.parameters["info"]!!)
                            }

                            val msgChain:MessageChain = if (msg?.image != "") {
                                val img = Base64.getDecoder().decode(msg?.image).toExternalResource()
                                buildMessageChain {
                                    +Image(bot.getFriendOrGroupOrNull(msg!!.to)?.uploadImage(img)!!.imageId)
                                    +PlainText(msg.info)
                                }
                            } else {
                                buildMessageChain{
                                    +PlainText(msg.info)
                                }
                            }

                            bot.getFriendOrGroupOrNull(msg!!.to)?.sendMessage(msgChain)

                            call.respondText("success")
                        }
                    }
                }.start(wait = true)
            }
        }
    }
}