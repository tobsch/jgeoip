require 'geoip-jars'

Dir[File.expand_path('../java/*.jar', __FILE__)].each { |jar| require(jar) }

java_import 'org.github.tobsch.jgeoip.JGeoIPLibrary'
JGeoIPLibrary.new.load(JRuby.runtime, false)

class JGeoIP
  # The +data/+ directory for geoip
  DATA_DIR = File.expand_path(File.join(File.dirname(__FILE__),'..','data','geoip'))

  # Ordered list of the ISO3166 2-character country codes, ordered by
  # GeoIP ID
  CountryCode = YAML.load_file(File.join(DATA_DIR,'country_code.yml'))

  # Ordered list of the ISO3166 3-character country codes, ordered by
  # GeoIP ID
  CountryCode3 = YAML.load_file(File.join(DATA_DIR,'country_code3.yml'))

  # Ordered list of the English names of the countries, ordered by GeoIP ID
  CountryName = YAML.load_file(File.join(DATA_DIR,'country_name.yml'))

  # Ordered list of the ISO3166 2-character continent code of the countries,
  # ordered by GeoIP ID
  CountryContinent = YAML.load_file(File.join(DATA_DIR,'country_continent.yml'))

  # Load a hash of region names by region code
  RegionName = YAML.load_file(File.join(DATA_DIR,'region.yml'))

  # Hash of the timezone codes mapped to timezone name, per zoneinfo
  TimeZone = YAML.load_file(File.join(DATA_DIR,'time_zone.yml'))
end
