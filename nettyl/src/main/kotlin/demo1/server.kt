package demo1

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.util.CharsetUtil

@Sharable
class EchoServerHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val instream = msg as ByteBuf
        println("server receiverd : ${instream.toString(CharsetUtil.UTF_8)}")
        ctx?.write(instream)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx?.writeAndFlush(Unpooled.EMPTY_BUFFER)?.addListener(ChannelFutureListener.CLOSE)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

}

class EchoServer(val port: Int) {
    fun start() {
        val serverHandler = EchoServerHandler()
        val group = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(group).channel(NioServerSocketChannel::class.java).localAddress(port).childHandler(
                object: ChannelInitializer<SocketChannel>(){
                    override fun initChannel(ch: SocketChannel?) {
                        ch?.pipeline()?.addLast(serverHandler)
                    }
                }
            )
            val f = b.bind().sync()
            f.channel().closeFuture().sync()
        } finally {
            group.shutdownGracefully().sync()
        }
    }
}

fun main (){
    EchoServer(9099).start()
}
