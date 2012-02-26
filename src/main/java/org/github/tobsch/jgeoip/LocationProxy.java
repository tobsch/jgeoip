package org.github.tobsch.jgeoip;
import com.maxmind.geoip.*;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.Library;
import org.jruby.runtime.Block;

import org.jruby.anno.JRubyMethod;

import org.jruby.RubyClass;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyObject;
import org.jruby.RubyHash;
import org.jruby.RubySymbol;

import java.util.ArrayList;


public class LocationProxy extends RubyObject {
  Location location;
  
  private static final ArrayList<String> attributes = new ArrayList<String>();
  
  // a map with all properties
  static {
    attributes.add("city");
    attributes.add("postal_code");
    attributes.add("country_code");
    attributes.add("country_name");
    attributes.add("region");
    attributes.add("latitude");
    attributes.add("longitude");
    attributes.add("dma_code");
    attributes.add("area_code");
    attributes.add("metro_code");    
  }
  
  public LocationProxy(final Ruby ruby, RubyClass rubyClass) {
    super(ruby, rubyClass);
  }

  public LocationProxy(final Ruby ruby, RubyClass rubyClass, Location location) {
    super(ruby, rubyClass);
    this.location = location;
  }
  
  @JRubyMethod
  public RubyArray keys(ThreadContext context) {
    RubyArray res = RubyArray.newArray(context.runtime);
    for (String key : attributes) {
      res.add(RubySymbol.newSymbol(context.runtime, key));
    }
    
    return res;
  }
  
  @JRubyMethod(name = "to_hash")
  public IRubyObject toHash(ThreadContext context) {
    RubyHash hash = RubyHash.newHash(context.runtime);
    for (String key : attributes) {
      hash.put(RubySymbol.newSymbol(context.runtime, key), get(context, context.runtime.newString(key)));
    }
    
    return hash;
  }
  
  @JRubyMethod(name = "[]")
  public IRubyObject get(ThreadContext context, IRubyObject key) {
    if (attributes.contains(key.toString())) {
      return send(context, key, Block.NULL_BLOCK);
    } else {
      return context.runtime.getNil();
    }
  }
  
  @JRubyMethod
  public IRubyObject distance(ThreadContext context, IRubyObject p2) {
    LocationProxy p2loc = (LocationProxy)p2;
    return context.runtime.newFloat(location.distance(p2loc.location));
  }
  
  @JRubyMethod
  public IRubyObject inspect(ThreadContext context) {
    return toHash(context).inspect();
  }
  
  @JRubyMethod(name = "city")
  public IRubyObject getCity(ThreadContext context) {
    return location.city == null ? context.runtime.getNil() : context.runtime.newString(location.city);
  }
  
  @JRubyMethod(name = "postal_code")
  public IRubyObject getPostalCode(ThreadContext context) {
    return location.postalCode == null ? context.runtime.getNil() : context.runtime.newString(location.postalCode);
  }

  @JRubyMethod(name = "country_code")
  public IRubyObject getCountryCode(ThreadContext context) {
    return location.countryCode == null ? context.runtime.getNil() : context.runtime.newString(location.countryCode);
  }

  @JRubyMethod(name = "country_name")
  public IRubyObject getCountryName(ThreadContext context) {
    return location.countryName == null ? context.runtime.getNil() : context.runtime.newString(location.countryName);
  }

  @JRubyMethod(name = "region")
  public IRubyObject getRegion(ThreadContext context) {
    return location.region == null ? context.runtime.getNil() : context.runtime.newString(location.region);
  }
  
  @JRubyMethod(name = "latitude")
  public IRubyObject getLatitude(ThreadContext context) {
    return context.runtime.newFloat(location.latitude);
  }

  @JRubyMethod(name = "longitude")
  public IRubyObject getLongitude(ThreadContext context) {
    return context.runtime.newFloat(location.latitude);
  }

  @JRubyMethod(name = "dma_code")
  public IRubyObject getDMACode(ThreadContext context) {
    return context.runtime.newFixnum(location.dma_code);
  }

  @JRubyMethod(name = "area_code")
  public IRubyObject getAreaCode(ThreadContext context) {
    return context.runtime.newFixnum(location.area_code);
  }

  @JRubyMethod(name = "metro_code")
  public IRubyObject getMetroCode(ThreadContext context) {
    return context.runtime.newFixnum(location.metro_code);
  }
}