job "cg-mongodb" {
  namespace = "psc-dam"
  datacenters = [
    "${datacenter}"]
  type = "service"

  vault {
    policies = [
      "psc-dam-engine"]
    change_mode = "restart"
  }

  group "cg-mongodb" {
    count = 1

     volume "cg-mongo" {
      type      = "host"
      read_only = false
      source    = "cg-mongo"
    }

    restart {
      attempts = 3
      delay = "60s"
      interval = "1h"
      mode = "fail"
    }

    constraint {
      attribute = "$\u007Bnode.class\u007D"
      value = "data"
    }

    update {
      max_parallel = 1
      min_healthy_time = "30s"
      progress_deadline = "5m"
      healthy_deadline = "2m"
    }

    network {
      port "db" {
        to = 27017
      }
    }

    task "cg-mongodb" {
      driver = "docker"
      volume_mount {
        volume      = "cg-mongo"
        destination = "/data"
        read_only   = false
      }

      template {
        data = <<EOH
{{ with secret "psc-dam-engine/cg-db" }}
MONGO_INITDB_ROOT_USERNAME = {{ .Data.data.root_user }}
MONGO_INITDB_ROOT_PASSWORD = {{ .Data.data.root_pass }}{{ end }}
EOH
        destination = "secrets/.env"
        change_mode = "restart"
        env = true
      }
      config {
        image = "${image}:${tag}"
        ports = ["db"]
      }
      resources {
        cpu = 200
        memory = 512
      }
      service {
        name = "$\u007BNOMAD_JOB_NAME\u007D"
        port = "db"
        check {
          name         = "alive"
          type         = "tcp"
          interval     = "30s"
          timeout      = "5s"
          failures_before_critical = 5
          port         = "db"
        }
      }
    }
  }
}
