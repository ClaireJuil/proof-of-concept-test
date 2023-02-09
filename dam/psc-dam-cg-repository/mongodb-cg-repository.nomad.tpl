job "cg-dam-api" {
  namespace = "psc-dam"
  datacenters = ["${datacenter}"]
  type = "service"
  vault {
    policies = ["psc-dam-engine"]
    change_mode = "restart"
  }

  group "dam-reader-api" {
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

    task "dam-reader" {
      driver = "docker"
      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
JAVA_TOOL_OPTIONS="-Xms256m -Xmx3g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties"
EOH
      }

      template {
        data = <<EOF
spring.application.name=psc-dam-api
server.servlet.context-path=/dam-reader

database.type=mongo

{{ range service "cg-mongodb" }}
spring.data.mongodb.host={{ .Address }}
spring.data.mongodb.port={{ .Port }}
{{ end }}
spring.data.mongodb.database=mongodb
{{ with secret "psc-dam-engine/cg-db" }}
spring.data.mongodb.username={{ .Data.data.mongo_user }}
spring.data.mongodb.password={{ .Data.data.mongo_pass }}
{{ end }}
spring.data.mongodb.auto-index-creation=true

spring.servlet.multipart.max-file-size=256MB
spring.servlet.multipart.max-request-size=256MB
spring.servlet.multipart.enabled=true

spring.main.allow-bean-definition-overriding=true

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
EOF
        destination = "secrets/application.properties"
        change_mode = "restart"
      }

      resources {
        cpu = 500
        memory = 3500
      }

      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/dam-reader"]
        port = "http"
        check {
          type = "http"
          port = "http"
          path = "dam-reader/check"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = 5
        }
      }
    }
  }
}
