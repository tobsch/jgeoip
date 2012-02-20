
package org.github.tobsch.jgeoip;
import com.maxmind.geoip.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import org.jruby.runtime.Block;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.lang.Object;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.RubyObject;
import org.jruby.RubyStruct;
import org.jruby.RubyString;
import org.jruby.RubyFloat;
import org.jruby.RubyHash;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.Library;
import org.jruby.RubySymbol;

/**
 *
 * 
 */
public class JGeoIPLibrary implements Library {
  private Ruby ruby;

  public void load(Ruby ruby, boolean bln) throws IOException {
    this.ruby = ruby;

    RubyClass jgeoip = ruby.defineClass("JGeoIP", ruby.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby ruby, RubyClass rc) {
        return new JGeoIP(ruby, rc);
      }
    });

    jgeoip.defineAnnotatedMethods(JGeoIP.class);
  }

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
    public RubyHash city(ThreadContext context, IRubyObject searchString) throws IOException {
      Location location = cl.getLocation(searchString.toString());
      
      RubyHash loc = new RubyHash(ruby);
      loc.put(RubySymbol.newSymbol(ruby, "city"), RubyString.newString(ruby, location.city));
      loc.put(RubySymbol.newSymbol(ruby, "postal_code"), RubyString.newString(ruby, location.postalCode));
      loc.put(RubySymbol.newSymbol(ruby, "country_code"), RubyString.newString(ruby, location.countryCode));
      loc.put(RubySymbol.newSymbol(ruby, "country_name"), RubyString.newString(ruby, location.countryName));
      loc.put(RubySymbol.newSymbol(ruby, "region"), RubyString.newString(ruby, location.region));
      loc.put(RubySymbol.newSymbol(ruby, "latitude"), RubyFloat.newFloat(ruby, location.latitude));
      loc.put(RubySymbol.newSymbol(ruby, "longitude"), RubyFloat.newFloat(ruby, location.longitude));
      loc.put(RubySymbol.newSymbol(ruby, "dma_code"), RubyFixnum.newFixnum(ruby, location.dma_code));
      loc.put(RubySymbol.newSymbol(ruby, "area_code"), RubyFixnum.newFixnum(ruby, location.area_code));
      loc.put(RubySymbol.newSymbol(ruby, "metro_code"), RubyFixnum.newFixnum(ruby, location.metro_code));
      
      return loc;
    }
  }
}