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
