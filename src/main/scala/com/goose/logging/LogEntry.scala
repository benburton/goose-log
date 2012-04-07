package com.goose.logging

import java.util.Date

/**
 * Simple case class representing an entry for the logger.
 */
case class LogEntry(userAgent: Option[String] = Some(""),
               origin: Option[String] = None, 
               timestamp: Option[Object] = Some(new Date()),
               details: Option[Map[String, String]] = None) {
  
  override def toString() = {
    var buffer = new StringBuilder("\n")
    userAgent match {
      case ua: Some[String] => buffer = LogEntry.appendWithIndent(buffer, "User Agent", ua.get)
      case None =>
    }
    origin match {
      case o: Some[String] => buffer = LogEntry.appendWithIndent(buffer, "Origin", o.get)
      case None =>
    }
    timestamp match {
      case ts: Some[Date] => buffer = LogEntry.appendWithIndent(buffer, "Timestamp", ts.get.toString)
      case None =>
    }
    details match {
      case dt: Some[Map[String, String]] => buffer = LogEntry.appendWithIndent(buffer, "Details", dt.get)
      case None =>
    }
    
    buffer.toString()
  }
}

object LogEntry {
  
  private val tabSize = 4
  private val indent = " " * tabSize
  
  private def appendWithIndent(stringBuilder: StringBuilder, key: String, value: String): StringBuilder = {
    stringBuilder.append(indent).append(key).append(": ").append(value).append("\n")
  }

  private def appendWithIndent(stringBuilder: StringBuilder, key: String, value: Map[String, String]): StringBuilder = {
    stringBuilder.append(indent).append(key).append(":\n")
    for ((k, v) <- value) {
      stringBuilder.append(indent).append(indent).append(k).append(": ").append(v).append("\n")
    }
    stringBuilder
  }
}
