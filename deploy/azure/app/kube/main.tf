########
# Application level stuff will live here
# Each module is conditionally created within this app infra definition interface and can be re-used across app types e.g. SSR webapp, API only
########

data "azurerm_client_config" "current" {}

# Naming convention
module "default_label" {
  source     = "git::https://github.com/cloudposse/terraform-null-label.git?ref=0.25.0"
  namespace  = "${var.name_company}-${var.name_project}"
  stage      = var.name_environment
  name       = "${lookup(var.location_name_map, var.resource_group_location, "uksouth")}-${var.name_domain}"
  attributes = var.attributes
  delimiter  = "-"

  tags = var.tags
}

module "app" {
  source                               = "git::https://github.com/amido/stacks-terraform//azurerm/modules/azurerm-server-side-app?ref=v6.0.27"
  create_cdn_endpoint                  = var.create_cdn_endpoint
  create_cosmosdb                      = var.create_cosmosdb
  resource_namer                       = module.default_label.id
  resource_group_location              = var.resource_group_location
  resource_tags                        = module.default_label.tags
  cosmosdb_sql_container               = var.cosmosdb_sql_container
  cosmosdb_sql_container_partition_key = var.cosmosdb_sql_container_partition_key
  cosmosdb_kind                        = var.cosmosdb_kind
  cosmosdb_offer_type                  = var.cosmosdb_offer_type
  create_cache                         = var.create_cache
  create_dns_record                    = var.create_dns_record
  dns_record                           = var.dns_record
  dns_zone_name                        = var.dns_zone_name
  infra_resource_group                 = var.infra_resource_group
  dns_zone_resource_group              = var.dns_zone_resource_group != "" ? var.dns_zone_resource_group : var.infra_resource_group
  dns_ip_address_name                  = var.app_gateway_frontend_ip_name
  dns_ip_address_resource_group        = var.infra_resource_group
  subscription_id                      = data.azurerm_client_config.current.subscription_id
}
