package Actors

import Core.Models.Models.Link

/**
  * Created by Ouasmine on 31/08/2017.
  */
object Messages {
  case class crawl(rootUrl: String, limit: Int, depth: Int)
  case class scrap(url: String, depth: Int, limit: Int)
  case class addParsedUrls(links: Seq[Link], depth: Int)
  case class stopParse()
}
