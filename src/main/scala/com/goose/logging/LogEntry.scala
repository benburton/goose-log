package com.goose.logging

import java.util.Date

/**
 * Simple case class representing an entry for the logger.
 */
case class LogEntry(userAgent: Option[String] = None,
               timestamp: Option[Object] = None,
               details: Option[Map[String, String]] = None) {
  
  override def toString() = {
    var buffer = new StringBuilder("\n")
    userAgent match { case ua: Some[String] => buffer = LogEntry.appendWithIndent(buffer, "User Agent", ua.get) }
    timestamp match { case ts: Some[Date] => buffer = LogEntry.appendWithIndent(buffer, "Timestamp", ts.get.toString) }
    details match {
      case dt: Some[Map[String, String]] => buffer = LogEntry.appendWithIndent(buffer, "Details", dt.get)
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
