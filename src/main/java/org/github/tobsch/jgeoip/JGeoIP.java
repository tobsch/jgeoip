package org.github.tobsch.jgeoip;

import com.maxmind.geoip.*;

public class JGeoIP extends RubyObject {
  LookupService cl;

  public JGeoIP(final Ruby ruby, RubyClass rubyClass) {
    super(ruby, rubyClass);
  }
    
  @JRubyMethod
  public IRubyObject initialize(ThreadContext context, IRubyObject databaseLocation) throws IOException {
    cl = new LookupService(databaseLocation.toString(), LookupService.GEOIP_MEMORY_CACHE);
      
    return context.nil;
  }

  @JRubyMethod
  public IRubyObject city(ThreadContext context, IRubyObject searchString) throws IOException {
    Location location = cl.getLocation(searchString.toString());
    if (location == null) {
      RubyNil nil = new RubyNil(ruby);
      return nil;
    }
    
    LocationProxy location = new LocationProxy(context, loc);
        
    return location;
  }
}
