require 'bundler/gem_tasks'
require 'rake/testtask'

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
