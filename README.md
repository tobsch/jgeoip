# Fast JRuby GeoIP Extension
This jRuby extension will help you with all your GeoIP needs in JRuby.
It is pretty small and simple right now but will surely grow up soon.
You can easily use it with the free GeoIP Library from maxmind.com aswell as the commercial one.

## example
### City Lookup
    p JGeoIP.new('/opt/MaxMind/GeoLiteCity.dat').city('github.com')
    
    # => {:city=>"San Francisco", :postal_code=>"94110", :country_code=>"US", :country_name=>"United States", :region=>"CA",  :latitude=>37.74839782714844, :longitude=>37.74839782714844, :dma_code=>807, :area_code=>415, :metro_code=>807}

Will return a Location object with all the result properties and a #to_hash method
    
### Distance between two locations (km)
    p1 = @geo.city('github.com')
    p2 = @geo.city('facebook.com')
    
    p p1.distance(p2)
    # => 46.25354059751858

## speed?
There is a benchmark script included, so you'll be able to check it yourself.
We compared it to the GeoIP gem so far (because geoip-c won't compile with jRuby).
Results are promising:

#### JGeoIP
    JGeoIP (>=0.1.5):
      0.285000   0.000000   0.285000 (  0.286000)
    JGeoIP (old non extension version, <0.1.5):
      1.047000   0.000000   1.047000 (  1.048000)    

#### Others
    pure ruby GeoIP (jRuby):
     35.614000   0.000000  35.614000 ( 35.614000)
    geoip-c (MRI 1.9.3-p0):
      1.140000   0.000000   1.140000 (  1.145224)
    pure ruby GeoIP (MRI 1.9.3-p0):
      30.060000  16.420000  46.480000 ( 60.940723)        
*Cool, eh?*

Please note that we did a warmup to give the JIT a chance.
The benchmark does 100k lookups.
Those results are Java 7, jruby-1.6.5 based. Looking forward to 1.7.0.

### TODO
* integrate the other lookup methods: so far we're just able to do city lookups
* check all the license related stuff: the maxmind lib is LGPL, which would be okay for me    