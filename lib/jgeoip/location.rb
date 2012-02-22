class Java::ComMaxmindGeoip::Location
  # returns a list of all relevant keys
  def keys
    mapping.keys
  end
  
  # getter for all value
  def [](key)
    mapping.key?(key) ? self.send(mapping[key]) : nil
  end
  
  def inspect
    to_hash.inspect
  end
  
  def to_hash
    mapping.inject({}) { |mem, map| mem[map.first] = self[map.last]; mem }
  end
  
  protected
    def mapping
      {
        :city         => :city,
        :postal_code  => :postalCode,
        :country_code => :countryCode,
        :country_name => :countryName,
        :region       => :region,
        :latitude     => :latitude,
        :longitude    => :longitude,
        :dma_code     => :dma_code,
        :area_code    => :area_code,
        :metro_code   => :metro_code
      }
    end
end