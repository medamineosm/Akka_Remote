package Dao

import Core.Models.Models.ScrapResponse

/**
  * Created by Ouasmine on 03/09/2017.
  */
object Dao_operations {


  def saveScrappedResponse(scrapedResponse : ScrapResponse) = {
    MongoFactory.mongoClient.save(
      MongoBsonConverter.
        buildMongoDbObject(scrapedResponse))
  }
}
