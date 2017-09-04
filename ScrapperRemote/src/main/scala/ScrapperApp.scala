import Actors.ScrapperActor
import akka.actor.{ActorSystem, Props}

/**
  * Created by Ouasmine on 31/08/2017.
  */
object ScrapperApp extends App{
  val system = ActorSystem("CrawlingSystem")
  val scrapperActor = system.actorOf(Props[ScrapperActor], name = "ScrapperActor")
  scrapperActor ! "The ScrapperActor is alive"
}
