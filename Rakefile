require 'rake/testtask'
require 'open-uri'
require 'zlib'

desc 'Run all the tests'
task :default => [ :test ]

task :test => [ 'test:unit' ]
namespace :test do
  
  task :environment do
    ENV['RACK_ENV'] = 'test'
  end
  
  [ :unit ].each do |category|
    Rake::TestTask.new(category) do |t|
      t.libs << "test"
      t.test_files = Dir["test/#{category}/**/*_test.rb"]
      t.verbose = true
    end
  end
end

task :setup => 'setup:download_db'
namespace :setup do
  task :download_db do
    remote_io = open('http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz')
    remote_io = Zlib::GzipReader.new(remote_io)
    FileUtils.mkdir_p('tmp')
    File.open('tmp/GeoLiteCity.dat', 'w') do |local_io|
      local_io.write(remote_io.read)
    end
  end
end

task :build do
  system %(buildr package)
  raise unless $?.success?
  libs = Dir['lib/java/jgeoip-*.jar']
  FileUtils.mv(libs.sort.last, 'lib/java/jgeoip.jar')
  FileUtils.rm_f(libs)
end

namespace :gem do
  require 'bundler/gem_tasks'
end

task :release => [:build, 'gem:release']