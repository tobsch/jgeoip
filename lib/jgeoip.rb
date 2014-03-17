require 'geoip-jars'

Dir[File.expand_path('../java/*.jar', __FILE__)].each { |jar| require(jar) }

java_import 'org.github.tobsch.jgeoip.JGeoIPLibrary'
JGeoIPLibrary.new.load(JRuby.runtime, false)
