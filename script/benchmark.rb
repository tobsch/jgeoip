require 'benchmark'
require 'rubygems'

def benchmark(description, &block)
  puts description
  # do a warmup
  10_000.times {
    block.call
  }
  # do the actual measurements
  puts Benchmark.measure {
    100_000.times {
      block.call
    }
  }
end

jruby = defined?(JRUBY_VERSION)
DB_FILE = '/opt/MaxMind/GeoLiteCity.dat'
if jruby
  # check the JGeoIP
  require File.expand_path('../../lib/jgeoip.rb', __FILE__)

  db = JGeoIP.new(DB_FILE)
  benchmark 'JGeoIP:' do
    result = db.city('github.com')
  end
end

# check the pure lib
gem 'geoip'
require 'geoip'
db = GeoIP.new(DB_FILE)
benchmark 'pure ruby GeoIP:'   do
  result = db.city('github.com')
end

unless jruby
  gem 'geoip-c'
  require 'geoip-c/geoip'
  db = GeoIP::City.new(DB_FILE)
  benchmark 'geoip-c:' do
    db.look_up('207.97.227.239')
  end
end