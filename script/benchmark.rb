require 'benchmark'

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
# check the JGeoIP
require File.expand_path('../../lib/jgeoip.rb', __FILE__)

# check the pure lib
require 'geoip'
db = GeoIP.new('/opt/MaxMind/GeoLiteCity.dat')
benchmark 'pure ruby GeoIP:'   do
  result = db.city('github.com')
end

db = JGeoIP.new('/opt/MaxMind/GeoLiteCity.dat')
benchmark 'JGeoIP:' do
  result = db.city('github.com')
end


