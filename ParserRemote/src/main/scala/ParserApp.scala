import Actors.ParserActor
import akka.actor.{ActorSystem, Props}

/**
  * Created by Ouasmine on 01/09/2017.
  */
object ParserApp extends App{
  val system = ActorSystem("CrawlingSystem")
  val scrapperActor = system.actorOf(Props[ParserActor], name = "ParserActor")
}
