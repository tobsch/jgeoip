require 'bundler/setup'

$:.push File.expand_path("../lib", __FILE__)
require "jgeoip/version"

# add maven repositories
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://repo1.maven.org/maven2'

define 'jgeoip' do
  project.version = JGeoIP::VERSION

  # add dependencies from jar
  compile.using(:target => '1.6', :source => '1.6').with('com.maxmind.geoip:geoip-api:jar:1.2.12')

  # package our shiny little bidder jar
  package :jar, :file => _("lib/java/jgeoip-#{JGeoIP::VERSION}.jar")

  task :setup => [ :clean, :compile, :package ]
end
