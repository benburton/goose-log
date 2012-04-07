package com.goose.logging

import org.scalatra.ScalatraServlet

/**
 * Trait which provides support for CORS.
 */
trait CrossOriginReourceSharing extends ScalatraServlet {

  /**
   * Returns request's Origin header in OPTIONS Access-Control-Allow-Origin response header if present, otherwise *.
   */
  options("*") {
    Option(request.getHeader("Origin")) match {
      case origin: Some[String] => response.setHeader("Access-Control-Allow-Origin", origin.get)
      case _ => response.setHeader("Access-Control-Allow-Origin", "*")
    }
  }
  
}
