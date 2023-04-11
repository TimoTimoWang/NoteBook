package demo1

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.util.CharsetUtil
import java.net.InetSocketAddress


@Sharable
class EchoClientHandler : SimpleChannelInboundHandler<ByteBuf>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, instr: ByteBuf?) {
        println("client recieved : ${instr?.toString(CharsetUtil.UTF_8)}")
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush(Unpooled.copiedBuffer("netty rockes!", CharsetUtil.UTF_8))
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}

class ClientServer(val host: String, val port: Int) {
    fun start() {
        val group = NioEventLoopGroup()
        try {
            val b = Bootstrap()
            b.group(group).channel(NioSocketChannel::class.java).remoteAddress(InetSocketAddress(host, port))
                .handler(object : ChannelInitializer<SocketChannel?>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch?.pipeline()?.addLast(EchoClientHandler())
                    }
                })
            val f = b.connect().sync()
            f.channel().closeFuture().sync()
        } finally {
            group.shutdownGracefully().sync()
        }
    }
}

fun main(){
    ClientServer("localhost",9099).start()
}