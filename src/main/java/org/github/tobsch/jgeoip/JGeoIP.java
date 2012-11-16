package org.github.tobsch.jgeoip;

import com.maxmind.geoip.*;

import java.io.IOException;

import org.jruby.Ruby;

import org.jruby.RubyClass;
import org.jruby.RubyObject;

import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;


public class JGeoIP extends RubyObject {
  LookupService cl;
  static RubyClass locationProxyClass;

  public JGeoIP(final Ruby ruby, RubyClass rubyClass) {
    super(ruby, rubyClass);
    this.locationProxyClass = ruby.getClass("Location");
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
      return context.runtime.getNil();
    }
    
    LocationProxy loc = new LocationProxy(context.runtime, locationProxyClass, location);
        
    return loc;
  }
  
  @JRubyMethod
  public IRubyObject org(ThreadContext context, IRubyObject searchString) throws IOException {
    String org = cl.getOrg(searchString.toString());
    if (org == null) {
      return context.runtime.getNil();
    }
    
    return context.runtime.newString(org);
  }

  @JRubyMethod
  public IRubyObject id(ThreadContext context, IRubyObject searchString) throws IOException {
    Integer id = cl.getID(searchString.toString());
    if (id == null) {
      return context.runtime.getNil();
    }
    
    return context.runtime.newFixnum((long)id);
  }
}
