package com.goose.logging

import org.scalatra._
import net.liftweb.json.JsonParser.parse
import net.liftweb.json.DefaultFormats
import javax.servlet.http.HttpServletResponse._
import com.codahale.logula.Logging

/**
 * Simple ScalatraServlet to process and log entries coming from POST requests. Allows entries from any Origin via CORS.
 */
class ErrorLoggingServlet extends ScalatraServlet with CrossOriginReourceSharing with Logging {

  val LOG_ENTRY_FORMAT = """
  User Agent: %s
  Details: %s"""

  Logging.configure { log =>
    log.file.enabled = true
    log.file.filename = "/var/log/test.log"
    log.file.maxSize = 10 * 1024 // KB
    log.file.retainedFiles = 5 // keep five old logs around
  }

  implicit val formats = DefaultFormats

  implicit def lgoEntryAsString(logEntry: LogEntry): String = {
    String.format(LOG_ENTRY_FORMAT, logEntry.userAgent,
    logEntry.details.foldLeft("\n    ")((acc, kv) => acc + kv._1 + ": " + kv._2 + "\n    "))
  }

  post("/") {
    try {
      val logEntry = parse(request.body).extract[LogEntry]
      log.error(logEntry)
    }
    catch {
      case e: Exception => println(e)
        response.setStatus(SC_OK)
    }
  }

  notFound {
    response.setStatus(SC_OK)
  }

}

/**
 * Case class defining the structure of the JSON request to be logged.
 */
case class LogEntry(userAgent: String,
                    details: Map[String, String])

