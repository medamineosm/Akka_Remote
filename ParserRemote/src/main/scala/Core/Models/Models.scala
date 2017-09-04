package Core.Models

/**
  * Created by Ouasmine on 01/09/2017.
  */
object Models {
  sealed case class Link(title: String, href: String)
  case class WebDocument(title: String,
                         body: String,
                         links: Seq[Link],
                         metaDescription: String)
  case class ScrapResponse(url: String, status: Int, html: String, depth: Int)

}
