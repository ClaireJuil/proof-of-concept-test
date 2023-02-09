job "dam-api" {
  namespace = "psc-dam"
  datacenters = ["${datacenter}"]
  type = "service"
  vault {
    policies = ["psc-dam-engine"]
    change_mode = "restart"
  }

  group "dam-api" {
    count = "1"
    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    affinity {
      attribute = "$\u007Bnode.class\u007D"
      value     = "standard"
    }

    update {
      max_parallel = 1
      min_healthy_time = "30s"
      progress_deadline = "5m"
      healthy_deadline = "2m"
    }

    network {
      port "http" {
        to = 8080
      }
    }

    task "dam-api" {
      driver = "docker"
      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
        JAVA_TOOL_OPTIONS="-Xms1g -Xmx2g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties"
EOH
      }

      template {
        destination = "secrets/application.properties"
        change_mode = "restart"
        data = <<EOH
server.servlet.context-path=/psc-dam-api/v1
{{ with secret "psc-dam-engine/psc-dam-api" }}
psc.url={{ .Data.data.psc_url}}
psc.clientID={{ .Data.data.psc_client_id}}
psc.clientSecret={{ .Data.data.psc_client_secret}}
with.gravitee={{ .Data.data.with_gravitee}}
{{ end }}
damreader.url=http://{{ range service "cg-dam-api" }}{{ .Address }}:{{ .Port }}{{ end }}/dam-reader
structurereader.url=http://{{ range service "psc-dam-rass-structure-api" }}{{ .Address }}:{{ .Port }}{{ end }}/rass-structure-api
EOH
      }

      resources {
        cpu = 300
        memory = 2148
      }

      service {
        name = "$\u007BNOMAD_NAMESPACE\u007D-$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/psc-dam-api/v1/"]
        port = "http"
        check {
          type = "http"
          path = "/psc-dam-api/v1/check"
          port = "http"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = 3
        }
      }
    }
  }
}
