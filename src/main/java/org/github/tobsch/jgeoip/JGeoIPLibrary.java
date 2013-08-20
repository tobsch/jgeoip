
package org.github.tobsch.jgeoip;
import org.github.tobsch.jgeoip.*;
import com.maxmind.geoip.*;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.Library;

/**
 *
 * 
 */
public class JGeoIPLibrary implements Library {
  private Ruby ruby;

  public void load(Ruby ruby, boolean bln) throws IOException {
    this.ruby = ruby;
    
    // define the jgeoip class
    final RubyClass jgeoip = ruby.defineClass("JGeoIP", ruby.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby ruby, RubyClass rc) {
        return new JGeoIP(ruby, rc);
      }
    });

    jgeoip.defineAnnotatedMethods(JGeoIP.class);
    jgeoip.defineAnnotatedConstants(JGeoIP.class);
    
    // define the location class
    final RubyClass location = ruby.defineClass("Location", ruby.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby ruby, RubyClass rc) {
        return new LocationProxy(ruby, rc);
      }
    });

    location.defineAnnotatedMethods(LocationProxy.class);
    
  }
}