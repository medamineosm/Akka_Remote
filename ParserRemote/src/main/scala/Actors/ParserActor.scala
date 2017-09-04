package Actors

import Actors.Messages.{addParsedUrls, parse}
import Core.Crawler.Parser
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, AllForOneStrategy}
/**
  * Created by Ouasmine on 01/09/2017.
  */
class ParserActor extends Actor with ActorLogging{

  val remoteMaster = context.actorSelection("akka.tcp://CrawlingSystem@127.0.0.1:5555/user/MasterActor")

  override def preStart(): Unit = log.info("ParserActor Starting ...")
  override val supervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 0) {
      case _: Exception     ⇒ Restart
    }

  override def receive: Receive = {

    case parse(response, depth) =>
      log.info("Parser Actor will parse the content of url : [" + response.status + "] " + response.url)
      val parsedPage = Parser.parse(response.html)
      log.info("["+response.status+"]["+response.url+"] Links found : " + parsedPage.links.size)
      remoteMaster ! addParsedUrls(parsedPage.links, depth+1)
  }


}
