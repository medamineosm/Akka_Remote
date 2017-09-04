import Actors.MasterActor
import Actors.Messages.crawl
import akka.actor.{ActorSystem, Props}

/**
  * Created by Ouasmine on 31/08/2017.
  */
object MasterApp  extends App{
  implicit val system = ActorSystem("CrawlingSystem")
  val masterActor = system.actorOf(Props[MasterActor], name = "MasterActor")  // the master actor

  masterActor ! crawl("http://www.1-2-3.com/fr/", 20, 0)
}
