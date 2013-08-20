package org.github.tobsch.jgeoip;

import com.maxmind.geoip.*;

import java.io.IOException;

import org.jruby.Ruby;

import org.jruby.RubyClass;
import org.jruby.RubyObject;
import org.jruby.RubySymbol;
import org.jruby.RubyHash;
import org.jruby.RubyInteger;

import org.jruby.anno.JRubyMethod;
import org.jruby.anno.JRubyConstant;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;


public class JGeoIP extends RubyObject {
  LookupService cl;
  static RubyClass locationProxyClass;

  @JRubyConstant public final static int GEOIP_STANDARD = LookupService.GEOIP_STANDARD;
  @JRubyConstant public final static int GEOIP_MEMORY_CACHE = LookupService.GEOIP_MEMORY_CACHE;
  @JRubyConstant public final static int GEOIP_CHECK_CACHE = LookupService.GEOIP_CHECK_CACHE;
  @JRubyConstant public final static int GEOIP_INDEX_CACHE = LookupService.GEOIP_INDEX_CACHE;

  public JGeoIP(final Ruby ruby, RubyClass rubyClass) {
    super(ruby, rubyClass);
    this.locationProxyClass = ruby.getClass("Location");
  }

  @JRubyMethod(required = 1, optional = 1)
  public IRubyObject initialize(ThreadContext context, IRubyObject[] args) throws IOException {
    String databaseLocation = args[0].toString();
    int cachingOptions = 0;

    if (args.length == 2) {
      RubyHash options = (RubyHash) args[1];
      RubySymbol cachingKey = RubySymbol.newSymbol(context.runtime, "caching");
      IRubyObject caching = options.fastARef(cachingKey);
      if (caching instanceof RubyInteger) {
        cachingOptions = (int) caching.convertToInteger().getLongValue();
      } else {
        throw context.runtime.newArgumentError("The :caching option must be an integer");
      }
    } else {
      cachingOptions = LookupService.GEOIP_MEMORY_CACHE;
    }

    cl = new LookupService(databaseLocation, cachingOptions);

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
