# -*- encoding: utf-8 -*-
require File.expand_path("../lib/jgeoip/version", __FILE__)

Gem::Specification.new do |s|
  s.name        = "jgeoip"
  s.version     = JGeoIP::VERSION
  s.authors     = ["Tobias Schlottke"]
  s.email       = ["tobias.schlottke@gmail.com"]
  s.homepage    = "http://github.com/tobsch/jgeoip"
  s.summary     = 'Fast jRuby library for Maxminds GeoIP Databases'
  s.description = 'This gem wraps maxminds original java library, which should make things faster than using other pure ruby libraries.'
  s.platform    = 'java'
  
  s.files         = `git ls-files`.split("\n")
  s.test_files    = `git ls-files -- {test,spec,features}/*`.split("\n")
  s.executables   = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }
  s.require_paths = ["lib"]

  # specify any dependencies here; for example:
  # s.add_development_dependency "rspec"
  # s.add_runtime_dependency "rest-client"
  s.add_development_dependency 'shoulda-context'
  s.add_development_dependency 'buildr', '1.4.6'
  s.add_development_dependency 'ci_reporter'
  
  # necessary instead of Mini:Unit as Mini:Unit cannot produce Jenkins-compatible test results
  s.add_development_dependency 'test-unit'
end
