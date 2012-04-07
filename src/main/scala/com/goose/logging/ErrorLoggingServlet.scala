package com.goose.logging

import org.scalatra._
import net.liftweb.json.JsonParser.parse
import net.liftweb.json.DefaultFormats
import javax.servlet.http.HttpServletResponse._
import com.codahale.logula.Logging
import org.apache.log4j.Level

class ErrorLoggerServlet extends ScalatraServlet with CrossOriginReourceSharing with Logging {

  Logging.configure { log =>
    log.file.enabled = true
    log.file.filename = "/var/log/test.log"
    log.file.maxSize = 10 * 1024 // KB
    log.file.retainedFiles = 5 // keep five old logs around
  }

  implicit val formats = DefaultFormats

  post("/") {
    try {
      val logEntry = parse(request.body).extract[LogEntry]
      log.error(logEntry.details)
    }
    catch {
      case e: Exception => response.setStatus(SC_OK)
    }
  }

  notFound {
    response.setStatus(SC_OK)
  }

}

case class LogEntry(context: String, details: String)

