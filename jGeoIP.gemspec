# -*- encoding: utf-8 -*-
$:.push File.expand_path("../lib", __FILE__)
require "jgeoip/version"

Gem::Specification.new do |s|
  s.name        = "jgeoip"
  s.version     = JGeoIP::VERSION
  s.authors     = ["Tobias Schlottke"]
  s.email       = ["tobias.schlottke@gmail.com"]
  s.homepage    = "http://github.com/tobsch/jgeoip"
  s.summary     = 'Fast JRuby extension for Maxminds GeoIP Databases'
  s.description = 'This gem wraps maxminds original java library with a pure jRuby extension, which should make things faster than calling the lib through jRuby directly.'
  s.platform    = 'java'
  
  s.files         = `git ls-files`.split("\n")
  s.test_files    = `git ls-files -- {test,spec,features}/*`.split("\n")
  s.executables   = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }
  s.require_paths = ["lib"]

  # specify any dependencies here; for example:
  # s.add_development_dependency "rspec"
  # s.add_runtime_dependency "rest-client"
  s.add_development_dependency 'shoulda-context'
  s.add_development_dependency 'buildr'
end
