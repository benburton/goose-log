#goose-log

goose-log is a client-side logging framework written in [Scala](http://scala-lang.org) using [Scalatra](https://github.com/scalatra/scalatra) and [logula](https://github.com/codahale/logula) 

## Setup

### Building

1. Install [sbt](https://github.com/harrah/xsbt/wiki) 0.11.2.
2. Run sbt in the root project directory.
3. Use the container:start target to start the service:

<pre><code>bburton$ sbt
> container:start
[info] Started SelectChannelConnector@0.0.0.0:8080 STARTING
[success] Total time: 10 s, completed Apr 7, 2012 9:20:31 AM
</code></pre>

### IntelliJ IDEA Support

1. Install the [Scala](http://confluence.jetbrains.net/display/SCA/Scala+Plugin+for+IntelliJ+IDEA) and [sbt](https://github.com/orfjackal/idea-sbt-plugin) plugins.
2. Use the [sbt-idea](https://github.com/mpeltonen/sbt-idea) sbt plugin target to generate the project files:

<pre><code>bburton$ sbt
> gen-idea
[info] Trying to create an Idea module default-eb8248
[info] Excluding folder target
[info] Created /Users/bburton/Documents/workspace-scala/goose-log/.idea/goose-log.iml
[info] Created /Users/bburton/Documents/workspace-scala/goose-log/.idea
[info] Excluding folder /Users/bburton/Documents/workspace-scala/goose-log/target
[info] Created /Users/bburton/Documents/workspace-scala/goose-log/.idea_modules/default-eb8248.iml
[info] Created /Users/bburton/Documents/workspace-scala/goose-log/.idea_modules/project.iml
</code></pre>

3. Launch IntelliJ IDEA and select File > Open Project, choosing the goose-log folder as the project root.

## Logging

You can use the client-side logger provided here: TODO -- add JS code for frontend

### JSON Logging Request Formats

#### Basic

goose-log processes JSON requests of the following format:
<pre><code>{
  "details" : {
    "event" : "inbox.message.read",
    "message" : "inbox message not found exception"
  }
}
</code></pre>
It expects a top-level key of "details" which can contain an arbitrary JSON object rendered in the server logs as:
<pre><code>> ERROR [2012-04-07 20:38:01,666] com.goose.logging.ErrorLoggingServlet: 
  User Agent: Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.27) Gecko/20120216 Firefox/3.6.27
  Origin: http://fiddle.jshell.net
  Timestamp: Sat Apr 07 16:38:01 EDT 2012
  Details:
    event: inbox.message.read
    message: inbox message not found exception
</code></pre>

#### Optional Parameters

Any of the additional metadata parameters (User Agent, Origin, Timestamp) can be explicitly overridden in the JSON request (TODO this isn't actually true at the moment):

<pre><code>{
  "userAgent" : "Lynx/2.8.5dev.16 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.6b",
  "origin" : "http://www.google.com/",
  "timestamp" : 978310861000,
  "details" : {
    "event" : "inbox.message.read",
    "message" : "inbox message not found exception"
  }
}
</code></pre>

generates the corresponding log entry:

<pre><code>> ERROR [2012-04-07 20:38:01,666] com.goose.logging.ErrorLoggingServlet:
  User Agent: Lynx/2.8.5dev.16 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.6b
  Origin: http://www.google.com/
  Timestamp: Sun Dec 31 20:01:01 EST 2000
  Details:
    event: inbox.message.read
    message: inbox message not found exception
</code></pre>

Note that the timestamp JSON will accept a Long value representing milliseconds since the Unix epoc, or a string representing a timestamp to be written to the log explicitly.

##### The Provider Parameter

You can add a provider. It writes to a files with that name. (TODO: rewrite this copy)

For example, the JSON request

<pre><code>{
  "provider" : "my-awesome-webapp",
  "details" : {
    "event" : "inbox.message.read",
    "message" : "inbox message not found exception"
  }
}
</code></pre>

will append to the file my-awesome-webapp.log in the default directory (currently /var/log/).

#### Sending a JSON List

Instead of passing error logging requests one-at-a-time, the logger can accept a JSON list of objects:

<pre><code>[{
  "details" : {
    "event" : "inbox.message.read",
    "message" : "inbox message not found exception"
  }
},{
  "details" : {
    "event" : "user.authentication",
    "message" : "bad user authentication information"
  }
}]
</code></pre>
