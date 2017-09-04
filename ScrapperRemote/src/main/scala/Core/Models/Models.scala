package Core.Models

/**
  * Created by OUASMINE Mohammed Amine on 31/08/2017.
  */
object Models {
  case class ScrapResponse(url: String, status: Int, html: String, depth: Int)
}

