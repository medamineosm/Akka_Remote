package Actors
import java.net.SocketTimeoutException

import Actors.Messages.{parse, scrap}
import Core.Models.Models.ScrapResponse
import Dao.Dao_operations
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, AllForOneStrategy}
import org.jsoup.{Connection, Jsoup}
/**
  * Created by Ouasmine on 31/08/2017.
  */
class ScrapperActor extends Actor with ActorLogging {

  val remoteParser = context.actorSelection("akka.tcp://CrawlingSystem@192.168.0.101:5151/user/ParserActor")
  var maxUrls = 0
  override def preStart(): Unit = log.info("ScrapperActor Starting ...")


  override def receive = {
    case scrap(url, depth, limit) =>
      log.info("Limit " + maxUrls +" / "+ limit)
      if(maxUrls < limit){
        log.debug(s"ScrapperActor recieve msg :'$scrap'")
        log.info("Scrapping the url ["+ url +"]")
        var result = Scrap(url, depth)
        remoteParser ! parse(result, depth)
        maxUrls = maxUrls +1
        log.info("saving the scrapped result into mongoDb")
        Dao_operations.saveScrappedResponse(result)
      }else{
        log.info("Scrapper reached limit ("+limit+") ["+maxUrls+"]")
      }
  }

  def Scrap(url: String, depth: Int): ScrapResponse = {
    var response : Connection.Response = null
    try {
      response = Jsoup.connect(url).timeout(30000).execute()
    } catch {
      case ioe: SocketTimeoutException =>
    }
    log.debug("html content for ["+url+"] = " + response.parse.html.size)
    ScrapResponse(url, response.statusCode(), response.parse().html(), depth)
  }
}
