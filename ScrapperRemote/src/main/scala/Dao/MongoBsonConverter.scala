package Dao

import Core.Models.Models.ScrapResponse
import com.mongodb.casbah.commons.MongoDBObject
/**
  * Created by Ouasmine on 03/09/2017.
  */
object MongoBsonConverter {

  /**
    * Convert a Stock object into a BSON format that MongoDb can store.
    */
  def buildMongoDbObject(scrapResponse: ScrapResponse) = {
    val builder = MongoDBObject.newBuilder
    builder += "url" -> scrapResponse.url
    builder += "status" -> scrapResponse.status
    builder += "html" -> scrapResponse.html
    builder += "depth" -> scrapResponse.depth
    builder.result
  }

}
