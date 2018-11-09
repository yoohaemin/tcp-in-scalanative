package tcp

import scala.scalanative.native._
import scala.scalanative.posix.sys.socket.{ socket, AF_INET, SOCK_STREAM }
import scala.scalanative.posix.netinet.in.{ sockaddr_in, INADDR_ANY }
import scala.scalanative.posix.arpa.inet.{ htons, htonl }

sealed trait Status
object Status {
  case object Ok extends Status
  case class Error(msg: String) extends Status
}


class Server(val listenFd: CInt) extends AnyVal {

  def listen(): Status = {
    val result = socket(AF_INET, SOCK_STREAM, 0)

    if (result == -1)
      Status.Error("Failed to create socket endpoint")
    else
      Status.Ok
  }

  def accept(): Status = ???

}

object Main {

  val Port = 1234

  val serverAddr = stackalloc[sockaddr_in]
  !serverAddr._1 = AF_INET.toUShort //sin_family
  !serverAddr._2 = htons(Port.toUShort) //sin_addr
  !serverAddr._3 = new sockaddr_in(htonl(INADDR_ANY)) //sin_port

  def main(args: Array[String]): Unit = {
    val server = new Server(0)

    server.listen() match {
      case Status.Error(msg) =>
        System.err.println(s"Failed to listen on address 0.0.0.0:$Port\n$msg\n")

      case Status.Ok =>
        while (true) {
          server.accept() match {
            case Status.Error(msg) =>
              System.err.println(s"Failed accepting connection\n")
              System.exit(1)
            case Status.Ok =>
          }
        }
    }

  }
}


object Foo {

  type Server = CStruct1[Int]
  val server = stackalloc[Server]

  !server

}
