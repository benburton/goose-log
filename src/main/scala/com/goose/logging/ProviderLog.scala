package com.goose.logging

import com.codahale.logula.Logging
import java.util.Date
import javax.servlet.http.HttpServletRequest

case class ProviderLog(provider: String) extends Logging {

  val LOG_PATH = "/opt/tomcat/logs"
  
  Logging.configure { log =>
    log.file.enabled = true
    log.file.filename = "%s/%s.log".format(LOG_PATH, provider)
    log.file.maxSize = 10 * 1024 // KB
    log.file.retainedFiles = 5 // keep five old logs around
  }

  def error(logEntry: LogEntry, request: HttpServletRequest) {
    log.error(logEntry.copy(userAgent = Some(request.getHeader("User-Agent")))
      .copy(timestamp = Some(new Date()))
      .copy(origin = Some(request.getHeader("Origin"))).toString)
  }

}
