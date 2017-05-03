APP_ENV = ENV['APP_ENV'] || ENV['RACK_ENV'] || '  development'

require 'bundler/setup'
Bundler.setup(:default, APP_ENV)

require 'active_record'
require 'active_support'
require 'require_all'

Dir.glob('./lib/*.rb').each do |file|
  require file
end


Dir.glob('./lib/db/*').each do |folder|
  Dir.glob(folder + "/*.rb").each do |file|
    require file
  end
end

Dir.glob('./initializers/*.rb').each do |file|
  require file
end
