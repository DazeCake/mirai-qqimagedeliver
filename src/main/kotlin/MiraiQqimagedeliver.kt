package moe.dazecake

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import moe.dazecake.entity.Sender
import moe.dazecake.utils.ParamsGetter
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.console.util.ContactUtils.getFriendOrGroupOrNull
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.info
import java.util.*

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
        val bots = mutableListOf<Bot>()
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> { bots.add(this.bot) }
        GlobalEventChannel.subscribeAlways<BotOfflineEvent> { bots.remove(this.bot) }

        GlobalEventChannel.subscribeOnce<BotOnlineEvent>{
            event ->
            launch {
                logger.info { "已开启消息推送服务，浏览器访问 http://本机ip:49875/status 获取运行状态" }
                embeddedServer(Netty, port = 49875) {
                    install(ContentNegotiation){
                        gson()
                    }
                    routing {
                        get("/status") {
                            val statusBuilder = StringBuilder()
                            statusBuilder.append("通知服务器正常运行中\n")
                            for (bot in bots) {
                                val status = if (bot.isOnline) "在线" else "掉线，请重新登录或执行重启"
                                statusBuilder.append("当前Bot: ${bot.id}" + "状态: $status\n")
                            }
                            statusBuilder.append("终端填写地址: 本机ip:49875")
                            call.respondText(statusBuilder.toString())
                        }
                        post("/") {
                            val bot = bots.random()
                            val msg = Sender("",0L,"")
                            ParamsGetter.getParameter("?${call.receiveText()}")?.forEach {
                                if (it.key == "image") {
                                    msg.image = it.value.toString()
                                }
                                if (it.key == "to") {
                                    msg.to = it.value.toString().toLong()
                                }
                                if (it.key == "info") {
                                    msg.info = it.value.toString()
                                }

                            }
                            logger.info("消息推送: ${msg.info} ${if (msg.image != "") "有图" else "无图"}")

                            val msgChain:MessageChain = if (msg.image != "") {
                                val img = Base64.getDecoder().decode(msg.image).toExternalResource()
                                buildMessageChain {
                                    +Image(bot.getFriendOrGroupOrNull(msg.to)?.uploadImage(img)!!.imageId)
                                    +PlainText(msg.info)
                                }
                            } else {
                                buildMessageChain{
                                    +PlainText(msg.info)
                                }
                            }

                            bot.getFriendOrGroupOrNull(msg.to)?.sendMessage(msgChain)

                            call.respondText("success")
                        }
                    }
                }.start(wait = true)
            }
        }
    }
}