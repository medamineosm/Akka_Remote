package Core.Crawler

import java.net.{MalformedURLException, URL}

import Core.Models.Models.{Link, WebDocument}
import org.jsoup.Jsoup

import scala.collection.JavaConversions._
import scala.util.control.Exception.catching

/**
  * Created by Ouasmine on 01/09/2017.
  */
object Parser {

  type JDoc = org.jsoup.nodes.Document

  def get(html: String): JDoc = Jsoup.parse(html)

  def titleText(doc: JDoc): String = doc.select("title").text

  def bodyText(doc: JDoc): String = doc.select("body").text

  /**
    * Allows for extraction without null pointer exceptions
    *
    */
  def safeMetaExtract(doc: JDoc, meta: String): String = {
    val result = doc.select("meta[name=" ++ meta ++ "]").first
    Option(result) match {
      case Some(v) => v.attr("content")
      case None => ""
    }
  }

  def metaKeywords(doc: JDoc): String = safeMetaExtract(doc, "keywords")

  def metaDescription(doc: JDoc): String = safeMetaExtract(doc, "description")

  /**
    * Extracts links from a document
    *
    */
  def linkSequence(doc: JDoc): Seq[Link] = {
    val links = doc.select("a[href]").iterator.toList
    links.map { l => Link(l.text, l.absUrl("href")) }
  }

  def extract(doc: JDoc): WebDocument = {
    val title: String = titleText(doc)
    val body: String = bodyText(doc)
    val links: Seq[Link] = linkSequence(doc).filter(_.href.trim != "")
    val desc: String = metaDescription(doc)
    WebDocument(title, body, links, desc)
  }

  def safeURL(url: String): Option[String] = {
    val result = catching(classOf[MalformedURLException]) opt new URL(url)
    result match {
      case Some(v) => Some(v.toString)
      case None => None
    }
  }

  def parseOnlyValideStatusCode(status: Int):Boolean = status != 404
  /**
    * Crawl a URL and return a WebDocument
    *
    */
  def parse(html: String): WebDocument = {
    val f = extract _ compose get
    f(html)
  }
}
