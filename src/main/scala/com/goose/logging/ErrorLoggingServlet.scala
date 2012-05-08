package com.goose.logging

import org.scalatra._
import net.liftweb.json.JsonParser.parse
import net.liftweb.json.DefaultFormats
import javax.servlet.http.HttpServletResponse._
import com.codahale.logula.Logging
import java.util.Date
import collection.mutable.{Map, HashMap}

/**
 * Simple ScalatraServlet to process and log entries coming from POST requests. Allows entries from any Origin via CORS.
 */
class ErrorLoggingServlet extends ScalatraServlet with CrossOriginReourceSharing {

  var providerLogs: Map[String, ProviderLog] = new HashMap[String, ProviderLog]
  
  implicit val formats = DefaultFormats

  /**
   * Implicit coersion from a String to a List[LogEntry]
   */
  private implicit def requestBodyToLogEntries(requestBody: String): List[LogEntry] = {
    try {
      parse(requestBody).extract[List[LogEntry]]
    }
    catch {
      case e: Exception => List(parse(requestBody).extract[LogEntry])
    }
  }

  post("/") {
    try {
      val logEntries: List[LogEntry] = request.body
      for (logEntry <- logEntries) {
        val provider: String = logEntry.provider.getOrElse("default")
        getProviderLog(provider) match {
          case log: Some[ProviderLog] => log.get.error(logEntry, request)
          case None => throw new Exception("unable to find or create log for %s".format(provider))
        }
      }
      response.setStatus(SC_OK)
    }
    catch {
      case e: Exception => e.printStackTrace()
        response.setStatus(SC_INTERNAL_SERVER_ERROR)
    }
  }

  notFound {
    response.setStatus(SC_NOT_FOUND)
  }
  
  private def getProviderLog(provider: String): Option[ProviderLog] = {
    if (!providerLogs.contains(provider)) {
      providerLogs.put(provider, new ProviderLog(provider))
    }
    providerLogs.get(provider)
  }

}

