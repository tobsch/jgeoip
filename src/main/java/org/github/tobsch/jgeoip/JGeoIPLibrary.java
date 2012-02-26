
package org.github.tobsch.jgeoip;
import org.github.tobsch.jgeoip.*

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
import org.jruby.RubyNil;
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

    RubyClass jgeoip = ruby.defineClass("JGeoIPJava", ruby.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby ruby, RubyClass rc) {
        return new JGeoIP(ruby, rc);
      }
    });

    jgeoip.defineAnnotatedMethods(JGeoIP.class);
  }
}