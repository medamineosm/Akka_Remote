package Dao

import com.mongodb.casbah.Imports._

/**
  * Created by OUASMINE Mohammed Amine on 31/08/2017.
  */
object MongoFactory {
  private val SERVER = "127.0.0.1"
  private val DATABASE = "Scrapper"
  private val COLLECTION = "Urls"

  val mongoClient = MongoClient(SERVER, 27017)(DATABASE)(COLLECTION)
}
