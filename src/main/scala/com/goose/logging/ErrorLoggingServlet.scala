package com.goose.logging

import org.scalatra._
import net.liftweb.json.JsonParser.parse
import net.liftweb.json.DefaultFormats
import javax.servlet.http.HttpServletResponse._
import com.codahale.logula.Logging
import java.util.Date

/**
 * Simple ScalatraServlet to process and log entries coming from POST requests. Allows entries from any Origin via CORS.
 */
class ErrorLoggingServlet extends ScalatraServlet with CrossOriginReourceSharing with Logging {

  Logging.configure { log =>
    log.file.enabled = true
    log.file.filename = "/var/log/test.log"
    log.file.maxSize = 10 * 1024 // KB
    log.file.retainedFiles = 5 // keep five old logs around
  }

  implicit val formats = DefaultFormats

  post("/") {
    try {
      val logEntry: LogEntry = parse(request.body).extract[LogEntry]
        .copy(userAgent = Some(request.getHeader("User-Agent")))
        .copy(timestamp = Some(new Date()))
      log.error(logEntry.toString)
    }
    catch {
      case e: Exception => e.printStackTrace()
        response.setStatus(SC_INTERNAL_SERVER_ERROR)
    }
  }

  notFound {
    response.setStatus(SC_NOT_FOUND)
  }

}

