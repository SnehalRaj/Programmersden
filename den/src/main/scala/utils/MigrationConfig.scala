package utils

import org.flywaydb.core.Flyway

trait MigrationConfig  {

  private val flyway = new Flyway()
  flyway.setDataSource("jdbc:postgresql://localhost/", "postgres", "postgres")
 
  def migrate() = {
    flyway.migrate()
  }

  def reloadSchema() = {
    flyway.clean()
    flyway.migrate()
  }
}