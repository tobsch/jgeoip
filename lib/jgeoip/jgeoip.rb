class JGeoIP
  # Import the geoip jar
  java_import 'com.maxmind.geoip.LookupService'

  def initialize(database_path)
    @cl = LookupService.new(database_path, LookupService::GEOIP_MEMORY_CACHE)
  end
  
  def city(search_string)
    @cl.getLocation(search_string)
  end
end