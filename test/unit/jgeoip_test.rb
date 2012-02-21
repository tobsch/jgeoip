require 'test_helper'
require 'benchmark'

class JGeoIPTest < Test::Unit::TestCase
  context 'The Geoip City API' do
    setup do
      @geo = JGeoIP.new('/opt/MaxMind/GeoLiteCity.dat')
    end

    should 'find the correct city by hostname' do
      correct_keys = [ :city, :postal_code, :country_code, :country_name, :region, :latitude, :longitude, :dma_code, :area_code, :metro_code ]
      result = @geo.city('github.com')
      assert_equal correct_keys, result.keys
      assert_equal 'San Francisco', result[:city] 
    end
    
    should 'find the city by ip too' do
      result = @geo.city('207.97.227.239')
      assert_equal 'San Francisco', result[:city] 
    end

    should 'throw a clean exception if the ip was not found' do
      result = @geo.city('127.0.0.1')
      assert_equal nil, result
    end
  end
end