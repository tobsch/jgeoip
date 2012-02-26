## Fast JRuby GeoIP Extension
This jRuby extension will help you with all your GeoIP needs in JRuby.
It is pretty small and simple right now but will surely grow up soon.
You can easily use it with the free GeoIP Library from maxmind.com aswell as the commercial one.

### example

    p JGeoIP.new('/opt/MaxMind/GeoLiteCity.dat').city('github.com')

Will return a Location object with all the result properties and a #to_hash method


### speed?
There is a benchmark script included, so you'll be able to check it yourself.
We compared it to the GeoIP gem so far (because geoip-c won't compile with jRuby).
Results are promising:

    pure ruby GeoIP:
     35.614000   0.000000  35.614000 ( 35.614000)
    JGeoIP (>=0.1.5):
      0.285000   0.000000   0.285000 (  0.286000)
    JGeoIP (non extension version, <0.1.5):
      1.047000   0.000000   1.047000 (  1.048000)    
      
Please note that we did a warmup to give the JIT a chance.
The benchmark does 100k lookups.
Those results are Java 7, jruby-1.6.5 based. Looking forward to 1.7.0.

### TODO
* switch back to an extension and a proxy object: this is surely faster. but time is money at the moment
* integrate the other lookup methods: so far we're just able to do city lookups
* check all the license related stuff: the maxmind lib is LGPL, which would be okay for me    