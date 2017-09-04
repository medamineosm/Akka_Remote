package Actors

import java.net.URL

import Actors.Messages.{addParsedUrls, crawl, scrap}
import Core.Models.Models.Link
import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorLogging, AllForOneStrategy}

import scala.collection.mutable

/**
  * Created by Ouasmine on 31/08/2017.
  */
class MasterActor extends Actor with ActorLogging{

  // create the remote actor
  val remoteScrapper = context.actorSelection("akka.tcp://CrawlingSystem@127.0.0.1:5150/user/ScrapperActor")
  var maxUrls = 0
  var rootDomaine: String = ""

  var urlToScrap = mutable.Stack[String]()
  var urlScraped = mutable.Stack[String]()

  var timeStart = 0D
  var timeStop = 0D

  override def preStart(): Unit = log.info("MasterActor Starting ...")
  override val supervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 0) {
      case _: Exception     ⇒ Restart
    }

  def receive = {
    case "INIT" =>
      remoteScrapper ! "INIT"
    case crawl(rootUrl, limit, 0) =>
      log.info(s"MasterActor recieve msg : '$crawl'")
      timeStart = System.currentTimeMillis

      rootDomaine = new URL(rootUrl).getHost
      maxUrls = limit
      addUrl(rootUrl)
      sendUrl(0, limit)

    case addParsedUrls(links, depth) =>
      if (urlScraped.size <= maxUrls){
        log.info("MasterActor recieve msg : " + addParsedUrls + " with Links(" + links.size + ")")
        addUrls(links, depth)
        log.debug("urlToScrap Stack size : " + urlToScrap.size)
        sendUrl(depth, maxUrls)
      }else{
        log.info("Max Urls limit reached - ["+maxUrls+"] ")
        timeStop = System.currentTimeMillis
        log.info("Elpased Time : " + (timeStop - timeStart)/1000 + " secs")
      }
  }


  def isScraped(url: String): Boolean  = urlScraped.contains(url)

  def addUrl(url: String) = if(!isScraped(url)) urlToScrap.push(url)

  def addUrls(links: Seq[Link], depth: Int) = {
    println("urlToScrap size : " + urlScraped.size)
    if(urlScraped.size <= maxUrls){
      for (link <- links if rootDomaine.equals(new URL(link.href).getHost)){
        if(!isScraped(link.href))
          if(urlScraped.size <= maxUrls)
            urlToScrap.push(link.href)
        log.info("deepness {"+depth+"} ("+urlScraped.size+") " + urlScraped.contains(link.href) +" -> "+ link.href)
      }
    }
  }
  def sendUrl(depth: Int, limit: Int): Unit ={
    while (!urlToScrap.isEmpty) {
      Thread.sleep(500)
      var url = urlToScrap.pop
      urlScraped.push(url)
      log.info(s"MasterActor Send url To scrap : '$url'")
      remoteScrapper ! scrap(url, depth, limit)
    }
  }
}
