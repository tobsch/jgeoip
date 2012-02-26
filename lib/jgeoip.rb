require 'java'

# Load the required jars
require File.expand_path('../java/geoip-1.2.5', __FILE__)

# load the version file
require File.expand_path('../jgeoip/version', __FILE__)

# import the jRuby Extension
require File.expand_path("../java/jgeoip-#{JGeoIP::VERSION}", __FILE__)
java_import 'org.github.tobsch.jgeoip.JGeoIPLibrary'
JGeoIPLibrary.new.load(JRuby.runtime, false)
