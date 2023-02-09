project = "psc-dam/cg-mongodb"

labels = { "domaine" = "psc-dam"}

runner {
  enabled = true
  profile = "psc-dam"
  data_source "git" {
    url = "https://github.com/prosanteconnect/proof-of-concept.git"
    path = "dam/psc-dam-components/psc-dam-cg-mongodb/"
    ignore_changes_outside_path = true
    ref = "main"
  }
  poll {
    enabled = false
    interval = "24h"
  }
}

app "psc-dam/cg-mongodb" {
  build {
    use "docker-pull" {
      image = var.image
      tag = var.tag
      disable_entrypoint = true
    }	
    registry {
      use "docker" {
        image    = "${var.registry}/${var.image}"
        tag      = var.tag
        username = var.registry_username
        password = var.registry_password
        local    = true
      }
    }
  }

  deploy {
    use "nomad-jobspec" {
      jobspec = templatefile("${path.app}/cg-mongodb.nomad.tpl", {
        image = var.image
        tag = var.tag
        datacenter = var.datacenter
      })
    }
  }
}

variable "datacenter" {
  type = string
  default = "pocs-ans-psc"
  env = ["NOMAD_DATACENTER"]
}

variable "image" {
  type    = string
  default = "mongo"
}

variable "tag" {
  type    = string
  default = "6.0"
}

variable "registry_username" {
  type    = string
  default = ""
  env     = ["REGISTRY_USERNAME"]
  sensitive = true
}

variable "registry_password" {
  type    = string
  default = ""
  env     = ["REGISTRY_PASSWORD"]
  sensitive = true
}

variable "registry" {
  type    = string
  default = ""
  env     = ["REGISTRY"]
}

