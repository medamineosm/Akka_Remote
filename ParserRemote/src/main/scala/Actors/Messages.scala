package Actors

import Core.Models.Models.{Link, ScrapResponse}

/**
  * Created by Ouasmine on 01/09/2017.
  */
object Messages {
  case class parse(response: ScrapResponse, depth: Int)
  case class addParsedUrls(links: Seq[Link], depth: Int)
}
