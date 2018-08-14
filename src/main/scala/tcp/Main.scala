package tcp

sealed trait Status
case object Ok extends Status
case class Error(msg: String) extends Status

case class Server(listenFd: Int) {

  def listen(): Status = ???

  def accept(): Status = ???

}

object Main {

  val Port = 0

  def main(args: Array[String]): Unit = {
    val server = Server(0)

    server.listen() match {
      case Error(msg) =>
        System.err.println(s"Failed to listen on address 0.0.0.0:$Port\n$msg\n")

      case Ok =>
        while (true) {
          server.accept() match {
            case Error(msg) =>
              System.err.println(s"Failed accepting connection\n")
              System.exit(1)
            case Ok =>
          }
        }
    }

  }
}
