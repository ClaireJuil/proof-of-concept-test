job "rass-structure-api" {
  namespace = "psc-dam"
  datacenters = ["${datacenter}"]
  type = "service"
  vault {
    policies = ["psc-dam-engine"]
    change_mode = "restart"
  }

  group "rass-structure-api" {
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

    task "rass-structure-api" {
      driver = "docker"
      config {
        image = "${artifact.image}:${artifact.tag}"
        ports = ["http"]
      }

      template {
        destination = "local/file.env"
        env = true
        data = <<EOH
JAVA_TOOL_OPTIONS="-Xms256m -Xmx1g -XX:+UseG1GC -Dspring.config.location=/secrets/application.properties"
EOH
      }

      template {
        data = <<EOF
spring.application.name=rass-structure-api
server.servlet.context-path=/rass-structure-api

database.type=mongo

spring.main.allow-bean-definition-overriding=true

{{ range service "rass-mongodb" }}
spring.data.mongodb.host={{ .Address }}
spring.data.mongodb.port={{ .Port }}
{{ end }}
spring.data.mongodb.database=mongodb
{{ with secret "psc-dam-engine/rass-db" }}
spring.data.mongodb.username={{ .Data.data.mongo_user }}
spring.data.mongodb.password={{ .Data.data.mongo_pass }}
{{ end }}
spring.data.mongodb.auto-index-creation=true

spring.servlet.multipart.max-file-size=256MB
spring.servlet.multipart.max-request-size=256MB
spring.servlet.multipart.enabled=true

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
EOF
        destination = "secrets/application.properties"
        change_mode = "restart"
      }

      resources {
        cpu = 500
        memory = 1280
      }

      service {
        name = "$\u007BNOMAD_NAMESPACE\u007D-$\u007BNOMAD_JOB_NAME\u007D"
        tags = ["urlprefix-/rass-structure-api"]
        port = "http"
        check {
          type = "http"
          port = "http"
          path = "rass-structure-api/check"
          interval = "30s"
          timeout = "2s"
          failures_before_critical = 5
        }
      }
    }
  }
}
