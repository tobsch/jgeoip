# coding: utf-8

require 'test_helper'
require 'benchmark'

class JGeoIPTest < Test::Unit::TestCase
  context 'The GeoIP Organization/ISP API' do
    setup do
      @geo = JGeoIP.new('/opt/MaxMind/GeoIPISP.dat')
    end

    should 'find the correct isp by ip' do
      assert_equal "Gruner + Jahr AG & Co, Hamburg", @geo.org('194.12.192.169')
      assert_equal "Deutsche Telekom AG", @geo.org('193.159.61.143')
    end
  end

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
    
    should 'be a proper location object' do
      result = @geo.city('github.com')
      assert_kind_of Location, result
    end
    
    should 'find the city by ip too' do
      result = @geo.city('207.97.227.239')
      assert_equal 'United States', result[:country_name]
      assert_in_delta 37.74839782714844, result.latitude, 10
      assert_in_delta -122.41560363769531, result.longitude, 10
    end

    should 'find out the distance between to points' do
      p1 = @geo.city('github.com')
      p2 = @geo.city('alice.de')
      p3 = @geo.city('facebook.com')
      # from sf to hamburg
      assert_in_delta 8893.200366609715, p1.distance(p2), 10
      
      # from sf to sf
      assert_in_delta 46.25354059751858, p1.distance(p3), 10
    end

    should 'return nil if an attribute does not exist' do
      result = @geo.city('207.97.227.239')
      assert_equal nil, result[:fooooooo]
    end

    should 'return a nil value if there is no value for an attribute' do
      result = @geo.city('85.183.18.35')
      assert_equal nil, result.postal_code
      assert_equal 0, result.dma_code
    end

    should 'be able to return the result as a hash' do
      result = @geo.city('85.183.18.35').to_hash
      assert_kind_of Hash, result
      assert_equal 'Germany', result[:country_name]
    end

    should 'be inspectable' do
      result = @geo.city('85.183.18.35').inspect
      assert_match /:city=>"(Othmarschen|Sparrieshoop)"/, result
    end

    should 'throw a clean exception if the ip was not found' do
      result = @geo.city('127.0.0.1')
      assert_equal nil, result
    end
    
    should 'convert values to utf8' do
      result = @geo.city('hs-osnabrueck.de').city
      assert_equal 'Osnabrück', result
      assert_equal 'UTF-8', result.encoding.to_s
    end
    
  end
end